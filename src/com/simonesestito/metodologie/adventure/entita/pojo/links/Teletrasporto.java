package com.simonesestito.metodologie.adventure.entita.pojo.links;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.Giocatore;
import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileCon;
import com.simonesestito.metodologie.adventure.entita.pojo.features.ApribileConChiave;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Usabile;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;

public class Teletrasporto extends OggettoLink implements ApribileCon<Chiave>, Usabile {
    private final ApribileConChiave apertura = new ApribileConChiave();

    public Teletrasporto(String name, Stanza stanzaB, Stanza stanzaA) {
        super(name, stanzaB, stanzaA);
    }

    @Override
    public void apri() throws AperturaException {
        apertura.apri();
    }

    @Override
    public void apri(Chiave oggetto) throws AperturaException {
        apertura.apri(oggetto);

        // Quando viene aperto, immediatamente teletrasporta
        // Vedasi comando:
        // -> usa chiave teletrasporto su teletrasporto // vengo teletrasportato nella piccola stanza misteriosa
        try {
            Giocatore.getInstance().prendiDirezione(this);
        } catch (CommandException e) {
            throw new AperturaException();
        }
    }

    @Override
    public void chiudi(Chiave oggetto) throws ChiusuraException {
        apertura.chiudi(oggetto);
    }

    @Override
    public boolean isAperto() {
        return apertura.isAperto();
    }

    @Override
    public void usa() throws CommandException {
        Giocatore.getInstance().entra(this);
    }
}
