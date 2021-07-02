package it.uniroma1.textadv.entita.pojo.objects;

import java.util.Objects;

public class Tronchesi extends Oggetto {
    private final Armadio armadio;

    public Tronchesi(String name, Armadio armadio) {
        super(name);
        this.armadio = armadio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tronchesi tronchesi = (Tronchesi) o;
        return Objects.equals(armadio, tronchesi.armadio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), armadio);
    }
}
