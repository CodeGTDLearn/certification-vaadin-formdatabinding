//package com.certification.formdatabinding.practice_project.utils;
//
//import com.certification.formdatabinding.practice_project.elevatorCategory.ElevatorCategory;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//
//@Builder
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class ElevatorCategoryMock {
//
//  public static ElevatorCategory createElevatorResidentialCategory() {
//
//    return
//         ElevatorCategory
//              .builder()
//              .categoryName("Residential")
//              .manufacturer("ElevatorCo")
//              .maxCapacity("10")
//              .speed("2 m/s")
//              .maxWeight("800 kg")
//              .build();
//  }
//
//  public static ElevatorCategory createElevatorCommercialCategory() {
//
//    return
//         ElevatorCategory
//              .builder()
//              .categoryName("Commercial")
//              .manufacturer("LiftTech")
//              .maxCapacity("20")
//              .speed("3 m/s")
//              .maxWeight("1500 kg")
//              .build();
//  }
//
//  public static ElevatorCategory createElevatorIndustrialCategory() {
//
//    return
//         ElevatorCategory
//              .builder()
//              .categoryName("Industrial")
//              .manufacturer("ElevatorPro")
//              .maxCapacity("30")
//              .speed("4 m/s")
//              .maxWeight("2000 kg")
//              .build();
//  }
//}