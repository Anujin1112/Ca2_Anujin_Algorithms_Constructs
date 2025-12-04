/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ca2.model;

public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;

    private Department department;
    private Integer managerId; // Store only the ID, not the full object

    public Employee() {}

    public Employee(int employeeId, String firstName, String lastName, String email,
                    String jobTitle, Department department, Integer managerId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.department = department;
        this.managerId = managerId;
    }

    // Getters and setters (same as before, but for managerId instead of Manager)
    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
