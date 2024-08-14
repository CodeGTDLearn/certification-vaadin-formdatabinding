package com.certification.formdatabinding.practice_project.serviceClient.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    private String firstName;
    private String lastName;
    private String email;
    private ClientAddress clientAddress;
}