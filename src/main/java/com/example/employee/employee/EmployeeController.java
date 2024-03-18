package com.example.employee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
public class EmployeeController  {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(value = "/employee")
    //@Cacheable(value = "employee",cacheManager = "cacheManager")
    public List<Employee> getEmployee(){
        return employeeService.getEmployee();
    }
    @PostMapping("/employee/{companyId}/save")
    public ResponseEntity<Employee> registerEmployee(@PathVariable(value = "companyId") Long id,@RequestBody Employee employee){
       return employeeService.addNewEmployee(id,employee);
    }

    @DeleteMapping(path = "/employee/{employeeId}")
    //@CacheEvict(value = "employee",key = "#p0")
    public Map<String, Boolean> deleteEmployee(@PathVariable ("employeeId") Long id){
        return  employeeService.deleteEmployee(id);
    }
    @DeleteMapping(path = "/employee/list")
    //@CacheEvict(value = "employee",key = "#p0")
    public Map<String, Boolean> deleteEmployee(){
        return  employeeService.deleteEmployeeAll(List.of(1L,2L));
    }
    @PutMapping(path = "/employee/{employeeId}")
    //@CacheEvict(cacheNames = "employee",key = "#p0")
    public ResponseEntity<Employee> updateEmployee(@PathVariable ("employeeId") Long id,
                                                   @RequestBody Employee employee){
        return employeeService.updateEmployee(id,employee);
    }

    @GetMapping(path = "/employee/{employeeId}")
    //@Cacheable(cacheNames = "employee",key = "#p0",cacheManager = "cacheManager")
    public Optional<Employee> findEmployeeById (@PathVariable ("employeeId") Long id){
        return employeeService.findEmployeeById(id);
    }
    @GetMapping(path = "/employee/find/{email}")
    public List<Employee> findEmployeeEmail (@PathVariable ("email") String email){
        return employeeService.findEmployeeByEmail(email);
    }
    @GetMapping(path = "/employee/find/age/{age}")
    public List<Employee> findEmployee (@PathVariable ("age") Long email){
        return employeeService.findEmployeeByAge(email);
    }
}
