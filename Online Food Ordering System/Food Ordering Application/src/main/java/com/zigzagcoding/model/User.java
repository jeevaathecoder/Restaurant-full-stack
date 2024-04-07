package com.zigzagcoding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zigzagcoding.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.zigzagcoding.model.USER_ROLE.ROLE_CUSTOMER;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    private USER_ROLE role = ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL )
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favourties = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true )
    private List<Address> addresses = new ArrayList<>();
}
