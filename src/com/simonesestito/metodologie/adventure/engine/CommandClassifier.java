package com.simonesestito.metodologie.adventure.engine;

import com.simonesestito.metodologie.adventure.engine.commands.Command;

import java.util.List;
import java.util.Optional;

public interface CommandClassifier<C extends Command> {
    Optional<C> processInput(List<String> input) throws TextEngine.CommandException;
}
