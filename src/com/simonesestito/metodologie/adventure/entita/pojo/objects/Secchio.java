package com.simonesestito.metodologie.adventure.entita.pojo.objects;

public class Secchio extends Oggetto {
    private boolean riempito;

    public Secchio(String name) {
        super(name);
    }

    public boolean isRiempito() {
        return riempito;
    }

    private void setRiempito(boolean riempito) {
        this.riempito = riempito;
    }

    public void riempi() {
        setRiempito(true);
    }
}
