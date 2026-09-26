package com.e_commerce.ShopSphere.user.service;

import com.e_commerce.ShopSphere.common.exception.RoleNotFoundException;
import com.e_commerce.ShopSphere.common.exception.SameEmailException;
import com.e_commerce.ShopSphere.user.dto.CreateUserRequest;
import com.e_commerce.ShopSphere.user.dto.UserResponse;
import com.e_commerce.ShopSphere.user.entity.Role;
import com.e_commerce.ShopSphere.user.entity.User;
import com.e_commerce.ShopSphere.user.repository.RoleRepository;
import com.e_commerce.ShopSphere.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.e_commerce.ShopSphere.enums.UserStatus;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(CreateUserRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new SameEmailException("User already exists");
        }
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(role);

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setRoleName(savedUser.getRole().getName());
        response.setStatus(savedUser.getStatus().name());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        return response;

    }

}
