package com.example.employee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/user/register")
    public String register(@RequestBody RegisterRequest registerRequest) throws Exception {
      return userService.register(registerRequest);
    }
    @PostMapping("/user/login")
    public HttpHeaders login(@RequestBody LoginRequest loginRequest) throws Exception {
        return userService.login(loginRequest);
    }
}
