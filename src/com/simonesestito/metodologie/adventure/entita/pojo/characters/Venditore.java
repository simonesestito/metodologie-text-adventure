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
    private List<Oggetto> carrello = new ArrayList<>();
    private Soldi soldiRicevuti;

    public Venditore(String name, List<Oggetto> oggettiVenduti) {
        super(name);
        this.oggettiVenduti = oggettiVenduti.stream()
                .peek(o -> {
                    try {
                        o.spostaIn(this);
                    } catch (CommandException ignored) { }
                })
                .toList();
    }

    @Override
    public List<Oggetto> getOggettiContenuti() {
        return Collections.unmodifiableList(oggettiVenduti);
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) throws InVenditaException {
        if (soldiRicevuti == null) {
            carrello.add(oggetto);
            throw new InVenditaException();
        }
    }

    @Override
    public List<Oggetto> ricevi(Soldi soldi) {
        if (soldi == null) {
            return List.of();
        }

        soldiRicevuti = soldi;
        var articoli = carrello;
        carrello = new ArrayList<>();
        return articoli;
    }

    public static class InVenditaException extends CommandException {
        public InVenditaException() {
            super("Dovrai comprare l'articolo per prenderlo");
        }
    }
}
