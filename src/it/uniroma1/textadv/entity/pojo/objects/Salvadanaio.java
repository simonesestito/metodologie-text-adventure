package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.features.Posizionabile;
import it.uniroma1.textadv.entity.pojo.features.Rompibile;
import it.uniroma1.textadv.entity.pojo.features.Rompitore;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Salvadanaio che una volta rotto, rilascia soldi nella stanza in cui è contenuto
 */
public class Salvadanaio extends OggettoContenitore implements Rompibile {
    /**
     * Stato della rottura del salvadanaio
     */
    private boolean rotto = false;

    /**
     * Crea un nuovo salvadanaio
     *
     * @param name  Nome del salvadanaio
     * @param soldi Soldi in esso contenuti
     */
    public Salvadanaio(String name, Soldi soldi) {
        super(name, new ArrayList<>(List.of(soldi)));
    }

    /**
     * Rompi il salvadanaio rilasciando soldi nella stanza dove si trova
     *
     * @param rompitore Oggetto che lo rompe
     * @throws CommandException Errore nella rottura
     */
    @Override
    public void rompi(Rompitore rompitore) throws CommandException {
        Rompibile.super.rompi(rompitore);

        rotto = true;

        var stanza = getPosizioneAggiungibile();
        if (stanza.isPresent()) {
            for (var oggetto : getOggettiContenuti())
                oggetto.spostaIn(stanza.get());
        }
    }

    /**
     * Verifica se il salvadanaio è rotto
     *
     * @return <code>true</code> se è rotto
     */
    @Override
    public boolean isRotto() {
        return rotto;
    }

    /**
     * Nascondi il contenuto del salvadanaio se questo non è ancora stato rotto
     *
     * @return <code>true</code> se il salvadanaio è sano, quindi il contenuto è nascosto
     */
    @Override
    protected boolean isContentHidden() {
        return !rotto;
    }

    /**
     * Rappresenta lo stato del salvadanaio come stringa.
     * <p>
     * Indica anche se è rotto
     *
     * @return Rappresentazione attuale del salvadanaio
     */
    @Override
    public String toString() {
        return super.toString() + (rotto ? Strings.of(StringId.OBJECT_BROKEN_STATUS_SUFFIX) : "");
    }

    /**
     * Prendi i soldi dal salvadanaio se questo è rotto
     *
     * @param oggetto Oggetto da prendere
     * @throws CommandException Errore nella presa dell'oggetto
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!rotto) {
            throw new ObjectUnbrokenException(getName());
        }

        super.prendiOggetto(oggetto);
    }

    /**
     * Controlla se due oggetti sono salvadanai uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono salvadanai uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Salvadanaio that = (Salvadanaio) o;
        return rotto == that.rotto;
    }

    /**
     * Calcola l'hash dell'oggetto
     *
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rotto);
    }
}
