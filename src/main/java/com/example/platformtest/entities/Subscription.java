package com.example.platformtest.entities;

import jakarta.persistence.*;

import java.time.LocalDate;



@Entity
@Table(name = "Subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubscriptionID")
    private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "APIID")
    private API api;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Column(name = "Statut")
    private boolean statut;

    // Constructors
    public Subscription() {
    }

    public Subscription(Long subscriptionId, API api, User user, LocalDate startDate, LocalDate endDate, boolean statut) {
        this.subscriptionId = subscriptionId;
        this.api = api;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statut = statut;
    }

    // Getters and Setters
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }
    @Transient
    public String getStatus() {
        LocalDate today = LocalDate.now();
        if (endDate != null && endDate.isBefore(today)) {
            return "expired";
        } else {
            return "active";
        }
    }
}
