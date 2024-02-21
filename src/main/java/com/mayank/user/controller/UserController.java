package com.mayank.user.controller;

import com.mayank.user.dto.CustomResponse;
import com.mayank.user.dto.DeleteUserRequest;
import com.mayank.user.dto.User;
import com.mayank.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
            List<User> users = userService.getAllUsers();
            logger.log(Level.INFO, "Users fetched successfully.");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching users. - {}", e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @GetMapping("/info/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer userId) {
        try {
            User user = userService.getUserByID(userId);
            logger.log(Level.INFO, "User fetched successfully.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching user. - {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/details")
    public ResponseEntity<User> getUserDetails(HttpServletRequest request) {
        try {
            String userEmail = userService.extractEmailFromRequest(request);
            User user = userService.getUserByEmail(userEmail);
            logger.log(Level.INFO, "User details fetched successfully.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching user.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete")
    public CustomResponse deleteUser(@RequestBody @Valid DeleteUserRequest request) {
        try {
            boolean status = userService.deleteUserByEmail(request.getEmail());
            if(!status) {
                throw new Exception();
            }
            logger.log(Level.INFO, "User Deleted successfully.");
            return new CustomResponse("User Deleted Successfully.", HttpStatus.OK);
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while deleting the user. - {}", e.getMessage());
            return new CustomResponse("Encountered a problem while deleting the user.", HttpStatus.NOT_FOUND);
        }
    }
}
