package com.example.restaurant.entities;

public class Chef extends Employee {
    private String specialty;

    public Chef() {}

    public Chef(int employeeId, String name, double salary, String specialty) {
        super(employeeId, name, salary);
        this.specialty = specialty;
    }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}
