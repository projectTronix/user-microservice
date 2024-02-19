package com.mayank.user.service;

import com.mayank.user.dto.AuthenticationRequest;
import com.mayank.user.dto.AuthenticationResponse;
import com.mayank.user.dto.ForgetPasswordRequest;
import com.mayank.user.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    ResponseEntity<String> updatePassword(Integer userID, ForgetPasswordRequest request);
}
