package com.simonesestito.metodologie.adventure.entita;

import java.util.Collections;
import java.util.List;

public class Stanza {
    private final String name;
    private final String description;
    private final List<Oggetto> objects;
    private final List<Personaggio> characters;
    // TODO: Stanze e link


    public Stanza(String name, String description, List<Oggetto> objects, List<Personaggio> characters) {
        this.name = name;
        this.description = description;
        this.objects = objects;
        this.characters = characters;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Oggetto> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public List<Personaggio> getCharacters() {
        return Collections.unmodifiableList(characters);
    }
}
