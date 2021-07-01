package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.Rompibile;
import it.uniroma1.textadv.entita.pojo.features.Rompitore;

import java.util.List;

public class Salvadanaio extends Oggetto implements Rompibile, Contenitore {
    private Soldi soldi;
    private boolean rotto = false;

    public Salvadanaio(String name, Soldi soldi) {
        super(name);
        this.soldi = soldi;
    }

    @Override
    public void rompi(Rompitore rompitore) throws CommandException {
        if (rotto)
            throw new CommandException("Salvadanaio già rotto");

        if (rompitore == null)
            throw new CommandException("Serve un oggetto per rompere il salvadanaio");

        rotto = true;
        // TODO
        //getStanza().ifPresent(stanza -> {
        //    var contenuto = getOggettiContenuti();
        //    contenuto.forEach(stanza::addObject);
        //    soldi = null;
        //});
    }

    @Override
    public String toString() {
        return super.toString() + (rotto ? " (rotto)" : "");
    }

    @Override
    public List<Oggetto> getOggettiContenuti() {
        return soldi == null || !rotto ? List.of() : List.of(soldi);
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!rotto) {
            throw new CommandException("Salvadanaio ancora intero");
        }

        if (!oggetto.equals(soldi)) {
            throw new CommandException("Non trovo " + oggetto + " qui");
        }

        soldi = null;
    }
}
