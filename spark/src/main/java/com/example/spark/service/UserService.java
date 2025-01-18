package com.example.spark.service;

import com.example.spark.model.DTO.UserLoginDTO;
import com.example.spark.model.DTO.UserSignupDTO;
import com.example.spark.model.Role;
import com.example.spark.model.User;
import com.example.spark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> login(UserLoginDTO user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    public User createUserFromDTO(UserSignupDTO userDTO) {
        User user = new User();
        user.setRole(Role.USER);
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFullname(userDTO.getFullname());
        user.setOrganizedEvents(new ArrayList<>());
        user.setComments(new ArrayList<>());

        return user;
    }
}
