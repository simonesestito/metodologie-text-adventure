package it.uniroma1.textadv.entita.pojo;

import it.uniroma1.textadv.Gioco;
import it.uniroma1.textadv.Mondo;
import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.characters.Personaggio;
import it.uniroma1.textadv.entita.pojo.features.*;
import it.uniroma1.textadv.entita.pojo.links.Direction;
import it.uniroma1.textadv.entita.pojo.links.Link;
import it.uniroma1.textadv.entita.pojo.objects.Tesoro;

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
    private final PrintStream userOutput; // Dove rispondere all'utente
    private Stanza currentRoom;

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

    public List<Posizionabile> getElementiInventario() {
        return getInventario().getOggettiContenuti();
    }

    public void vaiIn(Stanza stanza) throws UnreachableRoomException {
        if (stanza == null)
            throw new UnreachableRoomException();

        if (currentRoom != null)
            rispondiUtente("Ora sono in " + stanza);

        currentRoom = stanza;
    }

    public void vai(Direction direction) throws CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .orElseThrow(() -> new CommandException("Destinazione non trovata"))
                .attraversa(getCurrentLocation());
        vaiIn(destination);
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

    public void prendi(Posizionabile oggetto) throws CommandException {
        oggetto.spostaIn(getInventario());
    }

    public void prendi(Posizionabile oggetto, Contenitore contenitore) throws CommandException {
        oggetto.spostaIn(getInventario());
        rispondiUtente("Ho preso " + oggetto + " da " + contenitore);
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

    public <T extends Entity & Posizionabile, R extends Entity & Posizionabile> void dai(T oggetto, Ricevitore<? super T, R> ricevitore) throws CommandException {
        getInventario().prendiOggetto(oggetto);
        var ricevuto = ricevitore.ricevi(oggetto);

        if (!ricevuto.isEmpty()) {
            rispondiUtente("Oggetti ricevuti:");
            for (var oggettoRicevuto : ricevuto) {
                rispondiUtente(oggettoRicevuto.getName());
                oggettoRicevuto.spostaIn(getInventario());
            }
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
