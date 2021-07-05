package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.entita.pojo.features.Rompitore;

/**
 * Martello in grado di rompere altri oggetti
 */
public class Martello extends Oggetto implements Rompitore {
    /**
     * Crea un nuovo martello
     * @param name Nome del martello
     */
    public Martello(String name) {
        super(name);
    }
}
