package com.example.platformtest.controllers;

import com.example.platformtest.entities.Role;
import com.example.platformtest.entities.User;
import com.example.platformtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/superadmin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/manage_roles")
    public String manageRoles(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "superadmin/manage_roles";
    }

    @PostMapping("/update_role")
    public String updateRole(@RequestParam("userId") Long userId,
                             @RequestParam("roles") String[] roles) {
        User user = userService.getUserById(userId);
        Set<Role> rolesSet = new HashSet<>();
        for (String role : roles) {
            rolesSet.add(Role.valueOf(role));
        }
        user.setRoles(rolesSet);
        userService.saveUser(user);
        return "redirect:/superadmin/manage_roles";
    }
}
