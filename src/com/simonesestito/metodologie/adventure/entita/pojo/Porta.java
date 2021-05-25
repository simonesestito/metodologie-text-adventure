package com.simonesestito.metodologie.adventure.entita.pojo;

public class Porta extends Oggetto implements Link {
    private final Stanza stanzaA;
    private final Stanza stanzaB;

    public Porta(String name, Stanza stanzaA, Stanza stanzaB) {
        super(name);
        this.stanzaA = stanzaA;
        this.stanzaB = stanzaB;
    }

    @Override
    public Stanza getStanzaA() {
        return stanzaA;
    }

    @Override
    public Stanza getStanzaB() {
        return stanzaB;
    }
}
