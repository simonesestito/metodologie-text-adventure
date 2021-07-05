package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.objects.Inventario;

import java.util.Objects;

/**
 * Generico personaggio del gioco
 */
public class Personaggio extends Entity {
    /**
     * Inventario richiesto che lo abbiano tutti i personaggi
     */
    private final Inventario inventario = new Inventario(); // Sarà vuoto

    /**
     * Crea un nuovo personaggio
     * @param name Nome del personaggio
     */
    public Personaggio(String name) {
        super(name);
    }

    /**
     * Ottieni l'inventario, mutabile, del personaggio
     * @return Inventario del personaggio
     */
    protected Inventario getInventario() {
        return inventario;
    }

    /**
     * Verifica che due personaggi siano uguali
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se i personaggi sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Personaggio that = (Personaggio) o;
        return Objects.equals(getInventario(), that.getInventario());
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInventario());
    }
}
