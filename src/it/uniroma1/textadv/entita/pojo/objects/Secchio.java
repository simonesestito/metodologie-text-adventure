package it.uniroma1.textadv.entita.pojo.objects;


import java.util.Objects;

/**
 * Secchio che contiene acqua
 */
public class Secchio extends Oggetto {
    /**
     * Stato di riempimento del secchio
     */
    private boolean riempito;

    /**
     * Crea un nuovo secchio
     *
     * @param name Nome del secchio
     */
    public Secchio(String name) {
        super(name);
    }

    /**
     * Controlla se il secchio è riempito
     *
     * @return <code>true</code> se è riempito
     */
    public boolean isRiempito() {
        return riempito;
    }

    /**
     * Imposta il riempimento del secchio
     * @param riempito Riempimento del secchio
     */
    private void setRiempito(boolean riempito) {
        this.riempito = riempito;
    }

    /**
     * Riempi il secchio
     */
    /* package */ void riempi() {
        setRiempito(true);
    }

    /**
     * Svuota il secchio
     */
    /* package */ void svuota() {
        setRiempito(false);
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due secchi uguali
     * @param o Altro oggetto
     * @return <code>true</code> se sono due secchi uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Secchio secchio = (Secchio) o;
        return isRiempito() == secchio.isRiempito();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isRiempito());
    }
}
