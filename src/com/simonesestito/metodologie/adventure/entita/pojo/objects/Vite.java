package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.links.Botola;

public class Vite extends Oggetto {
    private final Botola botola;

    public Vite(String name, Botola botola) {
        super(name);
        this.botola = botola;
    }

    public Botola getBotola() {
        return botola;
    }
}
