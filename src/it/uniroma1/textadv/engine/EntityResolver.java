package it.uniroma1.textadv.engine;

import it.uniroma1.textadv.entity.pojo.Entity;
import it.uniroma1.textadv.entity.pojo.characters.Personaggio;
import it.uniroma1.textadv.entity.pojo.links.Direction;
import it.uniroma1.textadv.entity.pojo.links.Link;
import it.uniroma1.textadv.entity.pojo.characters.Giocatore;

import java.util.Optional;

/**
 * Risolve il riferimento a un'entità dal suo nome.
 * Cerca partendo dall'inventario del personaggio, la stanza dove si trova
 * e gli oggetti in essa contenuta che contengono altri oggetti.
 */
public class EntityResolver {
    /**
     * Metodo esterno per la risoluzione di un'entità dal suo nome
     * @param name Nome dell'entità richiesta
     * @return Entità risolta, o <code>Optional.empty()</code>
     */
    public Optional<?> resolveEntity(String name) {
        return Optional.empty()
                .or(() -> findEntityInInventory(name))
                .or(() -> findEntityInCurrentRoom(name))
                .or(() -> findEntityInLinks(name))
                .or(() -> findEntityInCharacters(name))
                .or(() -> findEntityInContainers(name))
                .or(() -> Direction.of(name));
    }

    /**
     * Cerca questa entità come personaggio della stanza corrente.
     * @param name Nome dell'entità richiesta
     * @return Entità risolta, o <code>Optional.empty()</code>
     */
    private Optional<Personaggio> findEntityInCharacters(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getCharacters()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findAny();
    }

    /**
     * Cerca questa entità come link della stanza corrente.
     * Sia come nome dell'oggetto link, che come stanza.
     * @param name Nome dell'entità richiesta
     * @return Entità risolta, o <code>Optional.empty()</code>
     */
    private Optional<Link> findEntityInLinks(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getLinks()
                .values()
                .stream()
                .filter(link -> {
                    if (link instanceof Entity)
                        return ((Entity) link).getName().equals(name);
                    return link.getRooms().anyMatch(s -> s.getName().equals(name));
                }).findAny();
    }

    /**
     * Cerca questa entità come oggetto nell'inventario del protagonista.
     * @param name Nome dell'entità richiesta
     * @return Entità risolta, o <code>Optional.empty()</code>
     */
    private Optional<?> findEntityInInventory(String name) {
        return Giocatore.getInstance()
                .getElementiInventario()
                .stream()
                .filter(o -> findEntity(o, name))
                .findAny();
    }

    /**
     * Cerca questa entità come oggetto nella stanza corrente.
     * @param name Nome dell'entità richiesta
     * @return Entità risolta, o <code>Optional.empty()</code>
     */
    private Optional<? extends Entity> findEntityInCurrentRoom(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getOggettiContenuti()
                .stream()
                .filter(o -> o.getName().equals(name))
                .findAny();
    }

    /**
     * Cerca questa entità come oggetto nei contenitori della stanza corrente.
     * @param name Nome dell'entità richiesta
     * @return Entità risolta, o <code>Optional.empty()</code>
     */
    private Optional<?> findEntityInContainers(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getContainers()
                .flatMap(o -> o.getOggettiContenuti().stream())
                .filter(o -> findEntity(o, name))
                .findAny();
    }

    /**
     * Controlla se un'entità combacia con il nome richiesto.
     * @param object Oggetto da verificare
     * @param name Nome richiesto
     * @return <code>true</code> se l'oggetto dato è l'entità richiesta
     */
    private boolean findEntity(Object object, String name) {
        if (object instanceof Entity)
            return ((Entity) object).getName().equals(name);
        return object.toString().equals(name);
    }

    /**
     * Controlla se due oggetti sono considerati uguali
     * @param obj Altro oggetto
     * @return <code>true</code> se i due oggetti sono uguali
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && obj.getClass() == getClass();
    }

}
