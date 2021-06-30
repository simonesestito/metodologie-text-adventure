package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Rompibile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Rompitore;

import java.util.List;

public class Salvadanaio extends Oggetto implements Rompibile, Contenitore {
    private Soldi soldi;
    private boolean rotto = false;

    public Salvadanaio(String name, Soldi soldi) {
        super(name);
        this.soldi = soldi;
    }

    @Override
    public List<? extends Oggetto> rompi(Rompitore rompitore) throws CommandException {
        if (rotto)
            throw new CommandException("Salvadanaio già rotto");

        if (rompitore == null)
            throw new CommandException("Serve un oggetto per rompere il salvadanaio");

        rotto = true;

        var contenuto = getOggettiContenuti();
        prendiOggetto(soldi); // Restituisci i soldi contenuti, e prendili dal contenuto
        return contenuto;
    }

    @Override
    public String toString() {
        return super.toString() + (rotto ? " (rotto)" : "");
    }

    @Override
    public List<Oggetto> getOggettiContenuti() {
        return soldi == null ? List.of() : List.of(soldi);
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws CommandException {
        if (!rotto) {
            throw new CommandException("Salvadanaio ancora intero");
        }

        if (!oggetto.equals(soldi)) {
            throw new CommandException("Non trovo " + oggetto + " qui");
        }

        soldi = null;
    }
}
