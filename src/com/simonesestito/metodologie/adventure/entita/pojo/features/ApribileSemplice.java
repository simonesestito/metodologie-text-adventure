package com.simonesestito.metodologie.adventure.entita.pojo.features;

public class ApribileSemplice implements Apribile<Object> {
    private boolean aperto = false;

    @Override
    public boolean isAperto() {
        return aperto;
    }

    @Override
    public void chiudi(Object oggetto) throws ChiusuraException {
        aperto = false;
    }

    @Override
    public void apri(Object oggetto) throws AperturaException {
        aperto = true;
    }
}
