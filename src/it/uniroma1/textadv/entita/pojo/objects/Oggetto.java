package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.PosizionabileUnico;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oggetto oggetto = (Oggetto) o;
        return Objects.equals(posizionabileUnico, oggetto.posizionabileUnico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posizionabileUnico);
    }
}
