package com.certification.formdatabinding.practice_project.elevatorCategory;

import lombok.*;

@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ElevatorCategory {

 private String categoryName;
 private String manufacturer;
 private String maxCapacity;
 private String speed;
 private String maxWeight;
}