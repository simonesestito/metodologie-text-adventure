package com.simonesestito.metodologie.adventure.entita.pojo.objects;

public class Armadio extends Oggetto {
    private final Oggetto contenuto;

    public Armadio(String name, Oggetto contenuto) {
        super(name);
        this.contenuto = contenuto;
    }
}
