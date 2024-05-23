package com.example.platformtest.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class SubscriptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private API api;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean approved;

    public SubscriptionRequest(Long id, boolean approved, LocalDate endDate, LocalDate startDate, API api, User user) {
        this.id = id;
        this.approved = approved;
        this.endDate = endDate;
        this.startDate = startDate;
        this.api = api;
        this.user = user;
    }

    public SubscriptionRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getStatus() {

        if (approved) {
            return "Approved";
        } else {
            return "Pending";
        }
    }
}