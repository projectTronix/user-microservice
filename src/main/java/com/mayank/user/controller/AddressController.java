package com.mayank.user.controller;

import com.mayank.user.dto.Address;
import com.mayank.user.dto.User;
import com.mayank.user.exception.UserNotFoundException;
import com.mayank.user.service.AddressService;
import com.mayank.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Validated
@RestController
public class AddressController {
    UserService userService;
    AddressService addressService;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping("users/{id}/addresses/add")
    public ResponseEntity<?> addAddress(@RequestBody @Valid Address address, @PathVariable(name = "id") Integer userId) {
        try {
            Optional<User> opt = userService.getUserByID(userId);
            if(!opt.isPresent()) {
                throw new UserNotFoundException("Invalid Email ID or password.");
            }
            User user = opt.get();
            if(address.getCity().isBlank() || address.getPinCode().isBlank() || address.getLoc().isBlank() || address.getState().isBlank()) {
                logger.log(Level.WARNING, "Address Field(s) Empty.");
                throw new Exception("Address Field(s) Empty.");
            }
            address.setUser(user);
            ResponseEntity<String> response = addressService.saveAddress(user, address);
            logger.log(Level.INFO, "Address added Successfully.");
            return new ResponseEntity<>(address, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("users/{id}/addresses/view")
    public ResponseEntity<List<Address>> getAllAddresses(@PathVariable(name = "id") Integer userID) {
        try {
            Optional<User> opt = userService.getUserByID(userID);
            if(!opt.isPresent()) {
                throw new UserNotFoundException("Invalid Email ID or password.");
            }
            User user = opt.get();
            return new ResponseEntity<>(addressService.getAllAddresses(user.getId()), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @DeleteMapping("users/{id}/addresses/{addressID}")
    public ResponseEntity<?> deleteAddress(@PathVariable(name = "id") Integer userId, @PathVariable(name = "addressID") Integer addressID) {
        try {
            Optional<User> opt = userService.getUserByID(userId);
            if(!opt.isPresent()) {
                throw new UserNotFoundException("Invalid Email ID or password.");
            }
            if(!addressService.findAddressByID(addressID).isPresent()) {
                throw new Exception("Address not found.");
            }
            User user = opt.get();
            ResponseEntity<String> response = addressService.deleteAddress(userId, addressID);
            logger.log(Level.INFO, "Address deleted Successfully.");
            return new ResponseEntity<>("Address deleted successfully.", HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
