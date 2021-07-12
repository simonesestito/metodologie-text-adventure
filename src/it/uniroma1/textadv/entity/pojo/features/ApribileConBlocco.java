package it.uniroma1.textadv.entity.pojo.features;

import java.util.Objects;

/**
 * Implementazione di una chiusura con un {@link BloccoApertura}.
 * <p>
 * Sfrutta lo strategy pattern per parametrizzare l'implementazione nelle altre classi.
 */
public class ApribileConBlocco<T extends BloccoApertura> implements ApribileCon<T> {
    /**
     * Blocco che controlla l'apertura
     */
    private BloccoApertura blocco;

    /**
     * Apri usando un blocco
     *
     * @param blocco Blocco che deve aprire
     * @throws AperturaException Errore nell'apertura dell'oggetto
     */
    @Override
    public void apri(T blocco) throws AperturaException {
        if ((blocco != null && !blocco.equals(this.blocco)) || !isAperto())
            throw new AperturaException();
    }

    /**
     * Chiudi usando un blocco
     *
     * @param blocco Blocco che deve chiudere
     * @throws ChiusuraException Errore nella chiusura dell'oggetto
     */
    @Override
    public void chiudi(T blocco) throws ChiusuraException {
        if (this.blocco != null && !isAperto())
            throw new ChiusuraException();
        this.blocco = blocco;
    }

    /**
     * Controlla se è aperto
     *
     * @return <code>true</code> se è aperto
     */
    @Override
    public boolean isAperto() {
        return !blocco.isBloccato();
    }

    /**
     * Verifica che due implementazioni siano uguali
     *
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se i due oggetti sono equivalenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApribileConBlocco<?> that = (ApribileConBlocco<?>) o;
        return isAperto() == that.isAperto() && Objects.equals(blocco, that.blocco);
    }

    /**
     * Calcola l'hash dell'oggetto
     *
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(blocco);
    }
}
