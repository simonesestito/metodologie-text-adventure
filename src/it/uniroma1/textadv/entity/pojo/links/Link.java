package it.uniroma1.textadv.entity.pojo.links;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.Stanza;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Collegamento tra due stanze nel gioco
 */
public interface Link {
    /**
     * Una delle due stanze collegate
     * @return Stanza collegata
     */
    Stanza getStanzaA();

    /**
     * Una delle due stanze collegate
     * @return Stanza collegata
     */
    Stanza getStanzaB();

    /**
     * Attraversa il collegamento per andare nell'altra stanza
     * @param da Da dove viene attraversato il collegamento
     * @return Destinazione raggiunta
     * @throws LinkNotUsableException Errore nell'attraversamento
     */
    default Stanza attraversa(Stanza da) throws LinkNotUsableException {
        if (!isAttraversabile())
            throw new LinkNotUsableException(this);

        if (Objects.equals(getStanzaA(), da)) {
            return getStanzaB();
        } else if (Objects.equals(getStanzaB(), da)) {
            return getStanzaA();
        } else {
            return null;
        }
    }

    /**
     * Controlla se il collegamento è attraversabile
     * @return <code>true</code> se è attraversabile
     */
    default boolean isAttraversabile() {
        return true;
    }

    /**
     * Crea un nuovo link diretto, senza ricorrere ad un oggetto, per collegare due stanze
     * @param a Stanza collegata
     * @param b Stanza collegata
     * @return Nuovo link senza essere un oggetto tra le due stanze date
     */
    static Link createDirect(Stanza a, Stanza b) {
        return new Link() {
            @Override
            public Stanza getStanzaA() {
                return a;
            }

            @Override
            public Stanza getStanzaB() {
                return b;
            }

            @Override
            public String toString() {
                return a + " <=> " + b;
            }
        };
    }

    /**
     * Ottieni lo stream delle stanze raggiunte da questo link
     * @return Stanze collegate dal link
     */
    default Stream<Stanza> getRooms() {
        return Stream.of(getStanzaA(), getStanzaB());
    }

    /**
     * Eccezione in caso non sia possibile utilizzare un collegamento
     */
    class LinkNotUsableException extends CommandException {
        /**
         * Crea un'eccezione per un link non utilizzabile
         * @param link Link non utilizzabile
         */
        public LinkNotUsableException(Link link) {
            super(Strings.of(StringId.LINK_NOT_USABLE, link.toString()));
        }
    }
}
