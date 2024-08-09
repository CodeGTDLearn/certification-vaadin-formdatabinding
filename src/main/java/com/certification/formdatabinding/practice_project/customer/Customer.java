package com.certification.formdatabinding.practice_project.customer;

import com.certification.formdatabinding.practice_project.address.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}