package com.example.employee.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    //List<Company> findByTitleContaining(String title);
}
