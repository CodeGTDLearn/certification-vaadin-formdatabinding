//package com.certification.formdatabinding.practice_project.utils;
//
//import com.certification.formdatabinding.practice_project.elevatorCategory.ElevatorCategory;
//import lombok.Builder;
//import lombok.Builder.Default;
//import lombok.Getter;
//
//import static java.lang.Integer.parseInt;
//
//@Builder
//@Getter
//public class DataBuilderLombok {
//
//  private ElevatorCategory category;   //variavel de instancia do DataBuilder
//
//  //    FakeValuesService faker = new FakeValuesService(new Locale("en-US"), new RandomService());
//  //    faker.letterify("12??89"); //will return something like "12hZ89"
//  //    faker.numerify("ABC##EF"); //will return something like "ABC99EF"
//  //    faker.bothify("12??##ED"); //will return something like "12iL27ED"
//  //    faker.regexify("[a-z1-9]{4}"); //will return something like "6bJ1"
//
//  @Default   //variavel pre carregada, por default
//  private static String catBreed = Faker.instance()
//                                        .cat()
//                                        .breed();
//
//  @Default   //variavel pre carregada, por default
//  private static int orderIdFake =
//       parseInt(Faker.instance()
//                     .regexify("[1-9] {3}")
//                     .trim());
//
//  public static DataBuilderLombok orderWithId() {
//
//    return DataBuilderLombok
//         .builder()
//
//         //builda a variavel de instancia: 'ordem'
//         .category(
//              new ElevatorCategory(111, "bengal")
//         )
//         .build();// carregamento ESTATICO
//  }
//
//  public static DataBuilderLombok ordemWithIdRandom() {
//
//    return DataBuilderLombok.builder()
//                            //builda a variavel de instancia: 'ordem'
//                            .category(
//                                 new ElevatorCategory(orderIdFake, catBreed)
//                            )
//                            .build();// carregamento DINAMICO/RANDOMICO
//  }
//
//  public ElevatorCategory create() {
//
//    return category;// retorna a variavel de instancia 'ordem'
//  }
//}