package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

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
}
