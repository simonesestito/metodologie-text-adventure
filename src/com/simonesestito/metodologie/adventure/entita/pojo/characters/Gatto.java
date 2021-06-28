package com.simonesestito.metodologie.adventure.entita.pojo.characters;

import com.simonesestito.metodologie.adventure.entita.pojo.features.Accarezzabile;

public class Gatto extends Personaggio implements Accarezzabile {
    public Gatto(String name) {
        super(name);
    }

    @Override
    public void accarezza() {
        System.out.println("Miao");
    }
}
