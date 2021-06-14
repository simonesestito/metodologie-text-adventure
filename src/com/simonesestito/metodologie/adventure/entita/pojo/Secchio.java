package com.simonesestito.metodologie.adventure.entita.pojo;

public class Secchio extends Oggetto {
    private boolean riempito;

    public Secchio(String name) {
        super(name);
    }

    public boolean isRiempito() {
        return riempito;
    }

    public void riempi() {
        setRiempito(true);
    }

    private void setRiempito(boolean riempito) {
        this.riempito = riempito;
    }
}
