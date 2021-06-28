package com.simonesestito.metodologie.adventure.entita.pojo.characters;

import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

public class Guardiano extends Personaggio {
    private final Oggetto oggetto;
    private final Entity distrazione;

    public Guardiano(String name, Oggetto oggetto, Entity distrazione) {
        super(name);
        this.oggetto = oggetto;
        this.distrazione = distrazione;
    }
}
