package com.e_commerce.ShopSphere.user.controller;


import com.e_commerce.ShopSphere.user.dto.CreateUserRequest;
import com.e_commerce.ShopSphere.user.dto.UserResponse;
import com.e_commerce.ShopSphere.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid  @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(Authentication authentication){

        String email = authentication.getName();
        UserResponse response = userService.getCurrentUser(email);

        return ResponseEntity.ok(response);
    }
}
