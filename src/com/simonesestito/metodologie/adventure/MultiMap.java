package com.simonesestito.metodologie.adventure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Multimappa che consente di gestire mappe con più valori a fronte della stessa chiave.
 *
 * @param <K> Tipo della chiave
 * @param <V> Tipo del valore (o dei valori)
 */
public class MultiMap<K, V> {
    /**
     * Mappa sottostante alla multimappa
     */
    private final Map<K, List<V>> map = new HashMap<>();

    /**
     * Aggiungi un nuovo valore alla multimappa
     *
     * @param key   Chiave su cui aggiungere il valore
     * @param value Valore da aggiungere
     */
    public void add(K key, V value) {
        map.computeIfAbsent(key, __ -> new LinkedList<>()).add(value);
    }

    /**
     * Rimuovi una chiave dalla multimappa, e tutti i valori ad essa associati
     *
     * @param key Chiave da rimuovere
     * @return Valori rimossi dalla multimappa, se la chiave era presente
     * @see Map#remove(Object)
     */
    public List<V> remove(K key) {
        return map.remove(key);
    }

    /**
     * Rimuovi una chiave dalla multimappa,
     * consumando i propri valori, se la chiave era presente e non vuota.
     *
     * @param key           Chiave da rimuovere
     * @param valueConsumer Consumer per i valori precedentemente associati alla chiave data
     */
    public void consume(K key, Consumer<V> valueConsumer) {
        var consumedValues = remove(key);
        if (consumedValues != null)
            consumedValues.forEach(valueConsumer);
    }
}
