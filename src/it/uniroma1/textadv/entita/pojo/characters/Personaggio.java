package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.objects.Inventario;

import java.util.Objects;

public class Personaggio extends Entity {
    private final Inventario inventario = new Inventario(); // Sarà vuoto

    public Personaggio(String name) {
        super(name);
    }

    protected Inventario getInventario() {
        return inventario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personaggio that = (Personaggio) o;
        return Objects.equals(getInventario(), that.getInventario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventario());
    }
}
