package ru.sbt.streams;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by kirill on 22.08.16
 */
@RunWith(MockitoJUnitRunner.class)
public class StreamsTest {
    List<Person> persons;
    Streams<Person> stream;

    @Before
    public void setUp() throws Exception {
        persons = new ArrayList<>();
        persons.add(new Person("a", 11));
        persons.add(new Person("b", 12));
        persons.add(new Person("c", 13));
        persons.add(new Person("d", 14));
        persons.add(new Person("e", 15));
        persons.add(new Person("f", 16));
        stream = Streams.of(persons);
    }

    @Test
    public void testFilterWithName() throws Exception {
        Predicate<Person> withName = p -> p.getName().equals("c");
        Function<Person, String> nameAsKey = Person::getName;
        Function<Person, Integer> ageAsValue = Person::getAge;
        Map<String, Integer> map = stream.filter(withName).toMap(nameAsKey, ageAsValue);

        Assert.assertEquals(Integer.valueOf(13), map.get("c"));
    }

    @Test
    public void testFilterAgeAbove() throws Exception {
        Predicate<Person> ageAbove = p -> p.getAge() > 13;
        Function<Person, String> nameAsKey = Person::getName;
        Function<Person, Integer> ageAsValue = Person::getAge;
        Map<String, Integer> map = stream.filter(ageAbove).toMap(nameAsKey, ageAsValue);

        Assert.assertEquals(3, map.size());
        Assert.assertEquals(Integer.valueOf(16), map.get("f"));
    }

    @Test
    public void testTransformToNamesList() throws Exception {
        Function<Person, String> name = Person::getName;
        Function<String, String> nameAsKey = s -> s;
        Function<String, String> nameAsValue = s -> s;
        Map<String, String> map = stream.transform(name).toMap(nameAsKey, nameAsValue);
        Set<String> strings = map.keySet();
        String[] expected = new String[]{"a", "b", "c", "d", "e", "f"};
        String[] actual = strings.toArray(new String[6]);

        Assert.assertArrayEquals(expected, actual);
        Assert.assertEquals(expected[0], actual[0]);
    }

    @Test
    public void testToMap() {
        Function<Person, String> nameAsKey = Person::getName;
        Function<Person, Integer> ageAsValue = Person::getAge;
        Map<String, Integer> map = stream.toMap(nameAsKey, ageAsValue);

        Assert.assertEquals(6, map.size());
        Assert.assertEquals(Integer.valueOf(16), map.get("f"));
    }

}