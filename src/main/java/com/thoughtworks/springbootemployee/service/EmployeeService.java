package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeInactiveStatusException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.hasInvalidAge()) {
            throw new EmployeeCreateException();
        }
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findById(id);
        matchedEmployee.setActive(Boolean.FALSE);
        employeeRepository.updateAnEmployeeById(id, matchedEmployee);
    }

    public Employee update(Long id, Employee employee) {
        Employee matchedEmployee = employeeRepository.findById(id);
        if (!matchedEmployee.isActive()) {
            throw new EmployeeInactiveStatusException();
        }
        return employeeRepository.updateAnEmployeeById(id, employee);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll() {
        return employeeRepository.listAll();
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }

    public List<Employee> findByPage(Long pageNumber, Long pageSize) {
        return employeeRepository.listEmployeeByPage(pageNumber, pageSize);
    }
}
