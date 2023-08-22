package com.thoughtworks.springbootemployee.model;

public class Employee {

    private final Long employeeId;
    private final Long companyId;
    private final String name;
    private Integer age;
    private final String gender;
    private Integer salary;

    public Employee(Long employeeId, Long companyId, String name, Integer age, String gender, Integer salary) {
        this.employeeId = employeeId;
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
        return employeeId;
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
}
