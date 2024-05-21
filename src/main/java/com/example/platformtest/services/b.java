package com.example.platformtest.services;

import com.example.platformtest.entities.User;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;

public class CustomUserDetails extends User {
    private final String name;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.name = user.getName(); // Assuming User entity has a name field
    }

    public String getName() {
        return name;
    }
}
