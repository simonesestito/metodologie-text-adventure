package it.uniroma1.textadv.entita.pojo.links;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.Stanza;

public class Bus extends OggettoLink {
    public Bus(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Stanza attraversa(Stanza da) throws CommandException {
        var stanza = super.attraversa(da);
        Giocatore.getInstance().rispondiUtente("""
                   ___________________
                   |,-----.,-----.,---.\\
                   ||     ||     ||    \\\\
                   |`-----'|-----||-----\\`----.
                   [       |    -||-   _|    (|
                   [  ,--. |_____||___/.--.   |
                   =-(( `))-----------(( `))-==
                      `--'             `--'
                """);
        return stanza;
    }
}
