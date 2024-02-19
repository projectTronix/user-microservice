package com.mayank.user.controller;

import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import com.mayank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("users/")
public class UserController {
    private final UserService userService;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch(UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer userId) {
        try {
            Optional<User> user = userService.getUserByID(userId);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch(UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // to do
    @PutMapping("/{id}/reset_password")
    public ResponseEntity<String> resetPassword(@PathVariable("id") Integer userId,@RequestBody String newPassword) {
        try {
            Optional<User> user = userService.getUserByID(userId);
            if(user.isEmpty()) {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
            ResponseEntity<String> response = userService.updatePassword(userId, newPassword);
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
