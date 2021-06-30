package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;

public class ApribileConChiave implements Apribile<Chiave> {
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
