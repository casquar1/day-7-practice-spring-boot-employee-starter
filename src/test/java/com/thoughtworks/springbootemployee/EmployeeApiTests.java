package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
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
        Employee alice = employeeRepository.save(new Employee(1L, "Alice", 30, "Female", 5000));
     
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
        Employee alice = employeeRepository.save(new Employee(1L, "Alice", 30, "Female", 5000));
        employeeRepository.save(new Employee(2L, "Bob", 28, "Male", 5000));

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

    @Test
    void should_return_404_not_found_when_perform_given_a_not_existing_id() throws  Exception {
    //given
        Long notExistingId = 99L;

     //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistingId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void should_return_the_employees_by_given_gender_when_perform_get_employees() throws Exception {
    //given
        Employee alice = employeeRepository.save(new Employee(1L, "Alice", 30, "Female", 5000));
        employeeRepository.save(new Employee(2L, "Bob", 28, "Male", 5000));
     
     //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender", "Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employeeId").value(alice.getEmployeeId()))
                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()));
     
     //then
    }

    @Test
    void should_return_the_employee_created_when_perform_post_employees_given_new_employee_with_JSON_format() throws Exception {
        //given
            Employee newEmployee = employeeRepository.save(new Employee(1L, "Alice", 30, "Female", 5000));

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(notNullValue()))
                .andExpect(jsonPath("$.companyId").value(newEmployee.getCompanyId()))
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(newEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(newEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()));
    }
    
    @Test
    void should_return_updated_employee_age_and_salary_when_perform_put_employee_given_an_employee_id() throws Exception {
    //given
        Employee employee = employeeRepository.save(new Employee(1L, "Alice", 30, "Female", 5000));
        String updatedEmployee =    "{\n" +
                "     \"age\": 33, \n" +
                "     \"salary\": 5500 \n" +
                "}";

     //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/employees/" + employee.getEmployeeId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(employee.getEmployeeId()))
                .andExpect(jsonPath("$.companyId").value(employee.getCompanyId()))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.age").value(33))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(5500));
    }
    
    @Test
    void should_return_no_content_status_when_delete_employee_given_an_employee_id_to_be_deleted() throws Exception {
    //given
        Employee employee = employeeRepository.save(new Employee(1L, "Alice", 30, "Female", 5000));
     
     //when
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/" + employee.getEmployeeId()))
                .andExpect(status().isNoContent());
    }
}
