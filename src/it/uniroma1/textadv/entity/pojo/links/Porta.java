package it.uniroma1.textadv.entity.pojo.links;

import it.uniroma1.textadv.entity.pojo.Stanza;
import it.uniroma1.textadv.entity.pojo.features.ApribileCon;
import it.uniroma1.textadv.entity.pojo.features.ApribileConChiave;
import it.uniroma1.textadv.entity.pojo.objects.Chiave;

import java.util.Objects;

/**
 * Porta, possibilmente chiusa a chiave ma non è detto, che collega due stanze
 */
public class Porta extends OggettoLink implements ApribileCon<Chiave> {
    /**
     * Implementazione parametrizzata (Strategy) dell'apertura
     */
    private final ApribileConChiave apertura = new ApribileConChiave();

    /**
     * Crea una nuova porta
     *
     * @param name    Nome della porta
     * @param stanzaA Stanza collegata
     * @param stanzaB Stanza collegata
     */
    public Porta(String name, Stanza stanzaA, Stanza stanzaB) {
        super(name, stanzaA, stanzaB);
    }

    /**
     * Apri la porta senza alcun oggetto
     *
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri() throws AperturaException {
        apertura.apri();
    }

    /**
     * Apri la porta con una chiave
     *
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri(Chiave oggetto) throws AperturaException {
        apertura.apri(oggetto);
    }

    /**
     * Chiudi la porta con una chiave
     *
     * @throws ChiusuraException Errore nell'apertura
     */
    @Override
    public void chiudi(Chiave oggetto) throws ChiusuraException {
        apertura.chiudi(oggetto);
    }

    /**
     * Controlla se la porta è attualmente aperta
     *
     * @return <code>true</code> se è aperta
     */
    @Override
    public boolean isAperto() {
        return apertura.isAperto();
    }

    /**
     * Controlla se la porta, intesa come collegamento, è attraversabile
     *
     * @return <code>true</code> se è attraversabile
     */
    @Override
    public boolean isAttraversabile() {
        return isAperto();
    }

    /**
     * Verifica che la porta e l'oggetto dato siano uguali
     *
     * @param o Oggetto da controllare
     * @return <code>true</code> se sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Porta porta = (Porta) o;
        return Objects.equals(apertura, porta.apertura);
    }
}
