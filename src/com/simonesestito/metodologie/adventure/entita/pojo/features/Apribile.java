package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.TextEngine;

public interface Apribile {
    void apri() throws TextEngine.CommandException;
    boolean isAperto();
}
