package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.features.*;
import it.uniroma1.textadv.entity.pojo.links.Botola;

import java.util.Objects;

/**
 * Rappresentazione della vite, oggetto che può controllare l'apertura di altri oggetti
 *
 * @see Botola
 */
public class Vite extends Oggetto implements BloccoApertura, UsabileCon<Cacciavite> {
    /**
     * Oggetto di cui controlla l'apertura
     */
    private final ApribileCon<Vite> botola;

    /**
     * Stato di blocco della vite
     */
    private boolean bloccato = true;

    /**
     * Crea una nuova vite, già bloccata
     *
     * @param name   Nome della nuova vite
     * @param botola Oggetto di cui controlla l'apertura
     * @throws ApribileCon.ChiusuraException Errore nella chiusura dell'oggetto che controlla
     */
    public Vite(String name, ApribileCon<Vite> botola) throws ApribileCon.ChiusuraException {
        super(name);
        this.botola = botola;
        botola.chiudi(this);
    }

    /**
     * Verifica se la vite è bloccata
     *
     * @return <code>true</code> se la vite è bloccata
     */
    @Override
    public boolean isBloccato() {
        return bloccato;
    }

    /**
     * Usa la vite con un cacciavite generico
     *
     * @param cacciavite Cacciavite da usare
     */
    @Override
    public void usaCon(Cacciavite cacciavite) {
        if (cacciavite != null)
            bloccato = false;
    }

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        if (isBloccato() && getPosizione() != null && !getPosizione().equals(contenitore))
            throw new Observable.LockedObjectException();
        super.spostaIn(contenitore);
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due viti uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due viti uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vite vite = (Vite) o;
        return isBloccato() == vite.isBloccato() && Objects.equals(botola, vite.botola);
    }

    /**
     * Calcola l'hash della vite
     *
     * @return Hash della vite
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), botola);
    }
}
