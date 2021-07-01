package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.ApribileSemplice;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;

import java.util.List;

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
            throw new CommandException("La scrivania è chiusa");
        super.prendiOggetto(oggetto);
    }
}
