package com.example.employee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;

    @Autowired
    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Employee> getEmployee(){
        return repo.findAll();
    }

    public Employee addNewEmployee(Employee employee) {
         Optional<Employee> employeeByEmail = repo.findEmployeeByEmail(employee.getEmail());
        if(employeeByEmail.isPresent()){
            throw new IllegalStateException("Email has been register");
        }
        return repo.save(employee);
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

//    @Transactional
//    public void updateEmployee(Long id, String name, String email) {
//        Employee employee = repo.findById(id).orElseThrow(()->new IllegalStateException("Id "+ id + "doesn't exits"));
//        if(name != null && name.length() >0 && !Objects.equals(employee.getName(),name)){
//            employee.setName(name);
//        }
//        if(email != null && email.length() >0 && !Objects.equals(employee.getEmail(),email)){
//            Optional <Employee> employeeOptional = repo.findEmployeeByEmail(email);
//            if (employeeOptional.isPresent()){
//                throw new IllegalStateException("Email exist");
//            }
//            employee.setEmail(email);
//        }
//    }

    public Optional<Employee> findEmployeeById(Long id) {
        boolean exist = repo.existsById(id);
        if(!exist){
            throw new IllegalStateException("Id " + id+ " does not exist!!!");
        }
        return repo.findById(id);
    }

    public ResponseEntity<Employee> updateEmployee(Long id, String name, String email) {
        Employee emp = repo.findById(id).orElseThrow(()->new IllegalStateException("Id " +id + " doesn't exit"));
        if(name != null && name.length() >0 && !Objects.equals(emp.getName(),name)){
            emp.setName(name);
        }
        if(email != null && email.length() >0 && !Objects.equals(emp.getEmail(),email)){
            Optional <Employee> employeeOptional = repo.findEmployeeByEmail(email);
            if (employeeOptional.isPresent()){
                throw new IllegalStateException("Email exist");
            }
            emp.setEmail(email);
        }
        return ResponseEntity.ok(repo.save(emp));
    }
}
