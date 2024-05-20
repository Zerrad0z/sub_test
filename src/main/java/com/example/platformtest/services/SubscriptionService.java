package com.example.platformtest.services;

import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.User;
import com.example.platformtest.repositories.SubscriptionRepository;
import com.example.platformtest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }


    public List<Subscription> getUserSubscriptions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return subscriptionRepository.findByUser(Optional.ofNullable(user));
    }

    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public List<Subscription> findSubscriptionsByUser(User user) {
        return subscriptionRepository.findByUser(Optional.ofNullable(user));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

}
