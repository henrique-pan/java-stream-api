package com.henrique.ex;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectExample {

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

    /*
        Collect is an extremely useful terminal operation to transform the elements of the stream into a
        different kind of result, e.g. a List, Set or Map. Collect accepts a Collector which consists of
        four different operations: a supplier, an accumulator, a combiner and a finisher.
     */

    private static void ex1() {
        List<Person> persons = Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));

        List<Person> filtered = persons.stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());
        System.out.println(filtered);

        Set<Person> filteredSet = persons.stream()
                .filter(p -> p.name.startsWith("M"))
                .collect(Collectors.toSet());
        filteredSet.forEach(System.out::println);

        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));

        personsByAge.forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
    }

    private static void ex2() {
        List<Person> persons = Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));

        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));

        System.out.println(averageAge);

        IntSummaryStatistics ageSummary =
                persons
                        .stream()
                        .collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummary);

        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

        System.out.println(phrase);
    }

    private static void ex3() {
        List<Person> persons = Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("Hublo", 23),
                new Person("David", 12));

        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));

        System.out.println(map);
    }

    private static void ex4() {
        List<Person> persons = Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("Hublo", 23),
                new Person("David", 12));

        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher

        String names = persons
                .stream()
                .collect(personNameCollector);

        System.out.println(names);
    }


}
