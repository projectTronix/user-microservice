package com.mayank.user.service;

import com.mayank.user.dto.Address;
import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import com.mayank.user.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final UserService userService;
    private final AddressRepository addressRepository;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public boolean saveAddress(Integer userId, Address address) throws Exception {
        try {
            User user = userService.getUserByID(userId);
            address.setUser(user);
            addressRepository.save(address);
            return true;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while saving to addresses -- saveAddress in AddressService. - " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public List<Address> getAllAddresses(Integer userID) throws Exception {
        try {
            User user= userService.getUserByID(userID);
            List<Address> addresses =  addressRepository.findAllByUserID(userID);
            if(addresses.isEmpty()) {
                return new ArrayList<>();
            }
            return addresses;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching addresses -- getAllAddresses in AddressService. - " + e.getMessage());
            throw new Exception("Error while fetching addresses of user.");
        }
    }
    @Override
    public Optional<Address> findAddressByID(Integer addressID) throws Exception {
        try {
            Optional<Address> opt = addressRepository.findById(addressID);
            return opt;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching address by address ID -- findAddressByID in AddressService. - " + e.getMessage());
            throw new Exception("Error while fetching address by addressID.");
        }
    }
    public boolean deleteAddress(Integer userId, Integer addressID) throws Exception {
        try {
            if(findAddressByID(addressID).isEmpty()) {
                return false;
            }
            addressRepository.deleteById(addressID);
            return findAddressByID(addressID).isEmpty();
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while deleting address -- deleteAddress in AddressService. - " + e.getMessage());
            throw new Exception("Error while deleting address.");
        }
    }
}
