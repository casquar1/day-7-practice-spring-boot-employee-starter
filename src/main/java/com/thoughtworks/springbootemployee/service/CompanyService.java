package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company findById(Long id) {
        return companyRepository.findById(id);
    }

    public List<Employee> findEmployeesByCompanyId(Long id) {
        return companyRepository.findEmployeesByCompanyId(id);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Company> findByPage(Long pageNumber, Long pageSize) {
        return companyRepository.findByPage(pageNumber, pageSize);
    }

    public Company create(Company company) {
        return companyRepository.create(company);
    }

    public Company update(Company company, Long id) {
        return companyRepository.update(company, id);
    }

    public void delete(Long id) {
        Company matchedCompany = companyRepository.findById(id);
        companyRepository.delete(matchedCompany);
    }
}
