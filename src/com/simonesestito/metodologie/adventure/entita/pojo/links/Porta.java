package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileConChiave;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;

public class Porta extends LinkApribile {
    public Porta(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }
}
