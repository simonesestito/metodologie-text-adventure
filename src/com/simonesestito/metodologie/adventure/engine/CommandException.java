package com.simonesestito.metodologie.adventure.engine;

/**
 * Errore generico relativo a un comando, anche alla sua esecuzione
 */
public class CommandException extends Exception {
    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class Fatal extends CommandException {
        public Fatal() {
        }

        public Fatal(String message) {
            super(message);
        }

        public Fatal(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
