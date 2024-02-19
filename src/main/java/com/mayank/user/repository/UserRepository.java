package com.mayank.user.repository;

import com.mayank.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users u where u.email = :email", nativeQuery = true)
    Optional<User> findBYEmail(@Param("email") String email);
    @Modifying // It means it's not a select statement
    @Query(value = "UPDATE users u SET u.password = :password where u.user_id = :user_id", nativeQuery = true)
    void updatePasswordByID(@Param("password") String password, @Param("user_id") Integer userID);
}
