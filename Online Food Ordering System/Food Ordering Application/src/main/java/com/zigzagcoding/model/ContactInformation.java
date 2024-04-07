package com.zigzagcoding.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformation {

    private String mobile;
    private String email;

    private String instagram;
    private String twitter;

}
