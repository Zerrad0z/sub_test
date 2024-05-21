package com.example.platformtest.services;

import com.example.platformtest.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long userId);
    void updateUser(Long userId, String username, String email, String roles);
    void deleteUser(Long userId);
}
