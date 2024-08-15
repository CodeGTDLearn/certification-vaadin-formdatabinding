package com.certification.formdatabinding.practice_project.elevatorCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElevatorCategory {

 private String categoryName;
 private String manufacturer;
 private String maxCapacity;
 private String speed;
 private String maxWeight;
}