package com.simonesestito.metodologie.adventure.entita.pojo.objects;

public class Scrivania extends Oggetto {
    private final Chiave chiave;

    public Scrivania(String name, Chiave chiave) {
        super(name);
        this.chiave = chiave;
    }
}
