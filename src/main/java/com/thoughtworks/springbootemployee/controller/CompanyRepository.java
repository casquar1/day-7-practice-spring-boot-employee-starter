package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    private static final long START_ID_MINUS_ONE = 0L;
    private static final long ID_INCREMENT = 1;
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

    public List<Company> listCompanyByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company save(Company company) {
        Long id = generateNextId();
        Company toBeSaveCompany = new Company(id, company.getName());
        companies.add(toBeSaveCompany);
        return toBeSaveCompany;
    }

    private Long generateNextId() {
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public void updateACompanyById(Company updateCompany, Long id) {
        Company company = this.findById(id);
        company.setName(updateCompany.getName());
    }
}
