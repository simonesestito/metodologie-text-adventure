package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.entita.pojo.features.Accarezzabile;

public class Cane extends Personaggio implements Accarezzabile {
    public Cane(String name) {
        super(name);
    }

    @Override
    public void accarezza() {
        System.out.println("Bau");
    }
}
