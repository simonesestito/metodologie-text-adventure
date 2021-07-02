package it.uniroma1.textadv.entita.pojo;

import it.uniroma1.textadv.entita.pojo.characters.Personaggio;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.ContenitoreAggiungibile;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.links.Direction;
import it.uniroma1.textadv.entita.pojo.links.Link;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;

import java.util.*;
import java.util.stream.Stream;

public class Stanza extends DescribableEntity implements ContenitoreAggiungibile {
    private final Set<Oggetto> objects = new HashSet<>();
    private final Set<Personaggio> characters = new HashSet<>();
    private final Map<Direction, Link> links = new HashMap<>();

    public Stanza(String name, String description) {
        super(name, description);
    }

    public Set<Oggetto> getObjects() {
        return Collections.unmodifiableSet(objects);
    }

    public Set<Personaggio> getCharacters() {
        return Collections.unmodifiableSet(characters);
    }

    public Map<Direction, Link> getLinks() {
        return Collections.unmodifiableMap(links);
    }

    public Optional<Link> getLink(Direction direction) {
        return Optional.ofNullable(links.get(direction));
    }

    @Override
    public void aggiungiOggetto(Posizionabile oggetto) {
        if (oggetto instanceof Oggetto)
            objects.add((Oggetto) oggetto);
    }

    public void addCharacter(Personaggio personaggio) {
        characters.add(personaggio);
    }

    public void addLink(Link link, Direction direction) {
        links.put(direction, link);
    }

    @Override
    public Set<Oggetto> getOggettiContenuti() {
        return getObjects();
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) {
        if (oggetto instanceof Oggetto)
            objects.remove(oggetto);
    }

    public Stream<Contenitore> getContainers() {
        return Stream.concat(
                objects.stream(),
                characters.stream()
        ).filter(p -> p instanceof Contenitore).map(p -> (Contenitore) p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Stanza stanza = (Stanza) o;
        return Objects.equals(getObjects(), stanza.getObjects()) && Objects.equals(getCharacters(), stanza.getCharacters()) && Objects.equals(getLinks(), stanza.getLinks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getObjects(), getCharacters(), getLinks());
    }
}
