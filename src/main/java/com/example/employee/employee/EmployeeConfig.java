package com.example.employee.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EmployeeConfig {
    @Bean
    CommandLineRunner commandLineRunner (
            CompanyRepository companyRepository){

        return  args -> {
            Company company = new Company(1L,"a");
            Company company1 = new Company(2L,"b");
            companyRepository.save(company);
            companyRepository.save(company1);
        };
    }
}
