package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.Stanza;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;

/**
 * Rappresentazione dei soldi nel gioco
 */
public class Soldi extends Oggetto {
    /**
     * Crea nuovi soldi
     * @param name Nome dei soldi creati
     */
    public Soldi(String name) {
        super(name);
    }

    /**
     * Sposta i soldi altrove
     * @param contenitore Nuovo contenitore dove stare
     * @throws CommandException Errore nello spostamento
     */
    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        super.spostaIn(contenitore);
        if (contenitore instanceof Stanza) {
            Giocatore.getInstance().rispondiUtente("""
                    ||====================================================================||
                    ||//$\\\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\//$\\\\||
                    ||(100)==================| FEDERAL RESERVE NOTE |================(100)||
                    ||\\\\$//        ~         '------========--------'                \\\\$//||
                    ||<< /        /$\\              // ____ \\\\                         \\ >>||
                    ||>>|  12    //L\\\\            // ///..) \\\\         L38036133B   12 |<<||
                    ||<<|        \\\\ //           || <||  >\\  ||                        |>>||
                    ||>>|         \\$/            ||  $$ --/  ||        One Hundred     |<<||
                    ||<<|      L38036133B        *\\\\  |\\_/  //* series                 |>>||
                    ||>>|  12                     *\\\\/___\\_//*   1989                  |<<||
                    ||<<\\      Treasurer     ______/Franklin\\________     Secretary 12 />>||
                    ||//$\\                 ~|UNITED STATES OF AMERICA|~               /$\\\\||
                    ||(100)===================  ONE HUNDRED DOLLARS =================(100)||
                    ||\\\\$//\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\\\$//||
                    ||====================================================================||
                    """);
        }
    }
}
