package com.example.platformtest.services;

import com.example.platformtest.entities.SubscriptionRequest;

import java.util.List;

public interface SubscriptionRequestService {
    List<SubscriptionRequest> getAllSubscriptionRequests();
    SubscriptionRequest getSubscriptionRequestById(Long requestId);
    SubscriptionRequest saveSubscriptionRequest(SubscriptionRequest request);
    void deleteSubscriptionRequest(Long requestId);

    void approveSubscriptionRequest(Long requestId);

    void rejectSubscriptionRequest(Long requestId);

    List<SubscriptionRequest> getSubscriptionRequestsByUserAndStatus(Long userId, boolean approved);
}
