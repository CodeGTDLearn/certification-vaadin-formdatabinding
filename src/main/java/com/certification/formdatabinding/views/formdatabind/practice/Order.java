package com.certification.formdatabinding.views.formdatabind.practice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class Order {
  private String customerName;
  private String customerEmail;
  private int productQuantity;
  private String productCategory;
  private LocalDate deliveryDate;
}