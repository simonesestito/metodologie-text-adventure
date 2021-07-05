package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.Ricevitore;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;
import it.uniroma1.textadv.entita.pojo.objects.Soldi;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Venditore di oggetti, in cambio di soldi, e a sua volta "contenitore" nel senso di possessore di oggetti
 */
public class Venditore extends Personaggio implements Contenitore, Ricevitore<Soldi, Posizionabile> {
    /**
     * Articoli presi dal personaggio ma non ancora ricevuti
     */
    private Set<Posizionabile> carrello = new HashSet<>();

    /**
     * Soldi ricevuti dal venditore
     */
    private Soldi soldiRicevuti;

    /**
     * Crea un nuovo venditore
     *
     * @param name           Nome del venditore
     * @param oggettiVenduti Oggetti che esso vende
     */
    public Venditore(String name, List<Oggetto> oggettiVenduti) {
        super(name);
        oggettiVenduti.forEach(o -> {
            try {
                o.spostaIn(getInventario());
            } catch (CommandException ignored) {
            }
        });
    }

    /**
     * Ottieni gli oggetti che vende, ovvero che possiede al momento
     *
     * @return Oggetti in vendita
     */
    @Override
    public Set<Posizionabile> getOggettiContenuti() {
        return getInventario().getOggettiContenuti();
    }

    /**
     * Prendi un oggetto in vendita
     *
     * @param oggetto Oggetto da prendere
     * @throws InVenditaException Se non l'ho ancora pagato, lo aggiunge al carrello senza prenderlo realmente
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) throws InVenditaException {
        if (soldiRicevuti == null) {
            carrello.add(oggetto);
            throw new InVenditaException();
        }
    }

    /**
     * Ricevi i soldi in cambio degli oggetti nel carrello
     * @param soldi Soldi da ricevere
     * @return Oggetti venduti
     */
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

    /**
     * Controlla se i venditori sono uguali
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se i venditori sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Venditore venditore = (Venditore) o;
        return Objects.equals(carrello, venditore.carrello) && Objects.equals(soldiRicevuti, venditore.soldiRicevuti);
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), carrello, soldiRicevuti);
    }

    /**
     * Eccezione in caso venga preso un oggetto in vendita, poi messo nel carrello
     */
    public static class InVenditaException extends CommandException {
        /**
         * Eccezione con messaggio di errore di aggiunto al carrello
         */
        public InVenditaException() {
            super(Strings.of(StringId.ITEM_ADDED_TO_CART));
        }
    }
}
