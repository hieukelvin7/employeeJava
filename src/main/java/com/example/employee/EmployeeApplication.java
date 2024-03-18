package com.example.employee;

import com.example.employee.employee.Company;
import com.example.employee.employee.CompanyRepository;
import com.example.employee.employee.Employee;
import com.example.employee.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RestController
@EnableCaching
public class EmployeeApplication{

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}


}
