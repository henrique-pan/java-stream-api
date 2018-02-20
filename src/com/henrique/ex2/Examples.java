package com.henrique.ex2;

import com.henrique.ex.Person;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.*;

public class Examples {

    public static void main(String[] args) {
        System.out.println("========================================================================================");
        ex1();
    }

    //Empty Stream
    private static void ex1() {
        Stream<String> streamEmpty = Stream.empty();
    }

    //Stream of Collection
    private static void ex2() {
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();
    }

    //Stream of Array
    private static void ex3() {
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);
    }

    //Stream.builder()
    private static void ex4() {
        Stream<String> streamBuilder = Stream.<String>builder().add("a").add("b").add("c").build();
    }

    //Stream.generate()
    private static void ex5() {
        Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);
    }

    //Stream.iterate()
    private static void ex6() {
        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
    }

    //Stream of Primitives
    private static void ex7() {
        // range(int startInclusive, int endExclusive)
        IntStream intStream = IntStream.range(1, 3);
        // rangeClosed(int startInclusive, int endInclusive)
        LongStream longStream = LongStream.rangeClosed(1, 3);

        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
    }

    //Stream of String
    private static void ex8() {
        IntStream streamOfChars = "abc".chars();
        Stream<String> streamOfString = Pattern.compile(", ").splitAsStream("a, b, c");
    }

    //Stream of File
    private static void ex9() {
        try {
            Path path = Paths.get("C:\\file.txt");
            Stream<String> streamOfStrings = Files.lines(path);
            Stream<String> streamWithCharset = Files.lines(path, Charset.forName("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Referencing a Stream
    private static void ex10() {
        Stream<String> stream = Stream.of("a", "b", "c").filter(element -> element.contains("b"));
        // Optional<String> anyElement = stream.findAny(); OK
        // Optional<String> firstElement = stream.findFirst(); Stream cannot be reused

        List<String> elements = Stream.of("a", "b", "c")
                        .filter(element -> element.contains("b"))
                        .collect(Collectors.toList());
        Optional<String> anyElement = elements.stream().findAny();
        Optional<String> firstElement = elements.stream().findFirst();
    }

    //The reduce() Method
    /*
        There are three variations of this method, which differ by their signatures and returning types.
        They can have the following parameters:
            identity – the initial value for an accumulator or a default value if a stream is
            empty and there is nothing to accumulate;
            accumulator – a function which specifies a logic of aggregation of elements.
            As accumulator creates a new value for every step of reducing, the quantity of new values
            equals to the stream’s size and only the last value is useful. This is not very good for the performance.
            combiner – a function which aggregates results of the accumulator.
            Combiner is called only in a parallel mode to reduce results of accumulators from different threads.
     */
    private static void ex11() {
        OptionalInt reduced = IntStream.range(1, 4).reduce((a, b) -> a + b);
        //reduced = 6 (1 + 2 + 3)

        int reducedTwoParams = IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
        //reducedTwoParams = 16 (10 + 1 + 2 + 3)

        int reducedParams = Stream.of(1, 2, 3)
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    Logger.getAnonymousLogger().info("combiner was called");
                    return a + b;
                });
        // reducedParams = 16

        int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
                .reduce(10, (a, b) -> a + b,
                                    (a, b) -> {
                                        Logger.getAnonymousLogger().info("combiner was called");
                                        return a + b;
                                    });
    }
}
