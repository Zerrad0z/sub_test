package com.example.platformtest.controllers;

import com.example.platformtest.entities.Subscription;
import com.example.platformtest.entities.SubscriptionRequest;
import com.example.platformtest.entities.User;
import com.example.platformtest.services.SubscriptionRequestService;
import com.example.platformtest.services.SubscriptionService;
import com.example.platformtest.services.UserService;
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
    private SubscriptionRequestService subscriptionRequestService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

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

        // Fetch username associated with the subscription
        String username = subscriptionService.getSubscriptionById(subscriptionId).getUser().getUsername();

        // Redirect back to the search_subscriptions page with the username as a parameter
        redirectAttributes.addAttribute("username", username);
        return "redirect:/admin/search_subscriptions";
    }

    @GetMapping("/manage_users")
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/manage_users";
    }

    @GetMapping("/user_details")
    public String userDetails(@RequestParam("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "admin/user_details";
    }

    @PostMapping("/update_user")
    public String updateUser(@RequestParam("userId") Long userId,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("roles") String roles) {
        userService.updateUser(userId, username, email, roles);
        return "redirect:/admin/manage_users";
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/manage_users";
    }

    // Method to display subscription requests for admin review
    @GetMapping("/subscription_requests")
    public String getSubscriptionRequests(Model model) {
        List<SubscriptionRequest> subscriptionRequests = subscriptionRequestService.getAllSubscriptionRequests();
        model.addAttribute("subscriptionRequests", subscriptionRequests);
        return "admin/subscription_requests";
    }

    @PostMapping("/approve_request")
    public String approveRequest(@RequestParam Long requestId) {
        subscriptionRequestService.approveSubscriptionRequest(requestId);
        return "redirect:/admin/subscription_requests";
    }

    @PostMapping("/reject_request")
    public String rejectRequest(@RequestParam Long requestId) {
        subscriptionRequestService.rejectSubscriptionRequest(requestId);
        return "redirect:/admin/subscription_requests"; // Redirect to view all requests after action
    }
}