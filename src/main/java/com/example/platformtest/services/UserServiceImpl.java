package com.example.platformtest.services;

import com.example.platformtest.entities.Role;
import com.example.platformtest.entities.User;
import com.example.platformtest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void updateUser(Long userId, String username, String email, String roles) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUsername(username);
            user.setEmail(email);
            Set<Role> roleSet = Stream.of(roles.split(","))
                    .map(String::trim)
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
            user.setRoles(roleSet);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
