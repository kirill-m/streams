package ru.sbt.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by kirill on 22.08.16
 */
public class Streams<T> {

    private final List<T> list;

    public Streams(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public static<T> Streams<T> of(List<T> list) {
        return new Streams<>(list);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        List<T> list = new ArrayList<>();
        for (T t : this.list) {
            if (predicate.test(t)) {
                list.add(t);
            }
        }

        return new Streams<>(list);
    }

    public <R> Streams<R> transform(Function<? super T, ? extends R> function) {
        List<R> list = new ArrayList<>();
        for (T t : this.list) {
            list.add(function.apply(t));
        }

        return new Streams<>(list);
    }

    public <K,V> Map<K,V> toMap(Function<? super T, ? extends K> key, Function<? super T,? extends V> value) {
        Map<K,V> map = new HashMap<>();

        for (T t : list) {
            map.put(key.apply(t), value.apply(t));
        }

        return map;
    }
}
