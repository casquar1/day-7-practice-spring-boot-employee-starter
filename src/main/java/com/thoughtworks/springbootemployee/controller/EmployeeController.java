package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
//TODO: remove unused import statement
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> listAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findEmployeeByGender(@RequestParam String gender) {
        return employeeService.findByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addAnEmployee(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("{id}")
    public Employee updateAnEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnEmployeeById(@PathVariable Long id) {
        employeeService.delete(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeeByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeService.findByPage(pageNumber, pageSize);
    }
}
