package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

/**
 * Specializzazione di un contenitore, in cui è anche possibile aggiungere oggetti
 */
public interface ContenitoreAggiungibile extends Contenitore {
    /**
     * Aggiungi un oggetto al contenitore
     * @param oggetto Oggetto da aggiungere
     * @throws CommandException Errore nell'aggiunta dell'oggetto
     */
    void aggiungiOggetto(Posizionabile oggetto) throws CommandException;
}
