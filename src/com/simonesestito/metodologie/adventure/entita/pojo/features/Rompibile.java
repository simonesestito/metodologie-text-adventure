package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.util.List;

public interface Rompibile extends UsabileCon<Rompitore> {
    void rompi(Rompitore rompitore) throws CommandException;

    @Override
    default void usaCon(Rompitore oggetto) throws CommandException {
        rompi(oggetto);
    }
}
