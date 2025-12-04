/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ca2.model;

import java.util.Objects;

public class Department {
    private String name;

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

