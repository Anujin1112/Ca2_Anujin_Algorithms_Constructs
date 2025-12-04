/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ca2.service;

import com.ca2.model.Department;
import com.ca2.model.Employee;
import com.ca2.model.Manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private final Map<String, Department> departmentMap = new HashMap<>();
    private final Random random = new Random();
    private int nextEmployeeId = 1;

    public EmployeeService() {
        addDepartment("IT Development");
        addDepartment("HR");
        addDepartment("Sales");
        addDepartment("Finance");
        addDepartment("Marketing");
        addDepartment("Customer Service");
        addDepartment("Operations");
        addDepartment("Accounting");
    }

    private void addDepartment(String name) {
        departmentMap.put(name.toLowerCase(), new Department(name));
    }

    private Department getOrCreateDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            name = "Unknown";
        }
        String key = name.trim().toLowerCase();
        if (!departmentMap.containsKey(key)) {
            departmentMap.put(key, new Department(name.trim()));
        }
        return departmentMap.get(key);
    }

    public void loadFromFile(String fileName) throws IOException {
        employees.clear();
        nextEmployeeId = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 8) continue;

                String firstName = parts[0].trim();
                String lastName = parts[1].trim();
                String email = parts[3].trim();
                String departmentName = parts[5].trim();
                String jobTitle = parts[7].trim();

                if (firstName.isEmpty() || lastName.isEmpty()) continue;

                Department department = getOrCreateDepartment(departmentName);
                Manager manager = new Manager(getRandomManagerType(), department);
                
                employees.add(new Employee(
                    nextEmployeeId++,
                    firstName,
                    lastName,
                    email,
                    jobTitle,
                    department,
                    manager
                ));
            }
        }
    }

    private String getRandomManagerType() {
        String[] types = {"Head Manager", "Assistant Manager", "Team Lead"};
        return types[random.nextInt(types.length)];
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public void sortEmployeesByLastName() {
        if (employees.isEmpty()) return;
        
        Employee[] employeeArray = employees.toArray(new Employee[0]);
        mergeSort(employeeArray, 0, employeeArray.length - 1);
        
        employees.clear();
        employees.addAll(Arrays.asList(employeeArray));
    }

    private void mergeSort(Employee[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(Employee[] arr, int left, int mid, int right) {
        Employee[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        Employee[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i].getLastName().compareToIgnoreCase(rightArr[j].getLastName()) <= 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        while (i < leftArr.length) {
            arr[k++] = leftArr[i++];
        }
        
        while (j < rightArr.length) {
            arr[k++] = rightArr[j++];
        }
    }

    public Employee searchEmployeeByName(String name) {
        if (employees.isEmpty()) return null;
        
        sortEmployeesByLastName();
        
        int left = 0;
        int right = employees.size() - 1;
        String target = name.trim().toLowerCase();
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String employeeName = employees.get(mid).getFullName().toLowerCase();
            int compare = employeeName.compareTo(target);
            
            if (compare == 0) {
                return employees.get(mid);
            } else if (compare < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return null;
    }

    public boolean addNewEmployee(String firstName, String lastName, String email, 
                                 String jobTitle, String departmentName, String managerType) {
        if (firstName == null || lastName == null || firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            return false;
        }
        
        if (email == null || email.trim().isEmpty()) {
            email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
        }
        
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            jobTitle = "Employee";
        }
        
        if (managerType == null || managerType.trim().isEmpty()) {
            managerType = getRandomManagerType();
        }
        
        Department department = getOrCreateDepartment(departmentName);
        Manager manager = new Manager(managerType, department);
        
        Employee employee = new Employee(
            nextEmployeeId++,
            firstName.trim(),
            lastName.trim(),
            email.trim(),
            jobTitle.trim(),
            department,
            manager
        );
        
        return employees.add(employee);
    }

    public void generateRandomEmployee() {
        String[] firstNames = {"John", "Emily", "Michael", "Sophia", "David", "Olivia", "James", "Emma", "Robert", "Charlotte"};
        String[] lastNames = {"Smith", "Brown", "Wilson", "Taylor", "Anderson", "Clark", "Johnson", "Miller", "Davis", "Moore"};
        String[] jobTitles = {"Teller", "Consultant", "Analyst", "Clerk", "Assistant", "Supervisor", "Coordinator", "Specialist"};
        
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@bank.com";
        String jobTitle = jobTitles[random.nextInt(jobTitles.length)];
        
        List<Department> departments = new ArrayList<>(departmentMap.values());
        Department dept = departments.get(random.nextInt(departments.size()));
        
        addNewEmployee(firstName, lastName, email, jobTitle, dept.getName(), getRandomManagerType());
    }

    public boolean hasEmployees() {
        return !employees.isEmpty();
    }

    public void displayEmployees(int count) {
        int displayCount = Math.min(count, employees.size());
        for (int i = 0; i < displayCount; i++) {
            Employee e = employees.get(i);
            System.out.printf("%3d. %-15s %-15s %-25s %-15s%n",
                e.getEmployeeId(),
                e.getFirstName(),
                e.getLastName(),
                e.getDepartment().getName(),
                e.getJobTitle());
        }
    }
}
