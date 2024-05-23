package com.example.platformtest.controllers;


import com.example.platformtest.entities.Role;
import com.example.platformtest.entities.SubscriptionRequest;
import com.example.platformtest.entities.User;
import com.example.platformtest.repositories.UserRepository;
import com.example.platformtest.services.SubscriptionRequestService;
import com.example.platformtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @GetMapping("/subscription_requests/approved")
    public ResponseEntity<List<SubscriptionRequest>> getUserApprovedSubscriptionRequests() {
        User user = getCurrentUser();
        List<SubscriptionRequest> subscriptionRequests = subscriptionRequestService.getSubscriptionRequestsByUserAndStatus(user.getUserId(), true);
        return ResponseEntity.ok(subscriptionRequests);
    }

    @GetMapping("/user/user_details")
    public String userDetails(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("roles", currentUser.getRoles().stream()
                .map(Role::name) // Use name() method to get the string representation of the enum constant
                .collect(Collectors.joining(","))); // Ensure roles are added
        model.addAttribute("userId", currentUser.getUserId());
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
    // Search users with pagination
    @GetMapping("/admin/users")
    public String searchUsers(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "10") int size,
                              @RequestParam(name = "mc", defaultValue = "") String mc) {
        Page<User> userPage = userRepository.findByUsernameContaining(mc, PageRequest.of(page, size));
        model.addAttribute("users", userPage.getContent());
        int[] pages = new int[userPage.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", size);
        model.addAttribute("pageCourante", page);
        model.addAttribute("motCle", mc);
        return "admin/manage_users";
    }

}
