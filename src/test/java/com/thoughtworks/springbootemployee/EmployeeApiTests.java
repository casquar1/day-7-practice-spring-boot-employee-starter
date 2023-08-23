package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvcClient;
    @BeforeEach
    void cleanupEmployeeData() {
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_all_given_employees_when_perform_get_employees() throws Exception {
    //given
        Employee alice = employeeRepository.save(new Employee(1L, 1L, "Alice", 30, "Female", 5000));
     
     //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employeeId").value(alice.getEmployeeId()))
                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()));
    }

    @Test
    void should_return_the_employee_when_perform_get_employee_given_an_employee_id() throws Exception {
    //given
        Employee alice = employeeRepository.save(new Employee(1L, 1L, "Alice", 30, "Female", 5000));
        employeeRepository.save(new Employee(2L, 2L, "Alice", 28, "Male", 5000));

     //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getEmployeeId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(alice.getEmployeeId()))
                .andExpect(jsonPath("$.companyId").value(alice.getCompanyId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()));
    }
}
