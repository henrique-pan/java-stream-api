package com.henrique.ex;

import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class HowStreamsWork {

    // https://docs.oracle.com/javase/9/docs/api/java/util/stream/Stream.html

    public static void main(String[] args) {
        System.out.println("========================================================================================");
        ex1();
        System.out.println("========================================================================================");
        ex2();
        System.out.println("========================================================================================");
        ex3();
        System.out.println("========================================================================================");
        ex4();
    }


    private static void ex1() {
        //List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        List<String> myList = List.of("a1", "a2", "b1", "c2", "c1");

        /*
        Intermediate:
            Intermediate operations return a stream so we can chain multiple
            intermediate operations without using semicolons.
        */
        /*
        Terminal:
            Terminal operations are either void or return a non-stream result.
        */
        myList.stream()
                .filter(s -> s.startsWith("c")) //Intermediate
                .map(String::toUpperCase) //Intermediate
                .sorted() //Intermediate
                .forEach(System.out::println); //Terminal
    }
    /*
        A function is non-interfering when it does not modify the underlying data source of the stream,
        e.g. in the above example no lambda expression does modify myList by adding or removing elements
        from the collection.

        A function is stateless when the execution of the operation is deterministic,
        e.g. in the above example no lambda expression depends on any mutable variables or states from the
        outer scope which might change during execution.
     */


    private static void ex2() {
        Arrays.asList("a1", "a2", "a3").stream().findFirst().ifPresent(System.out::println);

        // OR

        Stream.of("a1", "a2", "a3").findFirst().ifPresent(System.out::println);
    }

    /*
        Besides regular object streams Java 8 ships with special kinds of streams for working with the
        primitive data types int, long and double. As you might have guessed it's IntStream,
        LongStream and DoubleStream.
     */
    private static void ex3() {
        IntStream.range(1, 4).forEach(System.out::println);
        LongStream.range(1, 4).forEach(System.out::println);
    }

    /*
        All those primitive streams work just like regular object streams with the following differences:
        Primitive streams use specialized lambda expressions, e.g. IntFunction instead of Function or IntPredicate
        instead of Predicate. And primitive streams support the additional terminal aggregate
        operations sum() and average():
     */

    private static void ex4() {
        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1) // Return IntStream
                .average() // Return OptionalDouble
                .ifPresent(System.out::println);  // 5.0

        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt) // Transform a regular object stream to a primitive stream
                .max()
                .ifPresent(System.out::println);  // 3

        IntStream.range(1, 4)
                .mapToObj(i -> "a" + i) // Primitive streams can be transformed to object streams
                .forEach(System.out::println);

        Stream.of(1.0, 2.0, 3.0) // The stream of doubles
                .mapToInt(Double::intValue) // is first mapped to an int stream
                .mapToObj(i -> "a" + i) // and than mapped to an object stream of strings
                .forEach(System.out::println);

    }

}
