package com.example.platformtest.controllers;

import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.User;
import com.example.platformtest.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private SubscriptionService subscriptionService;

    // This method handles GET requests to /admin/manage_subscriptions and displays the initial form
    @GetMapping("/manage_subscriptions")
    public String manageSubscriptions(Model model) {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        model.addAttribute("subscriptions", subscriptions);
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
    @PostMapping("/update_subscription")
    public String updateSubscription(@RequestParam("subscriptionId") Long subscriptionId,
                                     @RequestParam(value = "newEndDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newEndDate,
                                     RedirectAttributes redirectAttributes) {
        if (newEndDate != null) {
            // Update the subscription end date
            subscriptionService.updateEndDate(subscriptionId, newEndDate);
        }
        // Redirect back to the search_subscriptions page with the username as a parameter
        redirectAttributes.addAttribute("username", "user1"); // Replace "user1" with the actual username
        return "redirect:/admin/search_subscriptions";
    }

    @PostMapping("/end_subscription")
    public String endSubscription(@RequestParam("subscriptionId") Long subscriptionId,
                                  RedirectAttributes redirectAttributes) {
        // End the subscription
        subscriptionService.expireSubscription(subscriptionId);
        // Redirect back to the search_subscriptions page with the username as a parameter
        redirectAttributes.addAttribute("username", "user1"); // Replace "user1" with the actual username
        return "redirect:/admin/search_subscriptions";
    }
}
