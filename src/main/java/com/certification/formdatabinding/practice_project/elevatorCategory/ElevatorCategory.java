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
 private Integer maxCapacity;
 private Double speed;
 private Integer maxWeight;
}