package com.certification.formdatabinding.elevatorsMaintenance.elevatorClient.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElevatorClient {
    private String firstName;
    private String lastName;
    private String email;
    private ElevatorClientAddress elevatorClientAddress;
}