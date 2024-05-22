package com.example.platformtest.services;

import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.User;
import com.example.platformtest.repositories.SubscriptionRepository;
import com.example.platformtest.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
         email = "user1@gmail.com"; // Replace with a valid email from your database
        String finalEmail = email;
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + finalEmail));
        return subscriptionRepository.findByUser(Optional.ofNullable(user));
    }

    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public List<Subscription> findSubscriptionsByUser(User user) {
        return subscriptionRepository.findByUser(Optional.ofNullable(user));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
    public void updateEndDate(Long subscriptionId, LocalDate newEndDate) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        subscription.setEndDate(newEndDate);
        subscriptionRepository.save(subscription);
    }

    public void expireSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        subscription.setStatut(false); // Assuming 'statut' represents the status
        subscriptionRepository.save(subscription);
    }
    @Transactional
    public void approveSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found"));

        subscription.setStatut(true); // or set it to whatever value indicates approval
        subscriptionRepository.save(subscription);

        // Add logging to verify the method is called and the subscription is saved
        System.out.println("Approved subscription with ID: " + subscriptionId);

    }
}
