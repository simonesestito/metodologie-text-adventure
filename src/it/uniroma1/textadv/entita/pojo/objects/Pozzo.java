package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.UsabileCon;

public class Pozzo extends Oggetto implements UsabileCon<Secchio> {
    public Pozzo(String name) {
        super(name);
    }

    @Override
    public void usaCon(Secchio oggetto) throws CommandException {
        oggetto.riempi();
    }
}
