package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.entita.pojo.Entity;

import java.util.List;
import java.util.Set;

public interface Ricevitore<T extends Entity, R> {
    Set<R> ricevi(T oggetto);
}
