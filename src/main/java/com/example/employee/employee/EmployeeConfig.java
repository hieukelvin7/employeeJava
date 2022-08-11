package com.example.employee.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EmployeeConfig {
    @Bean
    CommandLineRunner commandLineRunner (
            EmployeeRepository employeeRepository){
        return  args -> {
           Employee nva = new Employee(
                    "Nguyen Van a",
                    "nva@gmail.com",
                    "Ho chi minh",
                    21
            );
            Employee nvb = new Employee(
                    "tran thi c",
                    "ttt@gmail.com",
                    "Ha noi",
                    40
            );
            employeeRepository.saveAll(List.of(nva,nvb));
        };
    }
}
