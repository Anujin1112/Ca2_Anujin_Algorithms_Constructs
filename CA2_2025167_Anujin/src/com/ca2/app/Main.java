/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ca2.app;

import com.ca2.service.EmployeeService;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private enum MenuOption {
        SORT(1),
        SEARCH(2),
        ADD(3),
        GENERATE_RANDOM(4),
        EXIT(5);

        private final int value;

        MenuOption(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static MenuOption fromInt(int value) {
            for (MenuOption option : values()) {
                if (option.getValue() == value) {
                    return option;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeService();

        System.out.println("=======================================");
        System.out.println("   Bank Staff Management System");
        System.out.println("=======================================");

        System.out.print("Enter filename to load (e.g., Applicants_Form.txt): ");
        String fileName = scanner.nextLine();

        try {
            employeeService.loadFromFile(fileName);
            System.out.println("Data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            System.out.println("Starting with empty employee list.");
        }

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(scanner);
            MenuOption option = MenuOption.fromInt(choice);
            
            if (option == null) {
                System.out.println("Invalid option. Try again.");
                continue;
            }

            switch (option) {
                case SORT:
                    if (employeeService.hasEmployees()) {
                        employeeService.sortEmployeesByLastName();
                        System.out.println("\nSorted employees (first 20):");
                        employeeService.displayEmployees(20);
                    } else {
                        System.out.println("No employees to sort.");
                    }
                    break;
                    
                case SEARCH:
                    if (employeeService.hasEmployees()) {
                        System.out.print("Enter full name to search: ");
                        scanner.nextLine();
                        String name = scanner.nextLine();
                        Employee result = employeeService.searchEmployeeByName(name);
                        if (result != null) {
                            System.out.println("\nEmployee found:");
                            System.out.println("ID: " + result.getEmployeeId());
                            System.out.println("Name: " + result.getFullName());
                            System.out.println("Email: " + result.getEmail());
                            System.out.println("Job Title: " + result.getJobTitle());
                            System.out.println("Department: " + result.getDepartment().getName());
                            System.out.println("Manager: " + result.getManager());
                        } else {
                            System.out.println("Employee not found.");
                        }
                    } else {
                        System.out.println("No employees loaded.");
                    }
                    break;
                    
                case ADD:
                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Job Title: ");
                    String jobTitle = scanner.nextLine();
                    System.out.print("Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Manager Type (Head Manager/Assistant Manager/Team Lead): ");
                    String managerType = scanner.nextLine();
                    
                    if (employeeService.addNewEmployee(firstName, lastName, email, jobTitle, department, managerType)) {
                        System.out.println("Employee added successfully!");
                    } else {
                        System.out.println("Failed to add employee. Check required fields.");
                    }
                    break;
                    
                case GENERATE_RANDOM:
                    employeeService.generateRandomEmployee();
                    System.out.println("Random employee generated and added.");
                    break;
                    
                case EXIT:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
            }
            
            System.out.println();
        }
        
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("--------------- MENU ---------------");
        System.out.println("1. Sort employees by last name");
        System.out.println("2. Search employee by name");
        System.out.println("3. Add new employee");
        System.out.println("4. Generate random employee");
        System.out.println("5. Exit");
        System.out.print("Select option (1-5): ");
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number (1-5): ");
            }
        }
    }
}
