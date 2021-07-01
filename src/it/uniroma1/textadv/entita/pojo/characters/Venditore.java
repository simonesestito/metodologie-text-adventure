package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.Ricevitore;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;
import it.uniroma1.textadv.entita.pojo.objects.Soldi;

import java.util.ArrayList;
import java.util.List;

public class Venditore extends Personaggio implements Contenitore, Ricevitore<Soldi, Posizionabile> {
    private List<Posizionabile> carrello = new ArrayList<>();
    private Soldi soldiRicevuti;

    public Venditore(String name, List<Oggetto> oggettiVenduti) {
        super(name);
        oggettiVenduti.forEach(o -> {
            try {
                o.spostaIn(getInventario());
            } catch (CommandException ignored) {
            }
        });
    }

    @Override
    public List<Posizionabile> getOggettiContenuti() {
        return getInventario().getOggettiContenuti();
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws InVenditaException {
        if (soldiRicevuti == null) {
            carrello.add(oggetto);
            throw new InVenditaException();
        }
    }

    @Override
    public List<Posizionabile> ricevi(Soldi soldi) {
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
