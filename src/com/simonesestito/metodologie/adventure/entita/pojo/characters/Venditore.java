package com.simonesestito.metodologie.adventure.entita.pojo.characters;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Ricevitore;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Soldi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Venditore extends Personaggio implements Contenitore, Ricevitore<Soldi, Oggetto> {
    private final List<Oggetto> oggettiVenduti;
    private final List<Oggetto> carrello = new ArrayList<>();

    public Venditore(String name, List<Oggetto> oggettiVenduti) {
        super(name);
        this.oggettiVenduti = new ArrayList<>(oggettiVenduti);
    }

    @Override
    public List<Oggetto> getOggettiContenuti() {
        return Collections.unmodifiableList(oggettiVenduti);
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws CommandException {
        carrello.add(oggetto);
        throw new InVenditaException(oggetto.toString());
    }

    @Override
    public List<Oggetto> ricevi(Soldi oggetto) {
        if (oggetto == null)
            return List.of();
        else
            return carrello;
    }

    public static class InVenditaException extends CommandException {
        public InVenditaException(String message) {
            super(message);
        }
    }
}
