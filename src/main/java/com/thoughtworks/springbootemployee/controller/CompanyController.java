package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getCompanyList() {
        return companyRepository.listAll();
    }

    @GetMapping("/{id}")
    public Company findCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long id) {
        return companyRepository.getEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompanyByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyRepository.listCompanyByPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addACompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("{id}")
    public Company updateACompany(@RequestBody Company company, @PathVariable Long id) {
        return companyRepository.updateACompanyById(company, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteACompanyId(@PathVariable Long id) {
        Company toBeRemovedCompany = companyRepository.findById(id);
        companyRepository.deleteACompanyById(toBeRemovedCompany);
        return new ResponseEntity<String> (toBeRemovedCompany.getName() + " was deleted from the company list.", HttpStatus.NO_CONTENT);
    }
}
