package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.ApribileSemplice;

import java.util.List;

public class Cassetto extends OggettoContenitore implements ApribileCon<Object> {
    private final ApribileSemplice apribileSemplice = new ApribileSemplice();

    public Cassetto(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
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
}
