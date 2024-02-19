package com.mayank.user.service;

import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUsers() throws UserNotFoundException;
    Optional<User> getUserByID(Integer UserId) throws UserNotFoundException;
    ResponseEntity<String> updatePassword(Integer userID, String newPassword) throws UserNotFoundException;
}