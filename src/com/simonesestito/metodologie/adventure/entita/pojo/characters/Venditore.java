package com.simonesestito.metodologie.adventure.entita.pojo.characters;

import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.util.List;

public class Venditore extends Personaggio {
    private final List<Oggetto> oggettiVenduti;

    public Venditore(String name, List<Oggetto> oggettiVenduti) {
        super(name);
        this.oggettiVenduti = oggettiVenduti;
    }
}
