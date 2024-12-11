
package com.example.spark.controller;

import com.example.spark.auth.AuthenticationRequest;
import com.example.spark.auth.AuthenticationResponse;
import com.example.spark.model.Token;
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

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user) {
        Optional<User> foundUser = userService.login(user);
        if (foundUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authService.authenticate(new AuthenticationRequest(user.getUsername(), user.getPassword())));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<User> signup(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @RequestMapping(value = "/token/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Token>> getAllTokensOfUser(@PathVariable Long id) {
        var user = userService.findUserById(id);
        return user.map(value -> ResponseEntity.of(Optional.ofNullable(value.getTokens())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}