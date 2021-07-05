package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

/**
 * Oggetto che può essere usato insieme ad un altro oggetto
 *
 * @param <T> Tipo dell'altro oggetto
 */
public interface UsabileCon<T> {
    /**
     * Usa l'oggetto in questione con un altro dato
     *
     * @param oggetto Altro oggetto dato
     * @throws CommandException Errore nel loro utilizzo
     */
    void usaCon(T oggetto) throws CommandException;
}
