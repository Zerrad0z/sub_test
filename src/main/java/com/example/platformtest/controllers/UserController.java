package com.example.platformtest.controllers;

import com.example.platformtest.entities.SubscriptionRequest;
import com.example.platformtest.entities.User;
import com.example.platformtest.repositories.UserRepository;
import com.example.platformtest.services.SubscriptionRequestService;
import com.example.platformtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionRequestService subscriptionRequestService;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          SubscriptionRequestService subscriptionRequestService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionRequestService = subscriptionRequestService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/homepage")
    public String home() {
        return "homepage";
    }

    @GetMapping("/subscription_requests")
    public String getUserSubscriptionRequests(Model model) {
        User user = getCurrentUser();
        List<SubscriptionRequest> subscriptionRequests = subscriptionRequestService.getSubscriptionRequestsByUserAndStatus(user.getUserId(), false);
        model.addAttribute("subscriptionRequests", subscriptionRequests);
        return "user/user_requests";
    }

    @GetMapping("/subscription_requests/approved")
    public ResponseEntity<List<SubscriptionRequest>> getUserApprovedSubscriptionRequests() {
        User user = getCurrentUser();
        List<SubscriptionRequest> subscriptionRequests = subscriptionRequestService.getSubscriptionRequestsByUserAndStatus(user.getUserId(), true);
        return ResponseEntity.ok(subscriptionRequests);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @GetMapping("/user/user_details")
    public String userDetails(Model model) {
        User currentUser = getCurrentUser();
        // Do not include password in the model
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("email", currentUser.getEmail());
        // Add other necessary attributes
        return "user/user_details";
    }
    @PostMapping("/update_user")
    public String updateUser(@RequestParam("userId") Long userId,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {
        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(password);
        userService.updateUser(userId, username, email, encryptedPassword);
        return "redirect:/user/user_details";
    }

}
