package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileConChiave;

public class Chiave extends Oggetto {
    private final ApribileConChiave target;

    public Chiave(String name, ApribileConChiave target) {
        super(name);
        this.target = target;
    }

    public ApribileConChiave getTarget() {
        return target;
    }
}
