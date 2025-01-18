
package com.example.spark.controller;

import com.example.spark.auth.AuthenticationRequest;
import com.example.spark.auth.AuthenticationResponse;
import com.example.spark.model.DTO.UpdateRoleDTO;
import com.example.spark.model.Token;
import com.example.spark.model.DTO.UserLoginDTO;
import com.example.spark.model.DTO.UserSignupDTO;
import com.example.spark.model.User;
import com.example.spark.repository.TokenRepository;
import com.example.spark.service.AuthenticationService;
import com.example.spark.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final AuthenticationService authService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
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

    @GetMapping("/username/{id}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String id) {
        return userService.findUserByUsername(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/role")
    public ResponseEntity<UserSignupDTO> updateUserRole(@RequestBody UpdateRoleDTO user) {
        var x = userService.updateUserRole(user.getId(),user.getRole());
        return ResponseEntity.ok(UserSignupDTO.convertToDTO(x));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserLoginDTO loginDTO) {
        Optional<User> foundUser = userService.login(loginDTO);
        if (foundUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authService.authenticate(new AuthenticationRequest(loginDTO.getUsername(), loginDTO.getPassword())));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignupDTO> signup(@RequestBody UserSignupDTO signupDTO) {
        User user = userService.createUserFromDTO(signupDTO);
        return ResponseEntity.ok(UserSignupDTO.convertToDTO(userService.saveUser(user)));
    }

    @RequestMapping(value = "/token/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Token>> getAllTokensOfUser(@PathVariable Long id) {
        var user = userService.findUserById(id);
        return user.map(value -> ResponseEntity.of(Optional.ofNullable(value.getTokens())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}