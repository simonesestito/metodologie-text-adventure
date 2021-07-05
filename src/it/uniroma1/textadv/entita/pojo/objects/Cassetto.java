package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.ApribileConChiave;
import it.uniroma1.textadv.entita.pojo.features.ApribileSemplice;

import java.util.List;
import java.util.Objects;

/**
 * Cassetto, che contiene altri oggetti e non serve chiuderlo per forza
 */
public class Cassetto extends OggettoContenitore implements ApribileCon<Chiave> {
    /**
     * Implementazione parametrizzata (Strategy) dell'apertura
     */
    private final ApribileConChiave apribileSemplice = new ApribileConChiave();

    /**
     * Crea un nuovo cassetto
     * @param name Nome del cassetto
     * @param contenuto Contenuto del cassetto
     */
    public Cassetto(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    /**
     * Apri il cassetto con una chiave, se è chiuso
     * @param oggetto Oggetto con cui aprire
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri(Chiave oggetto) throws AperturaException {
        apribileSemplice.apri(oggetto);
    }

    /**
     * Chiudi il cassetto con una chiave
     * @param oggetto Oggetto con cui chiudere
     * @throws ChiusuraException Errore nella chiusura
     */
    @Override
    public void chiudi(Chiave oggetto) throws ChiusuraException {
        apribileSemplice.chiudi(oggetto);
    }

    /**
     * Controlla se l'oggetto è attualmente aperto
     * @return <code>true</code> se è aperto
     */
    @Override
    public boolean isAperto() {
        return apribileSemplice.isAperto();
    }

    /**
     * Verifica che l'oggetto corrente e quello dato siano uguali
     * @param o Oggetto da controllare
     * @return <code>true</code> se sono entrambi cassetti uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cassetto cassetto = (Cassetto) o;
        return Objects.equals(apribileSemplice, cassetto.apribileSemplice);
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), apribileSemplice);
    }
}
