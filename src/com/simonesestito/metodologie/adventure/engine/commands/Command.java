package com.simonesestito.metodologie.adventure.engine.commands;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;

public interface Command {
    void execute(Mondo mondo) throws TextEngine.CommandException;
}
