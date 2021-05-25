package com.simonesestito.metodologie.adventure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MultiMap<K, V> {
    private final Map<K, List<V>> map = new HashMap<>();

    public void add(K key, V value) {
        map.computeIfAbsent(key, __ -> new LinkedList<>()).add(value);
    }

    public List<V> remove(K key) {
        return map.remove(key);
    }

    public void consume(K key, Consumer<V> valueConsumer) {
        var consumedValues = remove(key);
        if (consumedValues != null)
            consumedValues.forEach(valueConsumer);
    }
}
