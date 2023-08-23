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
        return companyRepository.getEmployeesByCompanyId(id);
    }

    public List<Company> findAll() {
        return companyRepository.listAll();
    }

    public List<Company> listCompanyByPage(Long pageNumber, Long pageSize) {
        return companyRepository.listCompanyByPage(pageNumber, pageSize);
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company update(Company company, Long id) {
        return companyRepository.updateACompanyById(company, id);
    }

    public void delete(Long id) {
        Company matchedCompany = companyRepository.findById(id);
        companyRepository.deleteACompanyById(matchedCompany);
    }
}
