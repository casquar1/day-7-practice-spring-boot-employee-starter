package com.thoughtworks.springbootemployee.controller;

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
    public Employee updateAnEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        return employeeRepository.updateAnEmployeeById(employee, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAnEmployeeById(@PathVariable Long id) {
        Employee toBeRemovedEmployee = employeeRepository.findById(id);
        employeeRepository.deleteAnEmployeeById(toBeRemovedEmployee);
        return new ResponseEntity<String> (toBeRemovedEmployee.getName() + "'s record was deleted from the employee list.", HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeeByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeRepository.listEmployeeByPage(pageNumber, pageSize);
    }
}
