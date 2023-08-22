package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findEmployeeByGender(@RequestParam String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addAnEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateAnEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        Employee updatedEmployee = employeeRepository.updateAnEmployeeById(employee, id);
        return new ResponseEntity<>(updatedEmployee.getName() + "'s profile was updated.", HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAnEmployeeById(@PathVariable Long id) {
        Employee toBeRemovedEmployee = employeeRepository.findById(id);
        if (toBeRemovedEmployee == null) {
            throw new EmployeeNotFoundException();
        }
        employeeRepository.deleteAnEmployeeById(toBeRemovedEmployee);
        return new ResponseEntity<String> (toBeRemovedEmployee.getName() + "'s record was deleted from the employee list.", HttpStatus.CREATED);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeeByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeRepository.listEmployeeByPage(pageNumber, pageSize);
    }
}
