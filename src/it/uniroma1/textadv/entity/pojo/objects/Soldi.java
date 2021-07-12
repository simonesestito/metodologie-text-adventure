package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.Stanza;
import it.uniroma1.textadv.entity.pojo.characters.Giocatore;
import it.uniroma1.textadv.entity.pojo.features.Contenitore;

import java.util.Objects;

/**
 * Rappresentazione dei soldi nel gioco
 */
public class Soldi extends Oggetto {
    /**
     * Crea nuovi soldi
     *
     * @param name Nome dei soldi creati
     */
    public Soldi(String name) {
        super(name);
    }

    /**
     * Sposta i soldi altrove
     *
     * @param contenitore Nuovo contenitore dove stare
     * @throws CommandException Errore nello spostamento
     */
    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        var vecchiaPosizione = getPosizione();
        super.spostaIn(contenitore);
        if (!Objects.equals(contenitore, vecchiaPosizione) && contenitore instanceof Stanza) {
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
