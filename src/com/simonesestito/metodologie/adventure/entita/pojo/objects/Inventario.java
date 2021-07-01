package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.engine.EntityResolver;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ContenitoreAggiungibile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventario implements ContenitoreAggiungibile {
    private final List<Oggetto> inventario = new ArrayList<>();

    @Override
    public List<Oggetto> getOggettiContenuti() {
        return Collections.unmodifiableList(inventario);
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws CommandException {
        if (!inventario.contains(oggetto))
            throw new EntityResolver.UnresolvedEntityException(oggetto, this);
        inventario.remove(oggetto);
    }

    @Override
    public void aggiungiOggetto(Oggetto oggetto) {
        inventario.add(oggetto);
    }

    @Override
    public String toString() {
        return "inventario";
    }
}
