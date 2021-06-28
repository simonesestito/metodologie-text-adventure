package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.engine.TextEngine;

public interface Apribile {
    void apri() throws CommandException;
    boolean isAperto();
}
