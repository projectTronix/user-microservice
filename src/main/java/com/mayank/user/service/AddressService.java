package com.mayank.user.service;

import com.mayank.user.dto.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {
    boolean saveAddress(Integer userId, Address address) throws Exception;
    List<Address> getAllAddresses(Integer userID) throws Exception;
    boolean deleteAddress(Integer userID, Integer addressID) throws Exception;
    Optional<Address> findAddressByID(Integer addressID) throws Exception;
}
