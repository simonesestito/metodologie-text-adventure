package com.simonesestito.metodologie.adventure.entita.pojo.characters;

import com.simonesestito.metodologie.adventure.entita.pojo.Giocatore;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Accarezzabile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Parla;

public class Gatto extends Personaggio implements Accarezzabile, Parla {
    private static final String MIAGOLIO = "Miao!";

    public Gatto(String name) {
        super(name);
    }

    @Override
    public void accarezza() {
        Giocatore.getInstance().rispondiUtente(MIAGOLIO);
    }

    @Override
    public String parla() {
        return MIAGOLIO;
    }
}
