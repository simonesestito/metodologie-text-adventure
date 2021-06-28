package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Link;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

public abstract class OggettoLink extends Oggetto implements Link {
    private final Stanza stanzaA;
    private final Stanza stanzaB;

    public OggettoLink(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name);
        this.stanzaB = stanzaB;
        this.stanzaA = stanzaA;
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
