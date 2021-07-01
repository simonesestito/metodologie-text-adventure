package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileCon;

public class Chiave extends Oggetto {
    public Chiave(String name, ApribileCon<Chiave> target) throws ApribileCon.ChiusuraException {
        super(name);
        target.chiudi(this);
    }
}
