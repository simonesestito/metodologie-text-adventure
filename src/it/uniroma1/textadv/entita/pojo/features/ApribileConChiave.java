package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.entita.pojo.objects.Chiave;

public class ApribileConChiave implements ApribileCon<Chiave> {
    private Chiave chiave;
    private boolean aperto = false;

    @Override
    public void apri(Chiave chiave) throws AperturaException {
        if (chiave != this.chiave)
            throw new AperturaException();
        aperto = true;
    }

    @Override
    public void chiudi(Chiave chiave) throws ChiusuraException {
        if (this.chiave != null)
            throw new ChiusuraException();
        this.chiave = chiave;
        aperto = false;
    }

    @Override
    public boolean isAperto() {
        return aperto;
    }
}
