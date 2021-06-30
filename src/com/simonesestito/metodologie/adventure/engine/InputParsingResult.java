package com.simonesestito.metodologie.adventure.engine;

import java.util.List;

public record InputParsingResult(Command command, List<?> arguments) {
    public static final InputParsingResult EMPTY = new InputParsingResult(Command.EMPTY, List.of());

    public void execute() throws CommandException {
        command.execute(arguments);
    }
}
