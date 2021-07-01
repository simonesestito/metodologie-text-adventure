package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.entita.pojo.features.ApribileCon;

public class Chiave extends Oggetto {
    public Chiave(String name, ApribileCon<Chiave> target) throws ApribileCon.ChiusuraException {
        super(name);
        target.chiudi(this);
    }
}
