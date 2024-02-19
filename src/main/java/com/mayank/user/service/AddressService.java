package com.mayank.user.service;

import com.mayank.user.dto.Address;
import com.mayank.user.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {
    ResponseEntity<String> saveAddress(User user, Address address);
    List<Address> getAllAddresses(Integer userID);
    ResponseEntity<String> deleteAddress(Integer userID, Integer addressID);
    Optional<Address> findAddressByID(Integer addressID) throws Exception;
}
