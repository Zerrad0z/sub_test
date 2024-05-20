package com.example.platformtest.services;

import com.example.platformtest.entities.SubscriptionRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubscriptionRequestServiceImpl implements SubscriptionRequestService {
    @Override
    public List<SubscriptionRequest> getAllSubscriptionRequests() {
        return List.of();
    }

    @Override
    public SubscriptionRequest getSubscriptionRequestById(Long requestId) {
        return null;
    }

    @Override
    public SubscriptionRequest saveSubscriptionRequest(SubscriptionRequest request) {
        return null;
    }

    @Override
    public void deleteSubscriptionRequest(Long requestId) {

    }
}
