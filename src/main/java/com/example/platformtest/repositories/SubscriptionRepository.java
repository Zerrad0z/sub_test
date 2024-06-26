package com.example.platformtest.repositories;



import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(Optional<User> user);
}