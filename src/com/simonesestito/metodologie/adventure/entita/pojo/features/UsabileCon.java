package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;

public interface UsabileCon<T> {
    void usaCon(T oggetto) throws CommandException;
}
