package com.example.platformtest.repositories;

import com.example.platformtest.entities.SubscriptionRequest;
import com.example.platformtest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRequestRepository extends JpaRepository<SubscriptionRequest, Long> {
    List<SubscriptionRequest> findByApprovedFalse();
    List<SubscriptionRequest> findByUserAndApproved(User user, boolean approved);
}
