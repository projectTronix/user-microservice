package com.mayank.user.service;

import com.mayank.user.config.JwtService;
import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import com.mayank.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public List<User> getAllUsers() throws Exception {
        try {
            List<User> users =  userRepository.findAll();
            if(users.isEmpty()) {
                throw new UserNotFoundException("No users found.");
            }
            return users;
        } catch(Exception e) {
            logger.log(Level.INFO, e.getMessage());
            throw new Exception("Error while fetching users.");
        }
    }
    @Override
    public User getUserByID(Integer userID) throws Exception {
        try {
            Optional<User> opt = userRepository.findById(userID);
            if(opt.isEmpty()) {
                throw new UserNotFoundException("Invalid Email ID or password.");
            }
            return opt.get();
        } catch(Exception e) {
            logger.log(Level.INFO, e.getMessage());
            throw new Exception("Error while fetching user by ID.");
        }
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        try {
            Optional<User> opt = userRepository.findBYEmail(email);
            if(opt.isEmpty()) {
                throw new UserNotFoundException("Invalid Email ID.");
            }
            return opt.get();
        } catch(Exception e) {
            logger.log(Level.INFO, e.getMessage());
            throw new Exception("Error while fetching user by Email.");
        }
    }
    @Transactional
    @Override
    public boolean deleteUserByEmail(String email) throws Exception {
        try {
            long status = userRepository.deleteByEmail(email);
            return status != 0;
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public String extractEmailFromRequest(HttpServletRequest request) throws Exception {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String userEmail;
            final String jwt;
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            return userEmail;
        } catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
