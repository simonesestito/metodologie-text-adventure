package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.features.*;

import java.util.Objects;

public class Gatto extends Personaggio implements Accarezzabile, Parla, Posizionabile {
    private static final String MIAGOLIO = "Miao!";
    private final PosizionabileUnico posizionabileUnico = new PosizionabileUnico(this);

    public Gatto(String name) {
        super(name);
    }

    @Override
    public void accarezza() {
        Giocatore.getInstance().rispondiUtente(MIAGOLIO);
    }

    @Override
    public String parla() {
        return MIAGOLIO;
    }

    @Override
    public Contenitore getPosizione() {
        return posizionabileUnico.getPosizione();
    }

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        posizionabileUnico.spostaIn(contenitore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gatto gatto = (Gatto) o;
        return Objects.equals(posizionabileUnico, gatto.posizionabileUnico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posizionabileUnico);
    }
}
