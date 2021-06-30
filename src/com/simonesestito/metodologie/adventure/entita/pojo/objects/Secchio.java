package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Usabile;

public class Secchio extends Oggetto implements Usabile<Pozzo> {
    private boolean riempito;

    public Secchio(String name) {
        super(name);
    }

    public boolean isRiempito() {
        return riempito;
    }

    private void setRiempito(boolean riempito) {
        this.riempito = riempito;
    }

    public void riempi() {
        setRiempito(true);
    }

    @Override
    public void usa(Pozzo pozzo) throws CommandException {
        if (pozzo != null)
            riempi();
    }
}
