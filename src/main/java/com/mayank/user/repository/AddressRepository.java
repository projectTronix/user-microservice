package com.mayank.user.repository;

import com.mayank.user.dto.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AddressRepository extends JpaRepository <Address, Integer> {
    @Query(value = "SELECT * FROM addresses a WHERE a.user_id = :user_id", nativeQuery = true)
    List<Address> findAllByUserID(@Param("user_id") Integer userID);
}
