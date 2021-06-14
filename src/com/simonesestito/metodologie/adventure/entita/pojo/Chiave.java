package com.simonesestito.metodologie.adventure.entita.pojo;

public class Chiave extends Oggetto {
    private final Oggetto target;

    public Chiave(String name, Oggetto target) {
        super(name);
        this.target = target;
    }
}
