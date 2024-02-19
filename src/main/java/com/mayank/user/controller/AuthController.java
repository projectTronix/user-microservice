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

import java.util.Optional;
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
    @PutMapping("/forget_password")
    public ResponseEntity<String> resetPassword(@RequestBody ForgetPasswordRequest request) {
        try {
            Optional<User> user = userService.getUserByEmail(request.getEmail());
            if(user.isEmpty()) {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
            ResponseEntity<String> response = service.updatePassword(user.get().getId(), request);
            if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                logger.log(Level.WARNING, "Encountered a problem while updating password.");
                throw new Exception("Encountered a problem while updating password.");
            }
            return new ResponseEntity<>("password updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Encountered a problem while updating password.", HttpStatus.BAD_REQUEST);
        }
    }
}
