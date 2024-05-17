package controller;

import entities.SubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.SubscriptionRequestService;

import java.util.List;

@RestController
@RequestMapping("/subscription-requests")
public class SubscriptionRequestController {

    @Autowired
    private SubscriptionRequestService requestService;

    @GetMapping
    public List<SubscriptionRequest> getAllSubscriptionRequests() {
        return requestService.getAllSubscriptionRequests();
    }

    @GetMapping("/{requestId}")
    public SubscriptionRequest getSubscriptionRequestById(@PathVariable Long requestId) {
        return requestService.getSubscriptionRequestById(requestId);
    }

    @PostMapping
    public SubscriptionRequest createSubscriptionRequest(@RequestBody SubscriptionRequest request) {
        return requestService.saveSubscriptionRequest(request);
    }

    @DeleteMapping("/{requestId}")
    public void deleteSubscriptionRequest(@PathVariable Long requestId) {
        requestService.deleteSubscriptionRequest(requestId);
    }
}
