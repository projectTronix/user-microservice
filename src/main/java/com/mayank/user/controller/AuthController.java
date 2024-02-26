package com.mayank.user.controller;

import com.mayank.user.dto.*;
import com.mayank.user.service.AuthenticationService;
import com.mayank.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    private final UserService userService;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            String emailID = request.getEmail();
            String password = request.getPassword();
            if(emailID.isBlank() || password.isBlank()) {
                logger.log(Level.WARNING, "Email ID or Password is Empty.");
                throw new Exception("Email ID or Password is Empty.");
            }
            logger.log(Level.INFO, "User registered successfully.");
            return ResponseEntity.ok(service.register(request));
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>("Encountered a problem while registering user.", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        try {
            String emailID = request.getEmail();
            String password = request.getPassword();
            if(emailID.isBlank() || password.isBlank()) {
                logger.log(Level.WARNING, "Email ID or Password is Empty.");
                throw new Exception("Email ID or Password is Empty.");
            }
            logger.log(Level.INFO, "User Logged in successfully.");
            return ResponseEntity.ok(service.authenticate(request));
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>("Invalid Credentials. Please Try again.", HttpStatus.BAD_REQUEST);
        }
    }
}
