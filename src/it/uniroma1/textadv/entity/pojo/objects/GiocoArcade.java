package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.easter.DinoGame;
import it.uniroma1.textadv.entity.pojo.features.Gioco;

/**
 * Gioco arcade nelle sale giochi
 */
public class GiocoArcade extends Oggetto implements Gioco {
    /**
     * Crea un nuovo game box
     *
     * @param name Nome del game box
     */
    public GiocoArcade(String name) {
        super(name);
    }

    /**
     * Avvia il gioco arcade disponibile
     */
    @Override
    public void avviaGioco() {
        try {
            new DinoGame().avvia();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
