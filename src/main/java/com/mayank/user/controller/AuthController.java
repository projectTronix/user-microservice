package com.mayank.user.controller;

import com.mayank.user.dto.AuthenticationRequest;
import com.mayank.user.dto.AuthenticationResponse;
import com.mayank.user.dto.RegisterRequest;
import com.mayank.user.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        try {
            String emailID = request.getEmail();
            String password = request.getPassword();
            if(emailID.isBlank() || password.isBlank()) {
                logger.log(Level.WARNING, "Email ID or Password is Empty.");
                throw new Exception("Email ID or Password is Empty.");
            }
            return ResponseEntity.ok(service.register(request));
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            String emailID = request.getEmail();
            String password = request.getPassword();
            if(emailID.isBlank() || password.isBlank()) {
                logger.log(Level.WARNING, "Email ID or Password is Empty.");
                throw new Exception("Email ID or Password is Empty.");
            }
            return ResponseEntity.ok(service.authenticate(request));
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
