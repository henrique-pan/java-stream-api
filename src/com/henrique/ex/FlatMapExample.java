package com.henrique.ex;

import java.util.*;
import java.util.stream.IntStream;

public class FlatMapExample {

    public static void main(String[] args) {
        System.out.println("========================================================================================");
        ex1();
        System.out.println("========================================================================================");
        ex2();
        System.out.println("========================================================================================");
        ex3();
    }

    /*
        FlatMap transforms each element of the stream into a stream of other objects.
     */
    private static void ex1() {
        List<Foo> foos = new ArrayList<>();
        IntStream.range(1, 4).forEach(i -> foos.add(new Foo("Foo" + i)));

        foos.forEach(f -> IntStream.range(1, 4)
                            .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));

    }

    private static void ex2() {
        List<Foo> foos = new ArrayList<>();
        IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
                        .forEach(f.bars::add))
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));
    }

    private static void ex3() {
        Outer outer = new Outer();
        if (outer != null && outer.nested != null && outer.nested.inner != null) {
            System.out.println(outer.nested.inner.foo);
        }
        Optional.of(new Outer())
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);
    }

}

class Outer {
    Nested nested;
}

class Nested {
    Inner inner;
}

class Inner {
    String foo;
}
