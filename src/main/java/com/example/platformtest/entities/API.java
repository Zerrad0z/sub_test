package com.example.platformtest.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "API")
public class API {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APIID")
    private Long apiId;

    @Column(name = "Nom")
    private String nom;



    public API(Long apiId, String nom) {
        this.apiId = apiId;
        this.nom = nom;
    }

    public API() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }
}
