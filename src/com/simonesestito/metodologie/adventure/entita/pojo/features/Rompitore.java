package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;

public interface Rompitore extends Usabile<Rompibile> {
    // Interfaccia segnaposto

    @Override
    default void usa(Rompibile target) throws CommandException {
        target.rompi(this);
    }
}
