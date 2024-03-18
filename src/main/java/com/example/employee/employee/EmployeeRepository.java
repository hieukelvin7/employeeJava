package com.example.employee.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long>  {

    List<Employee> findEmployeeByEmailAndDeletedFalse(String email);

    @Query(value = "Select * from \"employee\" where age =?1 and deleted = false",nativeQuery = true)
    List<Employee> findEmployee(Long age);
    void deleteAllByIdIn(List<Long> id);
}
