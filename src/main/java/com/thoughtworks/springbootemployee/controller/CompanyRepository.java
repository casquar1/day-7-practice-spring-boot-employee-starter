package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    @Autowired
    private EmployeeRepository employeeRepository;

    static {
        companies.add(new Company(1L, "Hobby Lobby"));
        companies.add(new Company(2L, "The Knickknack Shack"));
        companies.add(new Company(3L, "Puzzle Huddle"));
        companies.add(new Company(4L, "Superscapes"));
        companies.add(new Company(5L, "The Tiny Tassel"));
    }

    public List<Company> listAll() {
        return companies;
    }

    public Company findById(Long id) {
        return companies.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getEmployeesByCompanyId(Long id) {
        return employeeRepository.listAll()
                .stream()
                .filter(employee -> employee.getCompanyId().equals(id))
                .collect(Collectors.toList());
    }
}
