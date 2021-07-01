package it.uniroma1.textadv.entita.pojo.links;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Stanza;

import java.util.Objects;
import java.util.stream.Stream;

public interface Link {
    Stanza getStanzaA();
    Stanza getStanzaB();

    default Stanza attraversa(Stanza da) throws CommandException {
        if (!isAttraversabile())
            throw new CommandException("Collegamento non attraversabile: " + this);

        if (Objects.equals(getStanzaA(), da)) {
            return getStanzaB();
        } else if (Objects.equals(getStanzaB(), da)) {
            return getStanzaA();
        } else {
            return null;
        }
    }

    default boolean isAttraversabile() {
        return true;
    }

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

    default Stream<Stanza> getRooms() {
        return Stream.of(getStanzaA(), getStanzaB()).filter(Objects::nonNull);
    }
}
