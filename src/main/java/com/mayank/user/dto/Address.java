package com.mayank.user.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "addresses")
@Entity
@Valid
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = "[1-9]{1}[0-9]{5}|[1-9]{1}[0-9]{3}\\\\s[0-9]{3}", message = "Pincode is not valid.")
    @Column(nullable = false)
    private String pinCode;
    @Size(min = 2, max = 14, message = "City must be at least 2 characters long and must not exceed 14 characters.")
    @Column(nullable = false)
    private String city;
    @Size(min = 2, max = 14, message = "State must be at least 2 characters long and must not exceed 14 characters.")
    @Column(nullable = false)
    private String state;
    @Size(min = 2, max = 30, message = "This field must be at least 2 characters long and must not exceed 30 characters.")
    @Column(nullable = false)
    private String loc;
    @Size(min = 2, max = 30, message = "This field must be at least 2 characters long and must not exceed 30 characters.")
    @Column(nullable = false)
    private String street;

    @ManyToOne
    //Adding the name
    @JoinColumn(name = "user_id", referencedColumnName="user_id")
    @JsonBackReference
    private User user;
}
