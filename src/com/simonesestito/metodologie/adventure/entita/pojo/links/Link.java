package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
