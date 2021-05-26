package com.simonesestito.metodologie.adventure.entita.pojo;

import java.util.Objects;

public interface Link {
    Stanza getStanzaA();
    Stanza getStanzaB();

    default Stanza getDestinazione(Stanza da) {
        if (Objects.equals(getStanzaA(), da)) {
            return getStanzaB();
        } else if (Objects.equals(getStanzaB(), da)) {
            return getStanzaA();
        } else {
            return null;
        }
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
        };
    }
}
