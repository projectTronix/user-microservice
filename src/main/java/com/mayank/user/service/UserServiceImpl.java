package com.mayank.user.service;

import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import com.mayank.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    public Optional<User> getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> opt = userRepository.findBYEmail(email);
        if(opt.isEmpty()) {
            throw new UserNotFoundException("Invalid Email ID.");
        }
        return opt;
    }
}
