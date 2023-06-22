package com.project.project.models;

import javax.persistence.*;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String creationData;
    private String expirationData;
    private int budget;
    private String status;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationData() {
        return creationData;
    }

    public void setCreationData(String creationData) {
        this.creationData = creationData;
    }

    public String getExpirationData() {
        return expirationData;
    }

    public void setExpirationData(String expirationData) {
        this.expirationData = expirationData;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
