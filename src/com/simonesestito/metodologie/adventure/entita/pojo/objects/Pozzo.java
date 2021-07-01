package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.features.UsabileCon;

public class Pozzo extends Oggetto implements UsabileCon<Secchio> {
    public Pozzo(String name) {
        super(name);
    }

    @Override
    public void usaCon(Secchio oggetto) throws CommandException {
        oggetto.riempi();
    }
}
