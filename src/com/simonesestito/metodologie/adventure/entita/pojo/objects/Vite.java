package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.BloccoApertura;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Botola;

public class Vite extends Oggetto implements BloccoApertura {
    private final Botola botola;
    private boolean bloccato = true;

    public Vite(String name, Botola botola) throws Apribile.ChiusuraException {
        super(name);
        this.botola = botola;
        botola.chiudi(this);
    }

    public Botola getBotola() {
        return botola;
    }

    public void svita(Cacciavite cacciavite) {
        if (cacciavite != null)
            bloccato = false;
    }

    @Override
    public boolean isBloccato() {
        return bloccato;
    }
}
