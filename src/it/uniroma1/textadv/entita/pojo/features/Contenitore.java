package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

import java.util.List;
import java.util.Set;

public interface Contenitore {
    Set<? extends Posizionabile> getOggettiContenuti();
    void prendiOggetto(Posizionabile oggetto) throws CommandException;
}
