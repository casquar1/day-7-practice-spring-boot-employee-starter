package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.getAge() < 18) {
            throw new EmployeeCreateException();
        }
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findById(id);
        matchedEmployee.setActive(Boolean.FALSE);
        employeeRepository.updateAnEmployeeById(id, matchedEmployee);
    }

    public void update(Long id) {
        Employee matchedEmployee = employeeRepository.findById(id);
        if (matchedEmployee.isActive()) {
            employeeRepository.updateAnEmployeeById(id, matchedEmployee);
        }
    }
}
