package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Employee {

    private Long employeeId;
    private final Long companyId;
    private final String name;
    private Integer age;
    private final String gender;
    private Integer salary;
    private Boolean isActive;

    public Employee(Long employeeId, Long companyId, String name, Integer age, String gender, Integer salary, Boolean isActive) {
        this.employeeId = employeeId;
        this.companyId = companyId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.isActive = isActive;
    }

    @JsonCreator
    public Employee(Long companyId, String name, Integer age, String gender, Integer salary) {
        this.companyId = companyId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
    
    public Long getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean isActive() {
        return isActive;
    }
}
