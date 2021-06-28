package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Personaggio;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Link;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.util.*;

public class Stanza extends DescribableEntity implements Contenitore {
    private final List<Oggetto> objects = new ArrayList<>();
    private final List<Personaggio> characters = new ArrayList<>();
    private final Map<Direction, Link> links = new HashMap<>();

    public Stanza(String name, String description) {
        super(name, description);
    }

    public List<Oggetto> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public List<Personaggio> getCharacters() {
        return Collections.unmodifiableList(characters);
    }

    public Map<Direction, Link> getLinks() {
        return Collections.unmodifiableMap(links);
    }

    public Optional<Link> getLink(Direction direction) {
        return Optional.ofNullable(links.get(direction));
    }

    public void addObject(Oggetto oggetto) {
        objects.add(oggetto);
    }

    public void addCharacter(Personaggio personaggio) {
        characters.add(personaggio);
    }

    public void addLink(Link link, Direction direction) {
        links.put(direction, link);
    }

    @Override
    public List<Oggetto> getOggettiContenuti() {
        return getObjects();
    }

    @Override
    public void prendiOggetto(Oggetto oggetto) {
        objects.remove(oggetto);
    }
}
