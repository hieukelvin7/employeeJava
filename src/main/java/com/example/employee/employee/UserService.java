package com.example.employee.employee;

import org.springframework.http.HttpHeaders;

public interface UserService {
    HttpHeaders login(LoginRequest loginRequest) throws Exception;
    String register(RegisterRequest registerRequest) throws Exception;
}
