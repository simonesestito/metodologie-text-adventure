package it.uniroma1.textadv.entity.pojo.features;

/**
 * Implementazione di un'apertura semplice, che funziona con ogni oggetto, o anche nessuno.
 * <p>
 * Sfrutta lo strategy pattern per parametrizzare l'implementazione nelle altre classi.
 */
public class ApribileSemplice implements ApribileCon<Object> {
    /**
     * Stato di apertura
     */
    private boolean aperto = false;

    /**
     * Controlla se è aperto
     *
     * @return <code>true</code> se è aperto
     */
    @Override
    public boolean isAperto() {
        return aperto;
    }

    /**
     * Chiudi con qualunque oggetto, anche nessuno (<code>null</code>)
     *
     * @param oggetto Oggetto con cui chiudere
     */
    @Override
    public void chiudi(Object oggetto) {
        aperto = false;
    }

    /**
     * Apri con qualunque oggetto, anche nessuno (<code>null</code>)
     *
     * @param oggetto Oggetto con cui aprire
     */
    @Override
    public void apri(Object oggetto) {
        aperto = true;
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
        ApribileSemplice that = (ApribileSemplice) o;
        return isAperto() == that.isAperto();
    }
}
