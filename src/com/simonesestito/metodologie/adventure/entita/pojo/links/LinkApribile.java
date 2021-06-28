package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileConChiave;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;

public abstract class LinkApribile extends OggettoLink implements ApribileConChiave {
    private boolean aperto = false;

    public LinkApribile(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }

    @Override
    public boolean isAperto() {
        return aperto;
    }

    @Override
    public void apri(Chiave chiave) throws ChiaveErrataException {
        if (chiave.getTarget() == this) {
            aperto = true;
        } else {
            throw new ChiaveErrataException();
        }
    }

    @Override
    public boolean isAttraversabile() {
        return isAperto();
    }
}
