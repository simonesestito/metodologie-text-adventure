package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.BloccoApertura;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.UsabileCon;
import it.uniroma1.textadv.entita.pojo.links.Botola;

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

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        super.spostaIn(contenitore);
    }
}
