package it.uniroma1.textadv.entity.pojo.links;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.Stanza;
import it.uniroma1.textadv.entity.pojo.characters.Giocatore;
import it.uniroma1.textadv.entity.pojo.features.ApribileCon;
import it.uniroma1.textadv.entity.pojo.features.ApribileConChiave;
import it.uniroma1.textadv.entity.pojo.features.Usabile;
import it.uniroma1.textadv.entity.pojo.objects.Chiave;

import java.util.Objects;

/**
 * Teletrasporto come collegamento tra stanze, chiuso a chiave
 */
public class Teletrasporto extends OggettoLink implements ApribileCon<Chiave>, Usabile {
    /**
     * Implementazione parametrizzata (Strategy) dell'apertura
     */
    private final ApribileConChiave apertura = new ApribileConChiave();

    /**
     * Crea un nuovo teletrasporto
     *
     * @param name    Nome del teletrasporto
     * @param stanzaA Stanza collegata
     * @param stanzaB Stanza collegata
     */
    public Teletrasporto(String name, Stanza stanzaA, Stanza stanzaB) {
        super(name, stanzaA, stanzaB);
    }

    /**
     * Apri il teletraporto senza alcun oggetto
     *
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri() throws AperturaException {
        apertura.apri();
    }

    /**
     * Apri il teletraporto con la chiave
     *
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri(Chiave oggetto) throws AperturaException {
        apertura.apri(oggetto);

        // Quando viene aperto, immediatamente teletrasporta
        // Vedasi comando:
        // -> usa chiave teletrasporto su teletrasporto // vengo teletrasportato nella piccola stanza misteriosa
        try {
            Giocatore.getInstance().prendiDirezione(this);
        } catch (CommandException e) {
            throw new AperturaException();
        }
    }

    /**
     * Chiudi il teletraporto con la chiave
     *
     * @throws ChiusuraException Errore nell'apertura
     */
    @Override
    public void chiudi(Chiave oggetto) throws ChiusuraException {
        apertura.chiudi(oggetto);
    }

    /**
     * Controlla se il teletrasporto è attualmente aperto
     *
     * @return <code>true</code> se è aperto
     */
    @Override
    public boolean isAperto() {
        return apertura.isAperto();
    }

    /**
     * Usa il teletrasporto, quindi entra al suo interno il protagonista
     *
     * @throws CommandException Errore nell'utilizzo
     * @see Giocatore#prendiDirezione(Link)
     */
    @Override
    public void usa() throws CommandException {
        Giocatore.getInstance().prendiDirezione(this);
    }

    /**
     * Controlla se i due teletrasporti sono uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teletrasporto that = (Teletrasporto) o;
        return Objects.equals(apertura, that.apertura);
    }
}
