package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.entity.pojo.features.ApribileCon;

/**
 * Chiave che apre altri oggetti
 *
 * @see it.uniroma1.textadv.entity.pojo.features.ApribileConChiave
 */
public class Chiave extends Oggetto {
    /**
     * Crea una nuova chiave
     *
     * @param name   Nome della chiave
     * @param target Oggetto che apre
     * @throws ApribileCon.ChiusuraException Errore nella chiusura dell'oggetto con questa chiave
     */
    public Chiave(String name, ApribileCon<Chiave> target) throws ApribileCon.ChiusuraException {
        super(name);
        target.chiudi(this);
    }
}
