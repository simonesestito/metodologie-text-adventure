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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
