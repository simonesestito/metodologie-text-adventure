package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.engine.EntityResolver;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Personaggio;
import com.simonesestito.metodologie.adventure.entita.pojo.features.*;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Link;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Giocatore che si interfaccia con l'utente via CLI
 */
public class Giocatore extends Personaggio {
    private static Giocatore INSTANCE;
    private Stanza currentRoom;
    private final List<Oggetto> inventario = new ArrayList<>();
    private final PrintStream userOutput; // Dove rispondere all'utente

    private Giocatore(String name, Stanza stanza, PrintStream userOutput) {
        super(name);
        this.userOutput = userOutput;
        vaiIn(stanza);
    }

    private Giocatore(String name, Stanza stanza) {
        this(name, stanza, System.out);
    }

    public static Giocatore init(String nome, Mondo mondo) {
        return init(nome, mondo.getStart());
    }

    public static Giocatore init(String nome, Stanza stanza) {
        if (INSTANCE != null)
            throw new IllegalStateException("Giocatore già inizializzato!");
        INSTANCE = new Giocatore(nome, stanza);
        return getInstance();
    }

    public static Giocatore getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }

    private void rispondiUtente(String messaggio) {
        userOutput.println(messaggio);
    }

    public Stanza getCurrentLocation() {
        return currentRoom;
    }

    public void vaiIn(Stanza stanza) {
        currentRoom = stanza;
    }

    public void vai(Direction direction) throws CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .orElseThrow(() -> new CommandException("Destinazione non trovata"))
                .attraversa(getCurrentLocation());
        vaiIn(destination);
        rispondiUtente("Ora sono in " + currentRoom);
    }

    public List<Oggetto> getInventario() {
        return Collections.unmodifiableList(inventario);
    }

    public void guarda() {
        rispondiUtente("Sono in " + currentRoom);

        if (currentRoom.getObjects().isEmpty()) {
            rispondiUtente("Non ci sono oggetti qui");
        } else {
            rispondiUtente("Qui intorno vedo " + currentRoom.getObjects().stream()
                    .map(Entity::getName)
                    .collect(Collectors.joining(", ")));
        }

        if (currentRoom.getCharacters().isEmpty()) {
            rispondiUtente("Sono da solo in questo posto");
        } else {
            rispondiUtente("Vedo anche " + currentRoom.getCharacters().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
        }

        rispondiUtente("Posso andare verso:");
        currentRoom.getLinks().forEach((direction, link) -> rispondiUtente(direction.name() + " -> " + link));
    }

    public void guarda(Entity entity) {
        rispondiUtente("Sto vedendo " + entity);
    }

    public void apri(Apribile<?> apribile) throws CommandException {
        apribile.apri();
        rispondiUtente("Ho aperto " + apribile);
    }

    public <T> void apri(Apribile<T> apribile, T oggetto) throws CommandException {
        apribile.apri(oggetto);
        rispondiUtente("Ho aperto " + apribile);
    }

    public void prendiDirezione(Link link) throws CommandException {
        vaiIn(link.attraversa(getCurrentLocation()));
    }

    public void prendi(Oggetto oggetto) throws CommandException {
        // Prova su ogni "contenitore" fornitore di oggetti
        var containers = getCurrentLocation()
                .getCharacters()
                .stream()
                .filter(p -> p instanceof Contenitore)
                .map(p -> (Contenitore) p)
                .iterator();

        while (containers.hasNext()) {
            try {
                prendi(oggetto, containers.next());
                return;
            } catch (EntityResolver.UnresolvedEntityException ignored) {
            }
        }

        // Prova nella stanza corrente
        prendi(oggetto, getCurrentLocation());
    }

    public void prendi(Oggetto oggetto, Contenitore contenitore) throws CommandException {
        if (contenitore.getOggettiContenuti().contains(oggetto)) {
            contenitore.prendiOggetto(oggetto);
            inventario.add(oggetto);
            rispondiUtente("Ho preso " + oggetto);
        } else {
            throw new EntityResolver.UnresolvedEntityException("Non trovo " + oggetto.getName() + " in " + contenitore);
        }
    }

    public void mostraInventario() {
        if (getInventario().isEmpty())
            rispondiUtente("Non ho nulla nell'inventario");
        else
            rispondiUtente("Ci sta: " + getInventario().stream()
                    .map(Entity::getName)
                    .collect(Collectors.joining(", ")));
    }

    public void accarezza(Accarezzabile accarezzabile) {
        accarezzabile.accarezza();
    }

    public void rompi(Rompibile rompibile, Rompitore rompitore) throws CommandException {
        rompibile.rompi(rompitore).forEach(getCurrentLocation()::addObject);
        rispondiUtente("Fatto: " + rompibile);
    }

    public void rompi(Rompibile rompibile) throws CommandException {
        rompibile.rompi(null).forEach(getCurrentLocation()::addObject);
    }

    public <T> void usa(Usabile<T> soggetto, T oggetto) throws CommandException {
        if (oggetto instanceof Rompibile) {
            rompi((Rompibile) oggetto, (Rompitore) soggetto);
        } else {
            soggetto.usa(oggetto);
            rispondiUtente("Ho usato " + soggetto + " su " + oggetto);
        }
    }

    public <T extends Oggetto, R extends Oggetto> void dai(T oggetto, Ricevitore<T, R> ricevitore) throws CommandException.Fatal {
        if (!inventario.contains(oggetto))
            throw new CommandException.Fatal("Non posseggo " + oggetto);

        var ricevuto = ricevitore.ricevi(oggetto);
        inventario.remove(oggetto);
        rispondiUtente("Ricevuti: " + ricevuto.stream()
                .map(Entity::toString)
                .collect(Collectors.joining(", ")));
        inventario.addAll(ricevuto);
    }
}
