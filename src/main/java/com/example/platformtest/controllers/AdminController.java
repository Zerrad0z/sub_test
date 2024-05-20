package com.example.platformtest.controllers;

import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.User;
import com.example.platformtest.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private SubscriptionService subscriptionService;

    // This method handles GET requests to /admin/manage_subscriptions and displays the initial form
    @GetMapping("/manage_subscriptions")
    public String manageSubscriptionsPage() {
        return "admin/manage_subscriptions";
    }

    // This method handles GET requests to /admin/search_subscriptions and retrieves user subscriptions
    @GetMapping("/search_subscriptions")
    public String searchUserSubscriptions(@RequestParam("username") String username, Model model) {
        User user = subscriptionService.findUserByUsername(username);
        if (user != null) {
            List<Subscription> subscriptions = subscriptionService.findSubscriptionsByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("subscriptions", subscriptions);
        } else {
            model.addAttribute("errorMessage", "User not found with username: " + username);
        }
        return "admin/manage_subscriptions";
    }
}
