package it.uniroma1.textadv.entity.pojo.features;

import it.uniroma1.textadv.entity.pojo.objects.Chiave;

import java.util.Objects;

/**
 * Implementazione dell'apertura di un'entità usando una {@link Chiave}.
 * <p>
 * Sfrutta lo strategy pattern per parametrizzare l'implementazione nelle altre classi.
 */
public class ApribileConChiave implements ApribileCon<Chiave> {
    /**
     * Chiave che controlla l'apertura
     */
    private Chiave chiave;

    /**
     * Verifica se è aperto
     */
    private boolean aperto = false;

    /**
     * Apri usando la chiave
     * @param chiave Chiave che dovrebbe aprire
     * @throws AperturaException Errore nell'apertura dell'oggetto
     */
    @Override
    public void apri(Chiave chiave) throws AperturaException {
        if (!Objects.equals(chiave, this.chiave))
            throw new AperturaException();
        aperto = true;
    }

    /**
     * Chiudi usando una chiave
     * @param chiave Chiave che dovrebbe chiudere
     * @throws ChiusuraException Errore nella chiusura dell'oggetto
     */
    @Override
    public void chiudi(Chiave chiave) throws ChiusuraException {
        if (this.chiave != null)
            throw new ChiusuraException();
        this.chiave = chiave;
        aperto = false;
    }

    /**
     * Controlla se è aperto
     * @return <code>true</code> se è aperto
     */
    @Override
    public boolean isAperto() {
        return aperto;
    }

    /**
     * Verifica che due implementazioni siano uguali
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se i due oggetti sono equivalenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApribileConChiave that = (ApribileConChiave) o;
        return isAperto() == that.isAperto() && Objects.equals(chiave, that.chiave);
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(chiave);
    }
}
