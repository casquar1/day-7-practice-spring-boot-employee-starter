package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getCompanyList() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company findCompanyById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long id) {
        return companyService.findEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompanyByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyService.listCompanyByPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addACompany(@RequestBody Company company) {
        return companyService.create(company);
    }

    @PutMapping("{id}")
    public Company updateACompany(@RequestBody Company company, @PathVariable Long id) {
        return companyService.update(company, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteACompanyId(@PathVariable Long id) {
        companyService.delete(id);
    }
}
