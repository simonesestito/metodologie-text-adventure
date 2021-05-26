package com.simonesestito.metodologie.adventure.engine;

import com.simonesestito.metodologie.adventure.engine.commands.MovementCommand;
import com.simonesestito.metodologie.adventure.entita.pojo.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.Giocatore;

import java.util.List;
import java.util.Optional;

/**
 * Gestisci i comandi per gli spostamenti del protagonista
 */
public class MovementCommandClassifier implements CommandClassifier<MovementCommand> {
    public static final String PAROLA_AZIONE = "vai";

    @Override
    public Optional<MovementCommand> processInput(List<String> input) throws TextEngine.CommandException {
        if (input.size() < 1)
            return Optional.empty();

        var directionStringIndex = input.get(0).equals(PAROLA_AZIONE) ? 1 : 0;
        var direction = parseDirection(input.get(directionStringIndex));
        return direction.map(d -> new MovementCommand(Giocatore.getInstance(), d));
    }

    private Optional<Direction> parseDirection(String parola) {
        try {
            return Optional.of(
                    Direction.valueOf(parola.toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
