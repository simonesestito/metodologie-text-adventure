package it.uniroma1.textadv.entity.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

/**
 * Oggetto che può essere usato
 */
public interface Usabile {
    /**
     * Utilizza l'oggetto
     * @throws CommandException Errore nel suo utilizzo
     */
    void usa() throws CommandException;
}
