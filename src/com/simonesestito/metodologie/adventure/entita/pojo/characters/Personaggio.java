package com.simonesestito.metodologie.adventure.entita.pojo.characters;

import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.features.Posizionabile;

public class Personaggio extends Entity implements Posizionabile {
    public Personaggio(String name) {
        super(name);
    }
}
