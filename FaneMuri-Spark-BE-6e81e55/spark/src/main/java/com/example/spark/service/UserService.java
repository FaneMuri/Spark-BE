package com.example.spark.service;

import com.example.spark.Utils.EmailValidator;
import com.example.spark.Utils.NameValidator;
import com.example.spark.Utils.PasswordValidator;
import com.example.spark.model.Role;
import com.example.spark.model.User;
import com.example.spark.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final NameValidator nameValidator;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                       EmailValidator emailValidator, NameValidator nameValidator,
                       PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
        this.nameValidator = nameValidator;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User registerUser(User user) {
        // Validate full name
        String fullName = user.getFullname();
        if (!nameValidator.isValidName(fullName)) {
            throw new RuntimeException("Full name is not valid: " + fullName);
        }

        Role role = user.getRole();
        if (role == null || (role != Role.USER && role != Role.ADMIN)) {
            throw new RuntimeException("Role is not valid: " + role);
        }



        // Validate email
        String userEmail = user.getEmail();
        if (!emailValidator.isValidEmail(userEmail)) {
            throw new RuntimeException("Email is not valid: " + userEmail);
        }
        if (userRepository.findByEmail(userEmail).isPresent()) {
            throw new RuntimeException("Email already used: " + userEmail);
        }

        // Validate username
        String username = user.getUsername();
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username cannot be null or empty");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken: " + username);
        }

        // Validate password
        String userPassword = user.getPassword();
        if (!passwordValidator.isValidPassword(userPassword)) {
            throw new RuntimeException("Password must contain only letters and/or digits and must be between 6 and 20 characters");
        }

        // Hash and set the password
        String hashedPassword = passwordEncoder.encode(userPassword);
        user.setPassword(hashedPassword);

        // Save the user to the database
        userRepository.save(user);

        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
