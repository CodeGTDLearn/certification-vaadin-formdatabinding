package com.certification.formdatabinding.practice_project.elevatorClient.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientAddress {
    private String streetAddress;
    private String city;
    private String postalCode;
}