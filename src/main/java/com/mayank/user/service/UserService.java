package com.mayank.user.service;

import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUsers() throws Exception;
    User getUserByID(Integer UserId) throws Exception;
    User getUserByEmail(String email) throws Exception;
    boolean deleteUserByEmail(String email) throws Exception;
    String extractEmailFromRequest(HttpServletRequest request) throws Exception;
}
