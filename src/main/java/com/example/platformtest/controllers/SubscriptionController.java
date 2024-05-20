package com.example.platformtest.controllers;

import com.example.platformtest.entities.API;
import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.User;
import com.example.platformtest.repositories.APIRepository;
import com.example.platformtest.repositories.UserRepository;
import com.example.platformtest.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final APIRepository apiRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, APIRepository apiRepository, UserRepository userRepository) {
        this.subscriptionService = subscriptionService;
        this.apiRepository = apiRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/subscribe")
    public String subscribeToApi(@RequestParam Long apiId,
                                 @RequestParam("startDate") LocalDate startDate,
                                 @RequestParam("endDate") LocalDate endDate,
                                 Principal principal) {
        // Fetch authenticated user
        User user = fetchAuthenticatedUser(principal);

        // Fetch API by id
        Optional<API> optionalApi = apiRepository.findById(apiId);
        if (optionalApi.isEmpty()) {
            // Handle case where API with given ID is not found
            throw new RuntimeException("API not found with id: " + apiId);
        }
        API api = optionalApi.get();

        // Create new subscription
        Subscription subscription = new Subscription();
        subscription.setApi(api);
        subscription.setUser(user);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setStatut(true);

        // Save subscription
        subscriptionService.saveSubscription(subscription);

        // Redirect or return appropriate view
        return "redirect:/index"; // Example redirect to index page after subscription
    }

    private User fetchAuthenticatedUser(Principal principal) {
        String email = principal.getName(); // Assuming principal contains the email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @GetMapping("/subscriptions")
    public String getUserSubscriptions(Authentication authentication, Model model) {
        String email = authentication.getName(); // Get the authenticated user's email
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(email);
        model.addAttribute("subscriptions", subscriptions);
        return "subscriptions"; // The name of your Thymeleaf template
    }
}
