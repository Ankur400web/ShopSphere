package com.e_commerce.ShopSphere.address.service;

import com.e_commerce.ShopSphere.address.dto.AddressResponse;
import com.e_commerce.ShopSphere.address.dto.CreateAddressRequest;
import com.e_commerce.ShopSphere.address.dto.UpdateAddressRequest;
import com.e_commerce.ShopSphere.address.entity.Address;
import com.e_commerce.ShopSphere.address.repository.AddressRepository;
import com.e_commerce.ShopSphere.user.entity.User;
import com.e_commerce.ShopSphere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    /*
     * CREATE ADDRESS
     */
    @Transactional
    public AddressResponse createAddress(
            CreateAddressRequest request
    ) {

        User user = getAuthenticatedUser();

        /*
         * If this address is going to be the default address,
         * remove default status from the user's existing addresses.
         */
        if (request.isDefaultAddress()) {
            removeDefaultAddress(user.getId());
        }

        Address address = new Address();

        address.setUser(user);
        address.setFullName(request.getFullName());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setDefaultAddress(request.isDefaultAddress());

        Address savedAddress =
                addressRepository.save(address);

        return buildAddressResponse(savedAddress);
    }


    /*
     * GET ALL ADDRESSES
     */
    @Transactional(readOnly = true)
    public List<AddressResponse> getMyAddresses() {

        User user = getAuthenticatedUser();

        return addressRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::buildAddressResponse)
                .toList();
    }


    /*
     * GET ONE ADDRESS
     */
    @Transactional(readOnly = true)
    public AddressResponse getMyAddress(Long addressId) {

        User user = getAuthenticatedUser();

        Address address =
                addressRepository
                        .findByIdAndUserId(
                                addressId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Address not found"
                                )
                        );

        return buildAddressResponse(address);
    }


    /*
     * UPDATE ADDRESS
     */
    @Transactional
    public AddressResponse updateAddress(
            Long addressId,
            UpdateAddressRequest request
    ) {

        User user = getAuthenticatedUser();

        Address address =
                addressRepository
                        .findByIdAndUserId(
                                addressId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Address not found"
                                )
                        );

        if (request.isDefaultAddress()) {
            removeDefaultAddress(user.getId());
        }

        address.setFullName(request.getFullName());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setDefaultAddress(
                request.isDefaultAddress()
        );

        Address updatedAddress =
                addressRepository.save(address);

        return buildAddressResponse(updatedAddress);
    }


    /*
     * DELETE ADDRESS
     */
    @Transactional
    public void deleteAddress(Long addressId) {

        User user = getAuthenticatedUser();

        Address address =
                addressRepository
                        .findByIdAndUserId(
                                addressId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Address not found"
                                )
                        );

        addressRepository.delete(address);
    }


    /*
     * SET DEFAULT ADDRESS
     */
    @Transactional
    public AddressResponse setDefaultAddress(
            Long addressId
    ) {

        User user = getAuthenticatedUser();

        Address address =
                addressRepository
                        .findByIdAndUserId(
                                addressId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Address not found"
                                )
                        );

        removeDefaultAddress(user.getId());

        address.setDefaultAddress(true);

        Address savedAddress =
                addressRepository.save(address);

        return buildAddressResponse(savedAddress);
    }


    /*
     * REMOVE DEFAULT STATUS
     */
    private void removeDefaultAddress(Long userId) {

        List<Address> addresses =
                addressRepository.findByUserId(userId);

        for (Address address : addresses) {

            if (address.isDefaultAddress()) {
                address.setDefaultAddress(false);
            }
        }

        addressRepository.saveAll(addresses);
    }


    /*
     * BUILD RESPONSE
     */
    private AddressResponse buildAddressResponse(
            Address address
    ) {

        AddressResponse response =
                new AddressResponse();

        response.setId(address.getId());
        response.setFullName(address.getFullName());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPostalCode(address.getPostalCode());
        response.setCountry(address.getCountry());
        response.setPhoneNumber(address.getPhoneNumber());
        response.setDefaultAddress(
                address.isDefaultAddress()
        );

        return response;
    }


    /*
     * GET AUTHENTICATED USER
     */
    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User doesn't exist"
                        )
                );
    }
}