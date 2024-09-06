package com.certification.formdatabinding.practice_project.utils;

import com.certification.formdatabinding.practice_project.elevatorCategory.ElevatorCategory;
import com.certification.formdatabinding.practice_project.elevatorCategory.config.ElevatorCategoryTypes;
import com.certification.formdatabinding.practice_project.elevatorClient.entity.ElevatorClientAddress;
import com.certification.formdatabinding.practice_project.elevatorParts.ElevatorParts;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MockedDataSourceElevatorCategory {

  public static List<ElevatorCategory> mockedElevatorCategories() {

    return Arrays.asList(
         new ElevatorCategory(
              ElevatorCategoryTypes.LUX.name(),
              "Atlas",
              8,
              3.0,
              640
         ),
         new ElevatorCategory(
              ElevatorCategoryTypes.PANORAMIC.name(),
              "Mafersa",
              7,
              2.0,
              260
         ),
         new ElevatorCategory(
              ElevatorCategoryTypes.SERVICE.name(),
              "Siemens",
              6,
              1.0,
              480
         )
    );


  }

  public static ElevatorClientAddress mockedElevatorClientAddress() {

    return new ElevatorClientAddress(
         "Baseline Road",
         "Ottawa",
         "t3c 1p5"
    );

  }

  public static ElevatorParts randomMockedElevatorPart() {

    Random random = new Random();

    String[] names = {"Engine", "Door", "Cable"};
    String[] manufacturers = {"Siemens", "Otis", "Kone"};
    String[] descriptions = {
         "Piece is responsible to up",
         "Controls the openingdoors",
         "Used to select the floor",
         "Supports the car and counterweight",
         "Detects the elevator car"
    };
    int[] quantities = {1, 2, 3};

    var name = names[random.nextInt(names.length)];
    var manufacturer = manufacturers[random.nextInt(manufacturers.length)];
    var description = descriptions[random.nextInt(descriptions.length)];
    int quantity = quantities[random.nextInt(quantities.length)];

    return new ElevatorParts(name, manufacturer, description, quantity);
  }

  public static ElevatorCategory randomMockedElevatorCategory() {

    Random random = new Random();

    // Escolhe um tipo aleatório do enum ElevatorCategoryTypes
    ElevatorCategoryTypes randomType =
         ElevatorCategoryTypes
              .values()[random.nextInt(ElevatorCategoryTypes.values().length)];

    // Gera valores aleatórios para os outros campos
    var randomName = "Elevator" + random.nextInt(100);
    var randomFloors = random.nextInt(50) + 1; // Número de andares entre 1 e 50
    var randomElevators = random.nextDouble(10) + 1; // Número de elevadores entre 1 e 10
    var randomCapacity = random.nextInt(1000) + 100; // Capacidade entre 100 e 1100

    return new ElevatorCategory(
         randomType.name(),
         randomName,
         randomFloors,
         randomElevators,
         randomCapacity
    );
  }
}