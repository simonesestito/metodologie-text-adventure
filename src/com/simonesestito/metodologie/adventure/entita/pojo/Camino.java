package com.simonesestito.metodologie.adventure.entita.pojo;

public class Camino extends Oggetto {
    private boolean acceso = true;

    public Camino(String name) {
        super(name);
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void accendi() {
        setAcceso(true);
    }

    public void spegni() {
        setAcceso(false);
    }

    private void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }
}
