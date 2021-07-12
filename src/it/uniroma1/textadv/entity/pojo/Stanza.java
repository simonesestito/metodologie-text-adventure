package it.uniroma1.textadv.entity.pojo;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.characters.Personaggio;
import it.uniroma1.textadv.entity.pojo.features.Contenitore;
import it.uniroma1.textadv.entity.pojo.features.ContenitoreAggiungibile;
import it.uniroma1.textadv.entity.pojo.features.Posizionabile;
import it.uniroma1.textadv.entity.pojo.links.Direction;
import it.uniroma1.textadv.entity.pojo.links.Link;
import it.uniroma1.textadv.entity.pojo.objects.Oggetto;

import java.util.*;
import java.util.stream.Stream;

/**
 * Rappresentazione di un qualsiasi luogo all'interno del mondo, non necessariamente una stanza.
 * <p>
 * Una stanza contiene oggetti ed è possibile aggiungerne altri.
 */
public class Stanza extends DescribableEntity implements ContenitoreAggiungibile {
    /**
     * Oggetti contenuti nella stanza
     */
    private final Set<Oggetto> objects = new HashSet<>();

    /**
     * Personaggi in questa stanza
     */
    private final Set<Personaggio> characters = new HashSet<>();

    /**
     * Collegamenti da questa stanza alle altre, indicizzati per direzione
     */
    private final Map<Direction, Link> links = new HashMap<>();

    /**
     * Crea una nuova stanza
     *
     * @param name        Nome della stanza
     * @param description Descrizione della stanza
     */
    public Stanza(String name, String description) {
        super(name, description);
    }

    /**
     * Ottieni una vista immutabile dei personaggi nella stanza
     *
     * @return Personaggi nella stanza
     */
    public Set<Personaggio> getCharacters() {
        return Collections.unmodifiableSet(characters);
    }

    /**
     * Ottieni una vista immutabile dei collegamenti della stanza
     *
     * @return Collegamenti della stanza
     */
    public Map<Direction, Link> getLinks() {
        return Collections.unmodifiableMap(links);
    }

    /**
     * Ottieni un collegamento in una data direzione, se esso è presente
     *
     * @param direction Direzione del collegamento
     * @return Collegamento trovato, o {@link Optional#empty()}
     */
    public Optional<Link> getLink(Direction direction) {
        return Optional.ofNullable(links.get(direction));
    }

    /**
     * Aggiungi un nuovo oggetto alla stanza
     *
     * @param oggetto Oggetto da aggiungere
     */
    @Override
    public void aggiungiOggetto(Posizionabile oggetto) throws CommandException {
        if (oggetto instanceof Oggetto && !objects.contains(oggetto)) {
            objects.add((Oggetto) oggetto);
            oggetto.spostaIn(this);
        }
    }

    /**
     * Aggiungi un personaggio in questa stanza
     *
     * @param personaggio Personaggio da aggiungere
     */
    public void addCharacter(Personaggio personaggio) {
        characters.add(personaggio);
    }

    /**
     * Aggiungi un collegamento da questa stanza ad altre
     *
     * @param link      Collegamento da aggiungere
     * @param direction Direzione del collegamento
     */
    public void addLink(Link link, Direction direction) {
        if (link.getRooms().noneMatch(s -> s.equals(this)))
            throw new IllegalArgumentException();
        links.put(direction, link);
    }

    /**
     * Ottieni una vista immutabile degli oggetti nella stanza
     *
     * @return Oggetti nella stanza
     */
    @Override
    public Set<Oggetto> getOggettiContenuti() {
        return Collections.unmodifiableSet(objects);
    }

    /**
     * Prendi un oggetto da questa stanza
     *
     * @param oggetto Oggetto da prendere
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) {
        if (oggetto instanceof Oggetto)
            objects.remove(oggetto);
    }

    /**
     * Ottieni uno stream di tutte le entità varie che contengono oggetti, all'interno della stanza
     *
     * @return Stream dei contenitori di oggetti
     */
    public Stream<Contenitore> getContainers() {
        return Stream.concat(
                objects.stream(),
                characters.stream()
        ).filter(p -> p instanceof Contenitore).map(p -> (Contenitore) p);
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due stanze uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due stanze uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Stanza stanza = (Stanza) o;
        return Objects.equals(objects, stanza.objects) && Objects.equals(getCharacters(), stanza.getCharacters());
    }

    /**
     * Calcola l'hash della stanza
     *
     * @return hash calcolato
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), objects, getCharacters());
    }
}
