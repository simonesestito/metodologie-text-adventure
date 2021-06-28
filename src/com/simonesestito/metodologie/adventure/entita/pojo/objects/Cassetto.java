package com.simonesestito.metodologie.adventure.entita.pojo.objects;

public class Cassetto extends Oggetto {
    private final Chiave chiave;

    public Cassetto(String name, Chiave chiave) {
        super(name);
        this.chiave = chiave;
    }
}
