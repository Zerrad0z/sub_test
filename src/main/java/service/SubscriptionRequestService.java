package service;

import entities.SubscriptionRequest;

import java.util.List;

public interface SubscriptionRequestService {
    List<SubscriptionRequest> getAllSubscriptionRequests();
    SubscriptionRequest getSubscriptionRequestById(Long requestId);
    SubscriptionRequest saveSubscriptionRequest(SubscriptionRequest request);
    void deleteSubscriptionRequest(Long requestId);
}
