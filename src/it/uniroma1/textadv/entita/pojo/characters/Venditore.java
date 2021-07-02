package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.Ricevitore;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;
import it.uniroma1.textadv.entita.pojo.objects.Soldi;

import java.util.*;

public class Venditore extends Personaggio implements Contenitore, Ricevitore<Soldi, Posizionabile> {
    private Set<Posizionabile> carrello = new HashSet<>();
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
    public Set<Posizionabile> getOggettiContenuti() {
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
    public Set<Posizionabile> ricevi(Soldi soldi) {
        if (soldi == null) {
            return Set.of();
        }

        soldiRicevuti = soldi;
        var articoli = carrello;
        carrello = new HashSet<>();
        return articoli;
    }

    public static class InVenditaException extends CommandException {
        public InVenditaException() {
            super("Dovrai comprare l'articolo per prenderlo");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Venditore venditore = (Venditore) o;
        return Objects.equals(carrello, venditore.carrello) && Objects.equals(soldiRicevuti, venditore.soldiRicevuti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), carrello, soldiRicevuti);
    }
}
