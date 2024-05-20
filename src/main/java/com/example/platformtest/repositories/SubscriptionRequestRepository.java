package com.example.platformtest.repositories;

import com.example.platformtest.entities.SubscriptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRequestRepository extends JpaRepository<SubscriptionRequest, Long> {
    // Define custom query methods if needed
}
