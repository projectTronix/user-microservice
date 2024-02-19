package com.mayank.user.service;

import com.mayank.user.dto.Address;
import com.mayank.user.dto.User;
import com.mayank.user.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    public ResponseEntity<String> saveAddress(User user, Address address) {
        try {
            addressRepository.save(address);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    public List<Address> getAllAddresses(Integer userID) {
        List<Address> addresses =  addressRepository.findAllByUserID(userID);
        if(addresses.isEmpty()) {
            return new ArrayList<>();
        }
        return addresses;
    }

    @Override
    public Optional<Address> findAddressByID(Integer addressID) throws Exception {
        Optional<Address> opt = addressRepository.findById(addressID);
        if(opt.isEmpty()) {
            throw new Exception("Address not found.");
        }
        return opt;
    }

    public ResponseEntity<String> deleteAddress(Integer userID, Integer addressID) {
        try {
            if(findAddressByID(addressID).isEmpty()) {
                throw new Exception("Address not found.");
            }
            addressRepository.deleteById(addressID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
}
