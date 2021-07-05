package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.entity.pojo.features.UsabileCon;

/**
 * Pozzo in cui è possibile riempire il secchio
 */
public class Pozzo extends Oggetto implements UsabileCon<Secchio> {
    /**
     * Crea un nuovo secchio
     *
     * @param name Nome del secchio
     */
    public Pozzo(String name) {
        super(name);
    }

    /**
     * Usa il pozzo per riempire un secchio
     *
     * @param oggetto Secchio da riempire
     */
    @Override
    public void usaCon(Secchio oggetto) {
        oggetto.riempi();
    }
}
