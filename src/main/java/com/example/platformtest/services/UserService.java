package com.example.platformtest.services;

import com.example.platformtest.entities.Role;
import com.example.platformtest.entities.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long userId);
    void updateUser(Long userId, String username, String email, String roles);
    void deleteUser(Long userId);

    User createUser(String username, String email, String password, Set<Role> roles);

    void saveUser(User user);
}
