package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.ApribileSemplice;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.List;
import java.util.Objects;

public class Scrivania extends OggettoContenitore implements ApribileCon<Object> {
    private final ApribileSemplice apribileSemplice = new ApribileSemplice();

    public Scrivania(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    @Override
    public void apri() throws AperturaException {
        apribileSemplice.apri();
    }

    @Override
    public void apri(Object oggetto) throws AperturaException {
        apribileSemplice.apri(oggetto);
    }

    @Override
    public void chiudi(Object oggetto) throws ChiusuraException {
        apribileSemplice.chiudi(oggetto);
    }

    @Override
    public boolean isAperto() {
        return apribileSemplice.isAperto();
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!isAperto())
            throw new CommandException(Strings.of(StringId.DESK_CLOSED));
        super.prendiOggetto(oggetto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Scrivania scrivania = (Scrivania) o;
        return Objects.equals(apribileSemplice, scrivania.apribileSemplice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), apribileSemplice);
    }
}
