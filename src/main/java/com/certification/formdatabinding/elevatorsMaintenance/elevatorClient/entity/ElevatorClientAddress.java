package com.certification.formdatabinding.elevatorsMaintenance.elevatorClient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElevatorClientAddress {
    private String streetAddress;
    private String city;
    private String postalCode;
}