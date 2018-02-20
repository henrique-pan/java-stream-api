package com.henrique.ex;

import java.util.stream.Stream;

public class ProcessingOrder {

    public static void main(String[] args) {
        System.out.println("========================================================================================");
        ex1();
        System.out.println("========================================================================================");
        ex2();
        System.out.println("========================================================================================");
        ex3();
        System.out.println("========================================================================================");
        ex4();
        System.out.println("========================================================================================");
        ex5();
        System.out.println("========================================================================================");
        ex6();
    }

    private static void ex1() {
        // An important characteristic of intermediate operations are laziness
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                }); // Terminal operation is missing
        // Intermediate operations will only be executed when a terminal operation is present.
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                }).forEach(s -> System.out.println("forEach: " + s));
    }
    /*
        The order of the result might be surprising. A naive approach would be to execute the operations
        horizontally one after another on all elements of the stream. But instead each element moves along
        the chain vertically. The first string "d2" passes filter then forEach, only then the second string "a2"
        is processed.
     */

    private static void ex2() {
        boolean b = Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });

        Stream<String> stringStream = Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                });
        stringStream.anyMatch(s -> {
            System.out.println("anyMatch: " + s);
            return s.startsWith("A");
        });


        boolean r = Stream.of("d2", "a2", "b1", "b3", "c")
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("a");
                });
        System.out.println(r);
    }

    private static void ex3() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    private static void ex4() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    private static void ex5() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }
    /*
        Sorting is a special kind of intermediate operation. It's a so called stateful operation since in
        order to sort a collection of elements you have to maintain state during ordering.
     */

    private static void ex6() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }
    /*
        In this example sorted is never been called because filter reduces the input collection to just one element.
        So the performance is greatly increased for larger input collections.
     */

}
