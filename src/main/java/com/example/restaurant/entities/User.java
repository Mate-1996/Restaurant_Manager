package com.example.restaurant.entities;

public abstract class User {
    protected int userId;
    protected String name;
    protected String role;

    public User(int userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getRole() { return role; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
}
