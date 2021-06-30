package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;

public interface Usabile<T> {
    void usa(T target) throws CommandException;
}
