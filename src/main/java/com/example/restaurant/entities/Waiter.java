package com.example.restaurant.entities;

public class Waiter extends Employee {
    private int numberOfTablesAssigned;

    public Waiter() {}

    public Waiter(int employeeId, String name, double salary, int numberOfTablesAssigned) {
        super(employeeId, name, salary);
        this.numberOfTablesAssigned = numberOfTablesAssigned;
    }

    public int getNumberOfTablesAssigned() { return numberOfTablesAssigned; }
    public void setNumberOfTablesAssigned(int numberOfTablesAssigned) { this.numberOfTablesAssigned = numberOfTablesAssigned; }


}
