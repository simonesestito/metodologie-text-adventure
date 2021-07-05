package it.uniroma1.textadv.entity.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

import java.util.Objects;

/**
 * Implementazione di {@link Posizionabile} per entità presenti univocamente in un solo punto.
 * <p>
 * Sfrutta lo strategy pattern per parametrizzare l'implementazione nelle altre classi.
 */
public class PosizionabileUnico implements Posizionabile {
    /**
     * Oggetto da controllare
     */
    private final Posizionabile oggetto;

    /**
     * Contenitore in cui è attualmente l'oggetto
     */
    private Contenitore posizione;

    /**
     * Crea una nuova istanza di questa implementazione per un dato oggetto
     * @param oggetto Oggetto da controllare
     */
    public PosizionabileUnico(Posizionabile oggetto) {
        this.oggetto = oggetto;
    }

    /**
     * Ottieni la sua posizione attuale
     *
     * @return Posizione attuale
     */
    @Override
    public Contenitore getPosizione() {
        return posizione;
    }

    /**
     * Sposta l'entità in un altro posto.
     * Dovranno essere aggiornati i suoi riferimenti anche nei contenitori precedente e successivo.
     *
     * @param contenitore Nuovo contenitore dove stare
     * @throws CommandException Errore nello spostamento
     */
    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        if (Objects.equals(contenitore, posizione)) {
            return;
        }

        if (this.posizione != null)
            this.posizione.prendiOggetto(oggetto);
        this.posizione = contenitore;

        var posizione = getPosizioneAggiungibile();
        if (posizione.isPresent())
            posizione.get().aggiungiOggetto(oggetto);
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
        PosizionabileUnico that = (PosizionabileUnico) o;
        return Objects.equals(oggetto, that.oggetto) && Objects.equals(getPosizione(), that.getPosizione());
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(oggetto, getPosizione());
    }
}
