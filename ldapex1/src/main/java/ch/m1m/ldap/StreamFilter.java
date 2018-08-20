package ch.m1m.ldap;

import java.util.Arrays;
import java.util.List;

public class StreamFilter {


    public static void main(String...args) throws InterruptedException {

        example_one();

    }

    public static void example_one() throws InterruptedException {

        List<JsonPerson> persons = Arrays.asList(
                new JsonPerson("andy", null),
                new JsonPerson("markus", null),
                new JsonPerson("thomas", null)
        );

        JsonPerson result1 = persons.stream()                        // Convert to steam
                .filter(x -> "thomas".equals(x.getFirstName()))        // we want "jack" only
                .distinct()
                .findAny()                                      // If 'findAny' then return found
                .orElse(null);                                  // If not found, return null

        System.out.println("result1 is: " + result1);

        JsonPerson result2 = persons.stream()
                .filter(x -> {
                    if ("markus".equals(x.getFirstName()))
                        return true;
                    else
                        return false;
                })
                .findFirst()
                .orElse(null);
        System.out.println("result2 is: " + result2);


        System.out.println("persons list has num members: " + persons.size());

        persons.forEach(p -> {
            p.getFirstName();
        });

        persons.stream()
            .forEach(System.out::println);
    }

}
