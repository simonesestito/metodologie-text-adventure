package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.engine.EntityResolver;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Personaggio;
import com.simonesestito.metodologie.adventure.entita.pojo.features.*;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Link;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Inventario;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Giocatore che si interfaccia con l'utente via CLI
 *
 * @see Giocatore#rispondiUtente(String)
 * @see Giocatore#userOutput
 */
@SuppressWarnings("unused")
public class Giocatore extends Personaggio {
    private static Giocatore INSTANCE;
    private Stanza currentRoom;
    private final Inventario inventario = new Inventario();
    private final PrintStream userOutput; // Dove rispondere all'utente

    private Giocatore(String name, Stanza stanza, PrintStream userOutput) {
        super(name);
        this.userOutput = userOutput;
        try {
            vaiIn(stanza);
        } catch (UnreachableRoomException e) {
            throw new IllegalArgumentException(e); // Irrecuperabile
        }
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

    public void rispondiUtente(String messaggio) {
        userOutput.println(messaggio);
    }

    public Stanza getCurrentLocation() {
        return currentRoom;
    }

    public void vaiIn(Stanza stanza) throws UnreachableRoomException {
        if (stanza == null)
            throw new UnreachableRoomException();

        currentRoom = stanza;
        rispondiUtente("Ora sono in " + currentRoom);
    }

    public void vai(Direction direction) throws CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .orElseThrow(() -> new CommandException("Destinazione non trovata"))
                .attraversa(getCurrentLocation());
        vaiIn(destination);
    }

    public List<Oggetto> getInventario() {
        return inventario.getOggettiContenuti();
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

    public void apri(ApribileCon<?> apribile) throws CommandException {
        apribile.apri();
        rispondiUtente("Ho aperto " + apribile);
    }

    public <T> void apri(ApribileCon<T> apribile, T oggetto) throws CommandException {
        apribile.apri(oggetto);
        rispondiUtente("Ho aperto " + apribile);
    }

    public void prendiDirezione(Link link) throws CommandException {
        vaiIn(link.attraversa(getCurrentLocation()));
    }

    public void prendi(Oggetto oggetto) throws CommandException {
        oggetto.spostaIn(inventario);
        // TODO Trova: oggetto.prendi();
        // Prova su ogni "contenitore" fornitore di oggetti
        // var containers = getCurrentLocation().getContainers().iterator();
        // while (containers.hasNext()) {
        //     try {
        //         prendi(oggetto, containers.next());
        //         return;
        //     } catch (EntityResolver.UnresolvedEntityException ignored) {
        //     }
        // }
//
        // // Prova nella stanza corrente
        // prendi(oggetto, getCurrentLocation());
    }

    public void prendi(Oggetto oggetto, Contenitore contenitore) throws CommandException {
        if (contenitore.getOggettiContenuti().contains(oggetto)) {
            oggetto.spostaIn(inventario);
            rispondiUtente("Ho preso " + oggetto + " da " + contenitore);
        } else {
            throw new EntityResolver.UnresolvedEntityException(oggetto, contenitore);
        }
    }

    public void mostraInventario() {
        if (getInventario().isEmpty())
            rispondiUtente("Non ho nulla nell'inventario");
        else
            rispondiUtente("Ci sta: " + getInventario().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
    }

    public void accarezza(Accarezzabile accarezzabile) {
        accarezzabile.accarezza();
    }

    public void rompi(Rompibile rompibile, Rompitore rompitore) throws CommandException {
        rompibile.rompi(rompitore);
        rispondiUtente("Fatto: " + rompibile);
    }

    public void rompi(Rompibile rompibile) throws CommandException {
        rompibile.rompi(null);
    }

    public <T> void usa(T oggetto, UsabileCon<T> soggetto) throws CommandException {
        soggetto.usaCon(oggetto);
        rispondiUtente("Ho usato " + oggetto + " con " + soggetto);
    }

    public void usa(Usabile soggetto) throws CommandException {
        soggetto.usa();
    }

    public <T extends Oggetto, R extends Oggetto> void dai(T oggetto, Ricevitore<T, R> ricevitore) throws CommandException {
        inventario.prendiOggetto(oggetto);
        var ricevuto = ricevitore.ricevi(oggetto);

        rispondiUtente("Oggetti ricevuti:");
        for (var oggettoRicevuto : ricevuto) {
            rispondiUtente(oggettoRicevuto.getName());
            oggettoRicevuto.spostaIn(inventario);
        }
    }

    public void entra(Link link) throws CommandException {
        prendiDirezione(link); // alias
    }

    public void parla(Parla parlatore) {
        rispondiUtente(parlatore + " dice: " + parlatore.parla());
    }

    private static class UnreachableRoomException extends CommandException {
        public UnreachableRoomException() {
            this(null);
        }

        public UnreachableRoomException(String room) {
            super("Stanza irraggiungibile" + (room == null ? "" : ": " + room));
        }
    }
}
