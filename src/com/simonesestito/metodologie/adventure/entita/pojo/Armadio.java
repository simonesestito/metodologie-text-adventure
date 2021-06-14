package com.simonesestito.metodologie.adventure.entita.pojo;

public class Armadio extends Oggetto{
    private final Oggetto contenuto;

    public Armadio(String name, Oggetto contenuto) {
        super(name);
        this.contenuto = contenuto;
    }
}
