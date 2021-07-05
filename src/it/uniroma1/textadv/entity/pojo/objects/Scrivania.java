package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.features.ApribileCon;
import it.uniroma1.textadv.entity.pojo.features.ApribileSemplice;
import it.uniroma1.textadv.entity.pojo.features.Posizionabile;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.List;
import java.util.Objects;

/**
 * Scrivania che contiene altri oggetti e può essere aperta senza altri oggetti
 */
public class Scrivania extends OggettoContenitore implements ApribileCon<Object> {
    /**
     * Implementazione parametrizzata (Strategy) dell'apertura
     */
    private final ApribileSemplice apribileSemplice = new ApribileSemplice();

    /**
     * Crea una nuova scrivania
     * @param name Nome della scrivania
     * @param contenuto Contenuto della scrivania
     */
    public Scrivania(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    /**
     * Apri la scrivania
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri() throws AperturaException {
        apribileSemplice.apri();
    }

    /**
     * Apri la scrivania
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri(Object oggetto) throws AperturaException {
        apribileSemplice.apri(oggetto);
    }

    /**
     * Chiudi la scrivania
     * @throws ChiusuraException Errore nella chiusura
     */
    @Override
    public void chiudi(Object oggetto) throws ChiusuraException {
        apribileSemplice.chiudi(oggetto);
    }

    /**
     * Controlla se la scrivania è aperta
     * @return <code>true</code> se la scrivania è aperta
     */
    @Override
    public boolean isAperto() {
        return apribileSemplice.isAperto();
    }

    /**
     * Prendi un oggetto da dentro la scrivania controllando che questa sia aperta
     * @param oggetto Oggetto da prendere
     * @throws CommandException Errore nella presa
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!isAperto())
            throw new CommandException(Strings.of(StringId.DESK_CLOSED));
        super.prendiOggetto(oggetto);
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due scrivanie uguali
     * @param o Altro oggetto
     * @return <code>true</code> se sono due scrivanie uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Scrivania scrivania = (Scrivania) o;
        return Objects.equals(apribileSemplice, scrivania.apribileSemplice);
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
