package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.entita.pojo.Entity;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Entità che può ricevere un oggetto in cambio di altri.
 * @param <T> Tipo dell'oggetto che riceve
 * @param <R> Tipo degli oggetti, se ci sono, che restituisce
 */
public interface Ricevitore<T extends Entity, R> {
    /**
     * Ricevi un oggetto in cambio di altri
     * @param oggetto Oggetto da dare
     * @return Insieme di oggetti ricevuti
     */
    Set<R> ricevi(T oggetto);
}
