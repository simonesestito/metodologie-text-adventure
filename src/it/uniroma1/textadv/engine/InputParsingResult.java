package it.uniroma1.textadv.engine;

import java.util.List;

/**
 * Risultato del parsing di un comando utente
 *
 * @param command   Comando risolto
 * @param arguments Argomenti risolti
 */
public record InputParsingResult(Command command, List<?> arguments) {
    /**
     * Istanza di un risultato vuoto
     */
    public static final InputParsingResult EMPTY = new InputParsingResult(Command.EMPTY, List.of());

    /**
     * Esegui il comando risultante, con gli argomenti richiesti dall'utente
     *
     * @throws CommandException Errore nell'esecuzione del comando
     */
    public void execute() throws CommandException {
        command.execute(arguments);
    }
}
