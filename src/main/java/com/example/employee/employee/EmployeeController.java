package com.example.employee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployee(){
        return employeeService.getEmployee();
    }
    @PostMapping
    public Employee registerEmployee(@RequestBody Employee employee){
       return employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path = "{employeeId}")
    public Map<String, Boolean> deleteEmployee(@PathVariable ("employeeId") Long id){
        return  employeeService.deleteEmployee(id);
    }
    @PutMapping(path = "{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable ("employeeId") Long id,
                                                   @RequestParam(required = false) String name, @RequestParam(required = false) String email){
        return employeeService.updateEmployee(id,name , email);
    }

    @GetMapping(path = "{employeeId}")
    public Optional<Employee> findEmployeeById (@PathVariable ("employeeId") Long id){
        return employeeService.findEmployeeById(id);
    }
}
