package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.entita.pojo.Entity;

import java.util.List;

public interface Ricevitore<T extends Entity, R extends Entity> {
    List<R> ricevi(T oggetto);
}
