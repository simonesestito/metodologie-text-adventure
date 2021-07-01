package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

import java.util.List;

public interface Contenitore {
    List<? extends Posizionabile> getOggettiContenuti();
    void prendiOggetto(Posizionabile oggetto) throws CommandException;
}
