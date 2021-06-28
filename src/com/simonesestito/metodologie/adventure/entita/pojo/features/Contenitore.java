package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.util.List;

public interface Contenitore {
    List<Oggetto> getOggettiContenuti();
    void prendiOggetto(Oggetto oggetto) throws TextEngine.CommandException;
}
