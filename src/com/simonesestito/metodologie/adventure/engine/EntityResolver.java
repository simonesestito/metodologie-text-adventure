package com.simonesestito.metodologie.adventure.engine;

import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Personaggio;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Venditore;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Link;
import com.simonesestito.metodologie.adventure.entita.pojo.Giocatore;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.util.List;
import java.util.Optional;

/**
 * Risolve il riferimento a un'entità dal suo nome.
 * Cerca partendo dall'inventario del personaggio, la stanza dove si trova
 * e gli oggetti in essa contenuta che contengono altri oggetti.
 */
public class EntityResolver {
    public Optional<?> resolveEntity(String name) {
        System.out.println("name = " + name);
        return Optional.empty()
                .or(() -> findEntityInInventory(name))
                .or(() -> findEntityInCurrentRoom(name))
                .or(() -> findEntityInLinks(name))
                .or(() -> findEntityInCharacters(name))
                .or(() -> findEntityInContainers(name))
                .or(() -> findEntityInVirtualReferences(name))
                .or(() -> Direction.of(name));
    }

    private Optional<Oggetto> findEntityInVirtualReferences(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getCharacters()
                .stream()
                .filter(p -> p instanceof Venditore)
                .map(p -> (Venditore) p)
                .map(Venditore::getOggettiContenuti)
                .flatMap(List::stream)
                .filter(o -> o.getName().equals(name))
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

    private Optional<? extends Entity> findEntityInInventory(String name) {
        return Giocatore.getInstance()
                .getInventario()
                .stream()
                .filter(o -> o.getName().equals(name))
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

    private Optional<? extends Entity> findEntityInContainers(String name) {
        return Giocatore.getInstance()
                .getCurrentLocation()
                .getObjects()
                .stream()
                .filter(o -> o instanceof Contenitore)
                .flatMap(o -> ((Contenitore) o).getOggettiContenuti().stream())
                .filter(o -> o.getName().equals(name))
                .findAny();
    }

    public static class UnresolvedEntityException extends CommandException.Fatal {
        public UnresolvedEntityException(Oggetto oggetto, Contenitore contenitore) {
            super("Non trovo " + oggetto.getName() + " in  " + contenitore);
        }
    }
}
