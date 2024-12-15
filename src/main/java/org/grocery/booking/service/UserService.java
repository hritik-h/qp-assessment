package org.grocery.booking.service;

import org.grocery.booking.Model.RegisterRequest;
import org.grocery.booking.entity.Role;
import org.grocery.booking.entity.User;
import org.grocery.booking.repos.RoleRepository;
import org.grocery.booking.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest registerRequest) {
        // Check if the user already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Find the role
        Role role = roleRepository.findByName(registerRequest.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Create the user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Set.of(role));

        // Save the user
        return userRepository.save(user);
    }
}
