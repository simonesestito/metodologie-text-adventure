package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;

public class Chiave extends Oggetto {
    public Chiave(String name, Apribile<Chiave> target) throws Apribile.ChiusuraException {
        super(name);
        target.chiudi(this);
    }
}
