package it.uniroma1.textadv.entity.pojo.objects;

/**
 * Cacciavite, per levare la {@link Vite} nel gioco
 */
public class Cacciavite extends Oggetto {
    /**
     * Crea un nuovo cacciavite
     * @param name Nome del cacciavite
     */
    public Cacciavite(String name) {
        super(name);
    }

    /**
     * Controlla se sono due cacciaviti
     * @param obj Altro oggetto
     * @return <code>true</code> se sono due cacciaviti uguali
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj.getClass() == getClass();
    }
}
