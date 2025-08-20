package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;
import com.example.demo.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;




import java.util.List;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // ⚠️ In real apps, you should verify the username/password from DB
        if ("admin".equals(username) && "admin123".equals(password)) {
            String token = jwtService.generateToken(username,"ADMIN");
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}


