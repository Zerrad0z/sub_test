package com.example.platformtest.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SubscriptionRequest")
public class SubscriptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestID")
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "APIID")
    private API api;

    @ManyToOne
    @JoinColumn(name = "AdminID")
    private Admin admin;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    public SubscriptionRequest(User user, API api, Admin admin, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.api = api;
        this.admin = admin;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SubscriptionRequest() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
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
}
