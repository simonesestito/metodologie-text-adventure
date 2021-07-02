package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

import java.util.Objects;

public class PosizionabileUnico implements Posizionabile {
    private final Posizionabile oggetto;
    private Contenitore posizione;

    public PosizionabileUnico(Posizionabile oggetto) {
        this.oggetto = oggetto;
    }

    @Override
    public Contenitore getPosizione() {
        return posizione;
    }

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        if (Objects.equals(contenitore, posizione)) {
            return;
        }

        if (this.posizione != null)
            this.posizione.prendiOggetto(oggetto);
        this.posizione = contenitore;

        getPosizioneAggiungibile().ifPresent(s -> s.aggiungiOggetto(oggetto));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PosizionabileUnico that = (PosizionabileUnico) o;
        return Objects.equals(oggetto, that.oggetto) && Objects.equals(getPosizione(), that.getPosizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(oggetto, getPosizione());
    }
}
