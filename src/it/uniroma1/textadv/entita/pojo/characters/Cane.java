package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.entita.pojo.features.Accarezzabile;
import it.uniroma1.textadv.entita.pojo.features.Parla;

public class Cane extends Personaggio implements Accarezzabile, Parla {
    private static final String ABBAIO = "Bau";
    public Cane(String name) {
        super(name);
    }

    @Override
    public void accarezza() {
        System.out.println(ABBAIO);
    }

    @Override
    public String parla() {
        return ABBAIO;
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
