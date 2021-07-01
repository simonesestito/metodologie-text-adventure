package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.objects.Inventario;

public class Personaggio extends Entity {
    private final Inventario inventario = new Inventario(); // Sarà vuoto

    public Personaggio(String name) {
        super(name);
    }

    protected Inventario getInventario() {
        return inventario;
    }
}
