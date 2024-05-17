package entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "DemandeAutorisation")
public class SubscriptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DemandeAutorisationID")
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

    public SubscriptionRequest(User user, Long requestId, API api, Admin admin) {
        this.user = user;
        this.requestId = requestId;
        this.api = api;
        this.admin = admin;
    }

    public SubscriptionRequest() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
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
}

