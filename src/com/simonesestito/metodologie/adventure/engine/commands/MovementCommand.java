package com.simonesestito.metodologie.adventure.engine.commands;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.features.Posizionabile;

public class MovementCommand implements Command {
    private final Posizionabile subject;
    private final Direction direction;

    public MovementCommand(Posizionabile subject, Direction direction) {
        this.subject = subject;
        this.direction = direction;
    }

    @Override
    public void execute(Mondo mondo) throws TextEngine.CommandException {
        // FIXME: subject.moveTo(direction);
    }
}
