package it.uniroma1.textadv.entita.pojo.links;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.Stanza;

/**
 * Rappresenta un bus navetta nel gioco, che collega due stanza
 */
public class Bus extends OggettoLink {
    /**
     * Crea un nuovo bus
     *
     * @param name    Nome del bus
     * @param stanzaA Stanza collegata
     * @param stanzaB Stanza collegata
     */
    public Bus(String name, Stanza stanzaA, Stanza stanzaB) {
        super(name, stanzaA, stanzaB);
    }

    /**
     * Sali sul bus, attraversandolo nel senso di collegamento
     *
     * @param da Stanza da cui partire
     * @return Altra stanza dove arrivare
     * @throws LinkNotUsableException Errore nell'attraversamento
     */
    @Override
    public Stanza attraversa(Stanza da) throws LinkNotUsableException {
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
