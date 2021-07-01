package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.entita.pojo.Entity;

import java.util.List;

public interface Ricevitore<T extends Entity, R> {
    List<R> ricevi(T oggetto);
}
