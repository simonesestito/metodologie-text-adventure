package com.simonesestito.metodologie.adventure.entita.pojo.characters;

import com.simonesestito.metodologie.adventure.entita.pojo.features.Accarezzabile;

public class Cane extends Personaggio implements Accarezzabile {
    public Cane(String name) {
        super(name);
    }

    @Override
    public void accarezza() {
        System.out.println("Bau");
    }
}
