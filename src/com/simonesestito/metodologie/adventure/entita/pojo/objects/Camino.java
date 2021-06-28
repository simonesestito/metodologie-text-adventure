package com.simonesestito.metodologie.adventure.entita.pojo.objects;

public class Camino extends Oggetto {
    private boolean acceso = true;
    private final Oggetto contenuto;

    public Camino(String name, Oggetto contenuto) {
        super(name);
        this.contenuto = contenuto;
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
