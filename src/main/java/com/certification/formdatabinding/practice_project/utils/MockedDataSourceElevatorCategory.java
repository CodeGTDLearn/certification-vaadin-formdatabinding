package com.certification.formdatabinding.practice_project.utils;


import com.certification.formdatabinding.practice_project.elevatorCategory.ElevatorCategory;
import com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryTypes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MockedDataSourceElevatorCategory {

  public static List<ElevatorCategory> mockedElevatorCategories() {

    return Arrays.asList(
         new ElevatorCategory(
              ElevatorCategoryTypes.LUX.name(),
              "Atlas",
              "8",
              "3",
              "640"
         ),
         new ElevatorCategory(
              ElevatorCategoryTypes.PANORAMIC.name(),
              "Mafersa",
              "7",
              "2",
              "260"
         ),
         new ElevatorCategory(
              ElevatorCategoryTypes.SERVICE.name(),
              "Siemens",
              "6",
              "1",
              "480"
         )
    );


  }

  public static ElevatorCategory mockedElevatorCategory() {

    return new ElevatorCategory(
         ElevatorCategoryTypes.LUX.name(),
         "Atlas",
         "8",
         "3",
         "640"
    );

  }

  public static ElevatorCategory createRandomMockedElevatorCategory() {

    Random random = new Random();

    // Escolhe um tipo aleatório do enum ElevatorCategoryTypes
    ElevatorCategoryTypes randomType =
         ElevatorCategoryTypes
              .values()[random.nextInt(ElevatorCategoryTypes.values().length)];

    // Gera valores aleatórios para os outros campos
    var randomName = "Elevator" + random.nextInt(100);
    var randomFloors = String.valueOf(random.nextInt(50) + 1); // Número de andares entre 1 e 50
    var randomElevators = String.valueOf(
         random.nextInt(10) + 1); // Número de elevadores entre 1 e 10
    var randomCapacity = String.valueOf(random.nextInt(1000) + 100); // Capacidade entre 100 e 1100

    return new ElevatorCategory(
         randomType.name(),
         randomName,
         randomFloors,
         randomElevators,
         randomCapacity
    );
  }
}