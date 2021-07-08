package it.uniroma1.textadv.utils;

/**
 * Struttura dati per contenere una coppia di valori
 *
 * @param <K> Tipo della chiave della coppia
 * @param <V> Tipo del valore della coppia
 */
public record Pair<K, V>(K key, V value) {
}
