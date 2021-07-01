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
    public List<Oggetto> getOggettiContenuti() {
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
}
