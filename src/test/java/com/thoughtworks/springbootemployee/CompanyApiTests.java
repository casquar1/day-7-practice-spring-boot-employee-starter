package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyApiTests {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvcClient;

    @Autowired
    private EmployeeRepository employeeRepository;


    @BeforeEach
    void cleanupCompanyData() {
        companyRepository.cleanAll();
    }

    @BeforeEach
    void cleanupEmployeeData() {
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_all_given_companies_when_perform_get_companies() throws Exception {
        //given
        Company company = companyRepository.create(new Company(1L, "Book Depository"));

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(company.getId()))
                .andExpect(jsonPath("$[0].name").value(company.getName()));
    }

    @Test
    void should_return_the_company_when_perform_get_company_given_a_company_id() throws Exception {
        //given
        Company company = companyRepository.create(new Company(1L, "Book Depository"));
        companyRepository.create(new Company(2L, "Barnes and Nobles"));

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.name").value(company.getName()));
    }

    @Test
    void should_return_404_not_found_when_perform_given_a_not_existing_id() throws  Exception {
        //given
        Long notExistingId = 99L;

        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + notExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_the_created_company_when_perform_post_companies_given_new_company_with_JSON_format() throws Exception {
        //given
        Company newCompany = companyRepository.create(new Company(1L, "Book Depository"));

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(newCompany.getName()));
    }

    @Test
    void should_return_updated_company_when_perform_put_company_given_a_company_id() throws Exception {
        //given
        Company company = companyRepository.create(new Company(1L, "Book Depository"));
        String updatedCompany =    "{\n" +
                "     \"name\": \"Barnes and Nobles\"\n" +
                "}";

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/companies/" + company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Barnes and Nobles"));
    }

    @Test
    void should_return_no_content_status_when_delete_company_given_a_company_id_to_be_deleted() throws Exception {
        //given
        Company company = companyRepository.create(new Company(1L, "Book Depository"));

        //when
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/companies/" + company.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_list_of_companies_when_get_companies_given_pageNumber_and_pageSize() throws Exception {
        //given
        Company firstCompany = companyRepository.create(new Company(1L, "Book Depository"));
        Company secondCompany = companyRepository.create(new Company(2L, "Fully Booked"));
        companyRepository.create(new Company(3L, "Barnes and Nobles"));
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        Long pageNumber = 1L;
        Long pageSize = 2L;
        paramsMap.add("pageNumber", pageNumber.toString());
        paramsMap.add("pageSize", pageSize.toString());

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(firstCompany.getId()))
                .andExpect(jsonPath("$[0].name").value(firstCompany.getName()))
                .andExpect(jsonPath("$[1].id").value(secondCompany.getId()))
                .andExpect(jsonPath("$[1].name").value(secondCompany.getName()));
    }

    @Test
    void should_return_list_of_employees_when_get_employees_given_a_company_id() throws Exception {
        //given
        Company firstCompany = companyRepository.create(new Company(1L, "Book Depository"));
        Employee firstEmployee = employeeRepository.create(new Employee(1L, "Alice", 30, "Female", 5000));
        Employee secondEmployee = employeeRepository.create(new Employee(1L, "Bob", 37, "Male", 4500));

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + firstCompany.getId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId").value(firstEmployee.getEmployeeId()))
                .andExpect(jsonPath("$[0].companyId").value(firstEmployee.getCompanyId()))
                .andExpect(jsonPath("$[0].name").value(firstEmployee.getName()))
                .andExpect(jsonPath("$[0].age").value(firstEmployee.getAge()))
                .andExpect(jsonPath("$[0].gender").value(firstEmployee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(firstEmployee.getSalary()))
                .andExpect(jsonPath("$[1].employeeId").value(secondEmployee.getEmployeeId()))
                .andExpect(jsonPath("$[1].companyId").value(secondEmployee.getCompanyId()))
                .andExpect(jsonPath("$[1].name").value(secondEmployee.getName()))
                .andExpect(jsonPath("$[1].age").value(secondEmployee.getAge()))
                .andExpect(jsonPath("$[1].gender").value(secondEmployee.getGender()))
                .andExpect(jsonPath("$[1].salary").value(secondEmployee.getSalary()));
    }
}
