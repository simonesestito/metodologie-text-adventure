package com.simonesestito.metodologie.adventure.entita.pojo;

import java.util.*;

public class Stanza extends Entity {
    private final String description;
    private final List<Oggetto> objects = new ArrayList<>();
    private final List<Personaggio> characters = new ArrayList<>();
    private final Map<Direction, Link> links = new HashMap<>();

    public Stanza(String name, String description) {
        super(name);
        this.description = description;
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
}
