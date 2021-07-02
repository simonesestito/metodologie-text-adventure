package it.uniroma1.textadv.engine;

import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.characters.Personaggio;
import it.uniroma1.textadv.entita.pojo.characters.Venditore;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.links.Direction;
import it.uniroma1.textadv.entita.pojo.links.Link;
import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Risolve il riferimento a un'entità dal suo nome.
 * Cerca partendo dall'inventario del personaggio, la stanza dove si trova
 * e gli oggetti in essa contenuta che contengono altri oggetti.
 */
public class EntityResolver {
    public Optional<?> resolveEntity(String name) {
        return Optional.empty()
                .or(() -> findEntityInInventory(name))
                .or(() -> findEntityInCurrentRoom(name))
                .or(() -> findEntityInLinks(name))
                .or(() -> findEntityInCharacters(name))
                .or(() -> findEntityInContainers(name))
                .or(() -> findEntityInVirtualReferences(name))
                .or(() -> Direction.of(name));
    }

    private Optional<?> findEntityInVirtualReferences(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getCharacters()
                .stream()
                .filter(p -> p instanceof Venditore)
                .map(p -> (Venditore) p)
                .map(Venditore::getOggettiContenuti)
                .flatMap(Collection::stream)
                .filter(o -> findEntity(o, name))
                .findAny();
    }

    private Optional<Personaggio> findEntityInCharacters(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getCharacters()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findAny();
    }

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

    private Optional<?> findEntityInInventory(String name) {
        return Giocatore.getInstance()
                .getElementiInventario()
                .stream()
                .filter(o -> findEntity(o, name))
                .findAny();
    }

    private Optional<? extends Entity> findEntityInCurrentRoom(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getObjects()
                .stream()
                .filter(o -> o.getName().equals(name))
                .findAny();
    }

    private Optional<?> findEntityInContainers(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getObjects()
                .stream()
                .filter(o -> o instanceof Contenitore)
                .flatMap(o -> ((Contenitore) o).getOggettiContenuti().stream())
                .filter(o -> findEntity(o, name))
                .findAny();
    }

    private boolean findEntity(Object object, String name) {
        if (object instanceof Entity)
            return ((Entity) object).getName().equals(name);
        return object.toString().equals(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static class UnresolvedEntityException extends CommandException.Fatal {
        public UnresolvedEntityException(Oggetto oggetto, Contenitore contenitore) {
            this(oggetto.getName(), contenitore);
        }

        public UnresolvedEntityException(Object nome, Contenitore contenitore) {
            super("Non trovo " + nome + " in  " + contenitore);
        }
    }
}
