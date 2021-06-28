package com.simonesestito.metodologie.adventure.entita.pojo.objects;

public class Salvadanaio extends Oggetto {
    private final Soldi soldi;

    public Salvadanaio(String name, Soldi soldi) {
        super(name);
        this.soldi = soldi;
    }
}
