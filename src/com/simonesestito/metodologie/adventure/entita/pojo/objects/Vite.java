package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileCon;
import com.simonesestito.metodologie.adventure.entita.pojo.features.BloccoApertura;
import com.simonesestito.metodologie.adventure.entita.pojo.features.UsabileCon;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Botola;

public class Vite extends Oggetto implements BloccoApertura, UsabileCon<Cacciavite> {
    private final Botola botola;
    private boolean bloccato = true;

    public Vite(String name, Botola botola) throws ApribileCon.ChiusuraException {
        super(name);
        this.botola = botola;
        botola.chiudi(this);
    }

    public Botola getBotola() {
        return botola;
    }

    @Override
    public boolean isBloccato() {
        return bloccato;
    }

    @Override
    public void usaCon(Cacciavite cacciavite) {
        if (cacciavite != null)
            bloccato = false;
    }
}
