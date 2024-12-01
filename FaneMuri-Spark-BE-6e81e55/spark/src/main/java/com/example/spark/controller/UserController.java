package com.example.spark.controller;

import com.example.spark.model.User;
import com.example.spark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Endpoints")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns the authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully and token created"),
            @ApiResponse(responseCode = "400", description = "Login failed due to incorrect credentials.")
    })
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        return userService.login(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Register a new user", description = "Creates a new user account and returns the authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully and token created"),
            @ApiResponse(responseCode = "400", description = "Registration failed due to some authentication-related issue (e.g., username already taken, invalid data).")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userData) {
        try {
            User user = userService.registerUser(userData);
            //TokenDTO tokenDTO = authService.createTokenReturnDTO(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
