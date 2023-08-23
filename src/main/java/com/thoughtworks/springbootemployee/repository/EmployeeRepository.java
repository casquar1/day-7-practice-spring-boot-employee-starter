package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();
    private static final long START_ID_MINUS_ONE = 0L;
    private static final long ID_INCREMENT = 1;

    static {
        employees.add(new Employee(1L, 1L, "Alice", 30, "Female", 5000, true));
        employees.add(new Employee(2L, 2L,"Bob", 31, "Male", 3000, true));
        employees.add(new Employee(3L, 3L,"Carl", 32, "Male", 4500, true));
        employees.add(new Employee(4L, 4L,"David", 33, "Male", 3500, true));
        employees.add(new Employee(5L, 5L,"Ellen", 34, "Female", 7000, true));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee save(Employee employee) {
        Long id = generateNextId();
        Employee toBeSaveEmployee = new Employee(id, employee.getCompanyId(), employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary(), employee.isActive());
        toBeSaveEmployee.setActive(true);
        employees.add(toBeSaveEmployee);
        return toBeSaveEmployee;
    }

    private Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getEmployeeId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public Employee updateAnEmployeeById(Long id, Employee updatedEmployee) {
        Employee employee = this.findById(id);
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        return employee;
    }

    public void deleteAnEmployeeById(Employee employee) {
        employees.remove(employee);
    }

    public List<Employee> listEmployeeByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void cleanAll() {
        employees.clear();
    }
}
