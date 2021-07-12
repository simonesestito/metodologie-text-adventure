package it.uniroma1.textadv.engine;

/**
 * Errore generico relativo a un comando, anche alla sua esecuzione
 */
public class CommandException extends Exception {
    /**
     * Crea un errore generico vuoto
     */
    public CommandException() {
    }

    /**
     * Crea un errore con un dato messaggio
     *
     * @param message Messaggio d'errore
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Crea un errore indicando sia un messaggio che una causa
     *
     * @param message Messaggio d'errore
     * @param cause   Altro errore considerato sua causa
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Errore più specifico, in quanto considerato FATALE ai fini del Fast-Forwarding di gioco
     */
    public static class Fatal extends CommandException {
        /**
         * Nuovo errore vuoto
         */
        public Fatal() {
        }

        /**
         * Crea un errore con un dato messaggio
         *
         * @param message Messaggio d'errore
         */
        public Fatal(String message) {
            super(message);
        }

        /**
         * Crea un errore indicando sia un messaggio che una causa
         *
         * @param message Messaggio d'errore
         * @param cause   Altro errore considerato sua causa
         */
        public Fatal(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
