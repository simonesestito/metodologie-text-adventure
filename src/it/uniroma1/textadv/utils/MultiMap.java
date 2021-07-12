package it.uniroma1.textadv.utils;

import java.util.*;
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
     * Aggiungi nuovi valori alla multimappa
     *
     * @param key    Chiave su cui aggiungere i valori
     * @param values Valori da aggiungere
     */
    public void add(K key, List<? extends V> values) {
        map.computeIfAbsent(key, __ -> new LinkedList<>()).addAll(values);
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
     * Ottieni una vista immutabile dei valori associati a una data chiave.
     *
     * @param key Chiave da cercare
     * @return Valori associati
     */
    public List<V> get(K key) {
        if (map.containsKey(key))
            return Collections.unmodifiableList(map.get(key));
        else
            return List.of();
    }

    /**
     * Controlla se una chiave è presente nella mappa
     *
     * @param key Chiave da cercare
     * @return <code>true</code> se la chiave è presente
     */
    public boolean containsKey(K key) {
        return map.containsKey(key);
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

    /**
     * Verifica se la mappa è vuota
     *
     * @return <code>true</code> se la mappa è vuota
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Ottieni l'insieme delle chiavi della multimappa
     *
     * @return Insieme delle chiavi
     * @see Map#keySet()
     */
    public Set<K> keySet() {
        return map.keySet();
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due multimappe uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due multimappe uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiMap<?, ?> multiMap = (MultiMap<?, ?>) o;
        return Objects.equals(map, multiMap.map);
    }

    /**
     * Calcola l'hash in base alla mappa sottostante
     *
     * @return Hash della multimappa
     */
    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
