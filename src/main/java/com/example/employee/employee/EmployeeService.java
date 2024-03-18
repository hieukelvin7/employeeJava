package com.example.employee.employee;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@CacheConfig(cacheNames = "EmployeeCache")
public class EmployeeService  {
    private final EmployeeRepository repo;
    private final CompanyRepository companyRepository;
    private final EntityManager entityManager;

    @Autowired
    public EmployeeService(EmployeeRepository repo, CompanyRepository companyRepository, EntityManager entityManager) {
        this.repo = repo;
        this.companyRepository = companyRepository;
        this.entityManager = entityManager;
    }
    private RedisTemplate template ;


    //@Cacheable(cacheNames = "employees")
    public List<Employee> getEmployee(){
        System.out.println("Get employee is called");
//        Session session = entityManager.unwrap(Session.class);
//
//        // Enable the "deletedEmployeeFilter" filter and set the "isDeleted" parameter to false
//        session.enableFilter("deletedEmployeeFilter").setParameter("isDeleted", false);

        // Now, you can perform your query
        List<Employee> response = repo.findAll();

        // Disable the filter to avoid affecting subsequent queries
//        session.disableFilter("deletedEmployeeFilter");
        return response;
    }

    public ResponseEntity<Employee> addNewEmployee(Long id,Employee employee) {
         //Optional<Employee> employeeByEmail = repo.findEmployeeByEmail(employee.getEmail());
             if (Objects.nonNull(employee.getId())){
                Employee emp = repo.findById(employee.getId()).orElseThrow(()->new IllegalStateException("Id " +id + " doesn't exit"));
                if (Objects.nonNull(emp)){
                    emp.setAddress(employee.getAddress());
                    emp.setAge(employee.getAge());
                    emp.setCompany(employee.getCompany());
                    emp.setDeleted(employee.isDeleted());
                }
                repo.save(emp);
            }else {
                 Optional<Company> company = companyRepository.findById(id);
                 company.ifPresent(employee::setCompany);
                 repo.save(employee);
             }


        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }


    public Map<String, Boolean> deleteEmployee(Long id) {
       boolean exist = repo.existsById(id);
       if(!exist){
           throw new IllegalStateException("Id" + id+ "does not exist!!!");
       }
       repo.deleteById(id);
       Map<String, Boolean> response = new HashMap<>();
       response.put("Deleted", Boolean.TRUE);
       return response;
    }
    public Map<String, Boolean> deleteEmployeeAll(List<Long> id) {
        repo.deleteAllByIdIn(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }

    public Optional<Employee> findEmployeeById(Long id) {
        System.out.println("Get employee is called");
        //Session session = entityManager.unwrap(Session.class);

        // Enable the "deletedEmployeeFilter" filter and set the "isDeleted" parameter to false
        //session.enableFilter("deletedEmployeeFilter").setParameter("isDeleted", false);

        // Now, you can perform your query
        boolean exist = repo.existsById(id);

        // Disable the filter to avoid affecting subsequent queries
        //session.disableFilter("deletedEmployeeFilter");

        if(!exist){
            throw new IllegalStateException("Id " + id+ " does not exist!!!");
        }
        return repo.findById(id);
    }

    public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) {
        Employee emp = repo.findById(employee.getId()).orElseThrow(()->new IllegalStateException("Id " +id + " doesn't exit"));
        emp.setAddress(employee.getAddress());
        emp.setAge(employee.getAge());
        emp.setCompany(employee.getCompany());
        return ResponseEntity.ok(repo.save(emp));
    }

    public List<Employee> findEmployeeByEmail(String email){
        List<Employee> emp = repo.findEmployeeByEmailAndDeletedFalse(email);
        return emp;
    }
    public List<Employee> findEmployeeByAge(Long age){
        List<Employee> emp = repo.findEmployee(age);
        return emp;
    }

}
