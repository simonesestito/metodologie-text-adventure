package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.PosizionabileUnico;

public abstract class Oggetto extends Entity implements Posizionabile {
    private final PosizionabileUnico posizionabileUnico = new PosizionabileUnico(this);

    public Oggetto(String name) {
        super(name);
    }

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        posizionabileUnico.spostaIn(contenitore);
    }

    @Override
    public Contenitore getPosizione() {
        return posizionabileUnico.getPosizione();
    }
}
