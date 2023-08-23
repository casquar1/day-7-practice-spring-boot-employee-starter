package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_service_and_employee_with_valid_age() {
    //given
        Employee employee = new Employee(null, 1L, "Kate", 23, "Female", 5000, true);
        Employee savedEmployee = new Employee(1L, 1L, "Kate", 23, "Female", 5000, true);
        when(mockedEmployeeRepository.save(employee)).thenReturn(savedEmployee);
     
     //when
        Employee employeeResponse = employeeService.create(employee);
     
     //then
        assertEquals(savedEmployee.getEmployeeId(), employeeResponse.getEmployeeId());
        assertEquals(1L, employeeResponse.getCompanyId());
        assertEquals("Kate", employeeResponse.getName());
        assertEquals(23, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(5000, employeeResponse.getSalary());
        assertEquals(true, employeeResponse.isActive());
    }
    
    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_less_than_18() {
    //given
        Employee employee = new Employee(null, 1L, "Kate", 16, "Female", 5000, true);
     
     //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () ->
                employeeService.create(employee));
     
     //then
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }
    
    @Test
    void should_set_active_false_when_delete_given_employee_service_and_employee_id() {
    //given
        Employee employee = new Employee(1L, 1L, "Kate", 23, "Female", 5000, true);
        employee.setActive(Boolean.FALSE);
        when(mockedEmployeeRepository.findById(employee.getEmployeeId()))
                .thenReturn(employee);
     
     //when
        employeeService.delete(employee.getEmployeeId());
     
     //then
        verify(mockedEmployeeRepository).updateAnEmployeeById(eq(employee.getEmployeeId()), argThat(tempEmployee -> {
            assertFalse(tempEmployee.isActive());
            assertEquals(1L, tempEmployee.getEmployeeId());
            assertEquals(1L, tempEmployee.getCompanyId());
            assertEquals("Kate", tempEmployee.getName());
            assertEquals(23, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(5000, tempEmployee.getSalary());
            return true;
        }));
    }
    
    @Test
    void should_return_updated_employee_when_update_given_employee_service_and_employee_service() {
    //given
        Employee employee = new Employee(1L, 1L, "Kate", 23, "Female", 5000, true);
        employee.setAge(33);
        employee.setSalary(5500);
        when(mockedEmployeeRepository.findById(employee.getEmployeeId()))
                .thenReturn(employee);

     //when
        employeeService.update(employee.getEmployeeId());
     
     //then
        verify(mockedEmployeeRepository).updateAnEmployeeById(eq(employee.getEmployeeId()), argThat(tempEmployee -> {
            assertEquals(1L, tempEmployee.getEmployeeId());
            assertEquals(1L, tempEmployee.getCompanyId());
            assertEquals("Kate", tempEmployee.getName());
            assertEquals(33, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(5500, tempEmployee.getSalary());
            assertTrue(tempEmployee.isActive());
            return true;
        }));
    }
}
