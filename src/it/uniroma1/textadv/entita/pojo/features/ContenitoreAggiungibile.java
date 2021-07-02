package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

public interface ContenitoreAggiungibile extends Contenitore {
    void aggiungiOggetto(Posizionabile oggetto) throws CommandException;
}
