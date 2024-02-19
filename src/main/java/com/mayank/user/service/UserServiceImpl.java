package com.mayank.user.service;

import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import com.mayank.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        List<User> users =  userRepository.findAll();
        if(users.isEmpty()) {
            throw new UserNotFoundException("No users found.");
        }
        return users;
    }
    @Override
    public Optional<User> getUserByID(Integer userID) throws UserNotFoundException{
        Optional<User> opt = userRepository.findById(userID);
        if(opt.isEmpty()) {
            throw new UserNotFoundException("Invalid Email ID or password.");
        }
        return opt;
    }

    @Override
    public ResponseEntity<String> updatePassword(Integer userID, String newPassword) throws UserNotFoundException {
        try {
            if(getUserByID(userID).isEmpty()) {
                throw new UserNotFoundException("Invalid Email ID or password.");
            }
            userRepository.updatePasswordByID(newPassword, userID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
