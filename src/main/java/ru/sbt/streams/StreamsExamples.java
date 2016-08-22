package ru.sbt.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kirill on 22.08.16
 */
public class StreamsExamples {


    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("a", 12));
        persons.add(new Person("b", 21));
        persons.add(new Person("c", 8));
        persons.add(new Person("d", 18));
        persons.add(new Person("e", 15));

        Map<Integer, Person> map = Streams.of(persons)
                .filter(p -> p.getAge() > 10)
                .transform(p -> new Person(p.getName(), p.getAge() + 10))
                .toMap(Person::getAge, p -> p);

        for (Map.Entry<Integer, Person> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getName());
        }
        System.out.println();

        for (Person person : persons) {
            System.out.println(person.getName());
        }
    }
}
