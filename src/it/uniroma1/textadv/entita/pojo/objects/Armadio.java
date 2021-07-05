package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.entita.pojo.features.ApribileCon;

import java.util.List;
import java.util.Objects;

/**
 * Rappresenta un armadio che contiene altri oggetti e si può aprire con un oggetto
 */
public class Armadio extends OggettoContenitore implements ApribileCon<Tronchesi> {
    /**
     * Stato di apertura dell'armadio
     */
    private boolean aperto = true;

    /**
     * Crea un nuovo armadio
     *
     * @param name      Nome dell'armadio
     * @param contenuto Elenco degli oggetti contenuti
     */
    public Armadio(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    /**
     * Apri l'armadio
     *
     * @param oggetto Oggetto con cui aprire
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri(Tronchesi oggetto) throws AperturaException {
        if (oggetto != null)
            aperto = true;
    }

    /**
     * Controlla se l'oggetto è attualmente aperto
     *
     * @return <code>true</code> se è aperto
     */
    @Override
    public boolean isAperto() {
        return aperto;
    }

    /**
     * Controlla se due armadi sono uguali
     *
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se i due armadi sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armadio armadio = (Armadio) o;
        return isAperto() == armadio.isAperto();
    }
}
