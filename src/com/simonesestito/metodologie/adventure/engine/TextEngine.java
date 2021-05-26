package com.simonesestito.metodologie.adventure.engine;

import com.simonesestito.metodologie.adventure.engine.commands.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Elabora l'input utente e capisce i comandi
 */
public class TextEngine {
    // TODO: più stop word
    public static final Set<String> STOP_WORDS_SET = Set.of(
            "a",
            "in",
            "su",
            "per"
    );

    // TODO: reflection auto-register
    public static final List<CommandClassifier<?>> CLASSIFIER_LIST = List.of(
            new MovementCommandClassifier()
    );

    // TODO: Creare il comando, secondo Command Pattern
    public Command processInput(String input) throws CommandException {
        return CLASSIFIER_LIST.stream()
                .map(c -> {
                    try {
                        return c.processInput(cleanInput(input));
                    } catch (CommandException e) {
                        return Optional.<Command>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UnknownCommand(input));
    }

    /**
     * Ripulisci l'input di informazioni non necessarie, come le <b>stop-word</b>.
     *
     * @param input Testo "sporco" di informazioni inutili
     * @return Testo ripulito, già diviso per parole
     */
    private List<String> cleanInput(String input) {
        return Arrays.stream(input.split("\\s+"))
                .filter(s -> !s.isBlank())
                .filter(s -> !STOP_WORDS_SET.contains(s))
                .toList();
    }

    /**
     * Errore generico nella comprensione del comando
     */
    public static class CommandException extends Exception {
        public CommandException() {
        }

        public CommandException(String message) {
            super(message);
        }

        public CommandException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Errore di riconoscimento del comando
     */
    public static class UnknownCommand extends CommandException {
        public UnknownCommand(String userInput) {
            super("Unknown command: " + userInput);
        }
    }
}
