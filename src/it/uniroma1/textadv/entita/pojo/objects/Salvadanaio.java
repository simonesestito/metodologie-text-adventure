package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.Rompibile;
import it.uniroma1.textadv.entita.pojo.features.Rompitore;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Salvadanaio extends OggettoContenitore implements Rompibile {
    private boolean rotto = false;

    public Salvadanaio(String name, Soldi soldi) {
        super(name, new ArrayList<>(List.of(soldi)));
    }

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

    @Override
    public boolean isRotto() {
        return rotto;
    }

    @Override
    protected boolean isContentHidden() {
        return !rotto;
    }

    @Override
    public String toString() {
        return super.toString() + (rotto ? Strings.of(StringId.OBJECT_BROKEN_STATUS_SUFFIX) : "");
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!rotto) {
            throw new ObjectUnbrokenException(getName());
        }

        super.prendiOggetto(oggetto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Salvadanaio that = (Salvadanaio) o;
        return rotto == that.rotto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rotto);
    }
}
