package com.example.platformtest.services;

import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.SubscriptionRequest;
import com.example.platformtest.entities.User;
import com.example.platformtest.repositories.SubscriptionRequestRepository;
import com.example.platformtest.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionRequestServiceImpl implements SubscriptionRequestService {

    @Autowired
    private SubscriptionRequestRepository subscriptionRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SubscriptionService subscriptionService;

    @Override
    public List<SubscriptionRequest> getAllSubscriptionRequests() {
        return subscriptionRequestRepository.findByApprovedFalse();
    }

    @Override
    public SubscriptionRequest getSubscriptionRequestById(Long requestId) {
        return subscriptionRequestRepository.findById(requestId).orElse(null);
    }

    @Override
    public SubscriptionRequest saveSubscriptionRequest(SubscriptionRequest request) {
        return subscriptionRequestRepository.save(request);
    }

    @Override
    public void deleteSubscriptionRequest(Long requestId) {
        subscriptionRequestRepository.deleteById(requestId);
    }

    @Override
    public void approveSubscriptionRequest(Long requestId) {
        SubscriptionRequest request = getSubscriptionRequestById(requestId);
        if (request != null) {
            request.setApproved(true);
            subscriptionRequestRepository.save(request);

            // Create a new Subscription if the request is approved
            if (request.isApproved()) {
                Subscription subscription = new Subscription();
                subscription.setUser(request.getUser());
                subscription.setApi(request.getApi());
                subscription.setStartDate(LocalDate.now());
                subscription.setEndDate(request.getEndDate());
                subscription.setStatut(true); // Assuming 'statut' represents the status
                subscriptionService.saveSubscription(subscription);
            }
        }
    }

    @Override
    public void rejectSubscriptionRequest(Long requestId) {
        subscriptionRequestRepository.deleteById(requestId);
    }
    @Override
    public List<SubscriptionRequest> getSubscriptionRequestsByUserAndStatus(Long userId, boolean approved) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return subscriptionRequestRepository.findByUserAndApproved(user, approved);
    }

}
