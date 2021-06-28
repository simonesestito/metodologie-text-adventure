package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;

public interface Rompibile {
    void rompi(Rompitore rompitore) throws CommandException;
}
