package it.uniroma1.textadv.entity.pojo.links;

import it.uniroma1.textadv.entity.pojo.characters.Giocatore;
import it.uniroma1.textadv.entity.pojo.Stanza;
import it.uniroma1.textadv.entity.pojo.features.ApribileCon;
import it.uniroma1.textadv.entity.pojo.features.ApribileConBlocco;
import it.uniroma1.textadv.entity.pojo.objects.Vite;

import java.util.Objects;

/**
 * Botola come oggetto che collega due stanze, ad apertura controllata da un altro oggetto
 */
public class Botola extends OggettoLink implements ApribileCon<Vite> {
    /**
     * Implementazione parametrizzata (Strategy) dell'apertura
     */
    private final ApribileConBlocco<Vite> apribileConBlocco = new ApribileConBlocco<>();

    /**
     * Crea una nuova botola
     * @param name Nome della botola
     * @param stanzaA Stanza collegata
     * @param stanzaB Stanza collegata
     */
    public Botola(String name, Stanza stanzaA, Stanza stanzaB) {
        super(name, stanzaA, stanzaB);
    }

    /**
     * Apri la botola con un oggetto
     * @param oggetto Oggetto con cui aprire
     * @throws AperturaException Errore nell'apertura
     */
    @Override
    public void apri(Vite oggetto) throws AperturaException {
        apribileConBlocco.apri(oggetto);
        Giocatore.getInstance().rispondiUtente("""
                                          _.-'
                                        _.-'
                        _____________.-'________________
                       /         _.-' O                /|
                      /  i====_======O      __________/ /
                     /  / _.-'      O      /     _   /|/
                    /  / | p       o      /     (   / /
                   /  /           O      /_________/ /
                  /  L===========O                /|/
                 /______________O________________/ /
                |________________________________|/
                """);
    }

    /**
     * Chiudi la botola con un oggetto
     * @param oggetto Oggetto con cui chiudere
     * @throws ChiusuraException Errore nella chiusura
     */
    @Override
    public void chiudi(Vite oggetto) throws ChiusuraException {
        apribileConBlocco.chiudi(oggetto);
    }

    /**
     * Verifica se la botola è aperta
     * @return Botola aperta
     */
    @Override
    public boolean isAperto() {
        return apribileConBlocco.isAperto();
    }

    /**
     * Controlla se la botola intesa come collegamento è attraversabile
     * @return <code>true</code> se è attraversabile
     */
    @Override
    public boolean isAttraversabile() {
        return isAperto();
    }

    /**
     * Verifica l'eguaglianza tra due botole
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se le botole sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Botola botola = (Botola) o;
        return Objects.equals(apribileConBlocco, botola.apribileConBlocco);
    }
}
