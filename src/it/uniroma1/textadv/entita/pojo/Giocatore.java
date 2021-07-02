package it.uniroma1.textadv.entita.pojo;

import it.uniroma1.textadv.Mondo;
import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.characters.Personaggio;
import it.uniroma1.textadv.entita.pojo.features.*;
import it.uniroma1.textadv.entita.pojo.links.Direction;
import it.uniroma1.textadv.entita.pojo.links.Link;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Set;
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
            throw new IllegalStateException();
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

    public Set<Posizionabile> getElementiInventario() {
        return getInventario().getOggettiContenuti();
    }

    public void vaiIn(Stanza stanza) throws UnreachableRoomException {
        if (stanza == null)
            throw new UnreachableRoomException();

        if (currentRoom != null)
            rispondiUtente(Strings.of(StringId.PLAYER_CURRENT_LOCATION, stanza.toString()));

        currentRoom = stanza;
    }

    public void vai(Direction direction) throws CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .orElseThrow(() -> new CommandException(Strings.of(StringId.DESTINATION_NOT_FOUND)))
                .attraversa(getCurrentLocation());
        vaiIn(destination);
    }

    public void guarda() {
        rispondiUtente(Strings.of(StringId.PLAYER_CURRENT_LOCATION, currentRoom.toString()));

        if (currentRoom.getObjects().isEmpty()) {
            rispondiUtente(Strings.of(StringId.PLAYER_NO_OBJECTS_SEEN));
        } else {
            var objects = currentRoom.getObjects().stream()
                    .map(Entity::getName)
                    .collect(Collectors.joining(", "));
            rispondiUtente(Strings.of(StringId.PLAYER_OBJECTS_SEEN, currentRoom.toString()));
        }

        if (currentRoom.getCharacters().isEmpty()) {
            rispondiUtente(Strings.of(StringId.PLAYER_NO_PLAYERS_SEEN));
        } else {
            var coPlayers = currentRoom.getCharacters().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            rispondiUtente(Strings.of(StringId.PLAYER_PLAYERS_SEEN, currentRoom.toString()));
        }

        rispondiUtente(Strings.of(StringId.PLAYER_DIRECTIONS));
        currentRoom.getLinks().forEach((direction, link) -> rispondiUtente(direction.toString() + " " + link));
    }

    public void guarda(Entity entity) {
        rispondiUtente(Strings.of(StringId.PLAYER_ENTITY_SEEN, entity.toString()));
    }

    public void apri(ApribileCon<?> apribile) throws CommandException {
        apribile.apri();
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_OPEN, apribile.toString()));
    }

    public <T> void apri(ApribileCon<T> apribile, T oggetto) throws CommandException {
        apribile.apri(oggetto);
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_OPEN, apribile.toString()));
    }

    public void prendiDirezione(Link link) throws CommandException {
        vaiIn(link.attraversa(getCurrentLocation()));
    }

    public void prendi(Posizionabile oggetto) throws CommandException {
        oggetto.spostaIn(getInventario());
    }

    public void prendi(Posizionabile oggetto, Contenitore contenitore) throws CommandException {
        oggetto.spostaIn(getInventario());
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_TAKE, oggetto.toString(), contenitore.toString()));
    }

    public void mostraInventario() {
        if (getInventario().isEmpty()) {
            rispondiUtente(Strings.of(StringId.PLAYER_EMPTY_INVENTARY));
        } else {
            var objects = getInventario().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            rispondiUtente(Strings.of(StringId.PLAYER_INVENTARY_CONTENT, objects));
        }
    }

    public void accarezza(Accarezzabile accarezzabile) {
        accarezzabile.accarezza();
    }

    public void rompi(Rompibile rompibile, Rompitore rompitore) throws CommandException {
        rompibile.rompi(rompitore);
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_BREAK, rompibile.toString()));
    }

    public void rompi(Rompibile rompibile) throws CommandException {
        rompibile.rompi(null);
    }

    public <T> void usa(T oggetto, UsabileCon<T> soggetto) throws CommandException {
        soggetto.usaCon(oggetto);
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_USE, oggetto, soggetto));
    }

    public void usa(Usabile soggetto) throws CommandException {
        soggetto.usa();
    }

    public <T extends Entity & Posizionabile, R extends Entity & Posizionabile> void dai(T oggetto, Ricevitore<? super T, R> ricevitore) throws CommandException {
        getInventario().prendiOggetto(oggetto);
        var ricevuto = ricevitore.ricevi(oggetto);

        if (!ricevuto.isEmpty()) {
            rispondiUtente(Strings.of(StringId.PLAYER_ACTION_RECEIVE));
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
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_LISTEN_SPOKEN, parlatore, parlatore.parla()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Giocatore giocatore = (Giocatore) o;
        return Objects.equals(userOutput, giocatore.userOutput) && Objects.equals(currentRoom, giocatore.currentRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userOutput, currentRoom);
    }

    private static class UnreachableRoomException extends CommandException {
        public UnreachableRoomException() {
            this(null);
        }

        public UnreachableRoomException(String room) {
            super(Strings.of(StringId.UNREACHABLE_ROOM_ERROR) + (room == null ? "" : ": " + room));
        }
    }
}
