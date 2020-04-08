package ch.m1m.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JustForFun {

    public static void main(String... args) {

        System.out.println("start...");

        // DTest dTest = new DTest();
        // dTest.firstname = "my-family-name";

        List<String> myList = new ArrayList<>();
        myList.add("eins");
        myList.add("zwei");
        myList.add("drei");

        List<String> result = myList.stream()               // convert list to stream
                .filter(line -> !"eins".equals(line))       // we dont like mkyong
                .map(entry -> entry.toUpperCase())
                .collect(Collectors.toList());              // collect the output and convert streams to a List

        result.forEach(System.out::println);                //output : spring, node

    }
}
