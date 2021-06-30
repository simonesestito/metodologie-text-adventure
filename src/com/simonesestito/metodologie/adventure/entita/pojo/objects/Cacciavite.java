package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Usabile;

public class Cacciavite extends Oggetto implements Usabile<Vite> {
    public Cacciavite(String name) {
        super(name);
    }

    @Override
    public void usa(Vite target) throws CommandException {
        target.svita(this);
    }
}
