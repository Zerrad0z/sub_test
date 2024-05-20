package com.example.platformtest.services;

import com.example.platformtest.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

}
