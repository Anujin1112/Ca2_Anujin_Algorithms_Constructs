/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ca2.model;

import java.util.Objects;

public class Manager {
    private String managerType;
    private Department department;

    public Manager() {}

    public Manager(String managerType, Department department) {
        this.managerType = managerType;
        this.department = department;
    }

    public String getManagerType() { return managerType; }
    public void setManagerType(String managerType) { this.managerType = managerType; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    @Override
    public String toString() {
        return managerType + " Manager, Department: " + 
               (department != null ? department.getName() : "No Department");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(managerType, manager.managerType) &&
               Objects.equals(department, manager.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerType, department);
    }
}
