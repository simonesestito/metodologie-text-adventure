package it.uniroma1.textadv.entity.pojo.characters;

import it.uniroma1.textadv.Mondo;
import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.Entity;
import it.uniroma1.textadv.entity.pojo.Stanza;
import it.uniroma1.textadv.entity.pojo.features.*;
import it.uniroma1.textadv.entity.pojo.links.Direction;
import it.uniroma1.textadv.entity.pojo.links.Link;
import it.uniroma1.textadv.entity.pojo.objects.Inventario;
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
    /**
     * Istanza dell'unico giocatore disponibile e possibile
     */
    private static Giocatore INSTANCE;

    /**
     * Stream dove il giocatore risponde all'utente.
     * <p>
     * Forzarlo ad essere {@link System#out} è errato perchè non è detto
     * che la risposta utente debba essere verso quello stream.
     * <p>
     * In questo modo ne si permette la parametrizzazione
     */
    private final PrintStream userOutput;

    /**
     * Stanza in cui si trova attualmente il giocatore
     */
    private Stanza currentRoom;

    /**
     * Crea l'unico giocatore possibile all'interno del gioco
     *
     * @param name       Nome del giocatore
     * @param stanza     Stanza in cui si trova inizialmente
     * @param userOutput Stream dove rispondere all'utente
     */
    private Giocatore(String name, Stanza stanza, PrintStream userOutput) {
        super(name);
        this.userOutput = userOutput;
        try {
            vaiIn(stanza);
        } catch (CommandException e) {
            throw new IllegalArgumentException(e); // Irrecuperabile
        }
    }

    /**
     * Crea l'unico giocatore possibile all'interno del gioco,
     * assumendo le risposte utente su {@link System#out}
     *
     * @param name   Nome del giocatore
     * @param stanza Stanza in cui si trova inizialmente
     */
    private Giocatore(String name, Stanza stanza) {
        this(name, stanza, System.out);
    }

    /**
     * Inizializza l'unico giocatore possibile
     *
     * @param name  Nome del giocatore
     * @param mondo Mondo in cui si trova il giocatore
     * @return Giocatore creato, unico nel gioco
     * @throws IllegalStateException Se esiste già un giocatore
     */
    public static Giocatore init(String name, Mondo mondo) {
        return init(name, mondo.getStart());
    }

    /**
     * Inizializza l'unico giocatore possibile
     *
     * @param name   Nome del giocatore
     * @param stanza Stanza in cui si trova inizialmente
     * @return Giocatore creato, unico nel gioco
     * @throws IllegalStateException Se esiste già un giocatore
     */
    public static Giocatore init(String name, Stanza stanza) {
        // if (INSTANCE != null)
        //    throw new IllegalStateException();
        INSTANCE = new Giocatore(name, stanza);
        return getInstance();
    }

    /**
     * Ottieni l'unico giocatore disponibile
     *
     * @return Unico giocatore
     */
    public static Giocatore getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }

    /**
     * Rispondi all'utente sullo stream predisposto
     *
     * @param messaggio Messaggio da rispondere all'utente
     */
    public void rispondiUtente(String messaggio) {
        userOutput.println(messaggio);
    }

    /**
     * Stanza in cui si trova attualmente
     *
     * @return Stanza attuale
     */
    public Stanza getCurrentLocation() {
        return currentRoom;
    }

    /**
     * Ottieni gli elementi contenuti nell'inventario
     *
     * @return Elementi dell'inventario
     * @see Inventario#getOggettiContenuti()
     */
    public Set<Posizionabile> getElementiInventario() {
        return getInventario().getOggettiContenuti();
    }

    /**
     * Spostati in un'altra stanza
     *
     * @param stanza Stanza dove andare
     * @throws UnreachableRoomException In caso la destinazione sia irragiungibile
     */
    public void vaiIn(Stanza stanza) throws CommandException {
        if (stanza == null)
            throw new UnreachableRoomException();

        if (currentRoom != null)
            rispondiUtente(Strings.of(StringId.PLAYER_CURRENT_LOCATION, stanza.toString()));

        currentRoom = stanza;
    }

    /**
     * Spostati verso una direzione
     *
     * @param direction Stanza in cui andare
     * @throws UnreachableRoomException    In caso la stanza non sia stata trovata
     * @throws Link.LinkNotUsableException In caso il collegamento in quella direzione non si possa usare
     */
    public void vai(Direction direction) throws CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .orElseThrow(UnreachableRoomException::new)
                .attraversa(getCurrentLocation());
        vaiIn(destination);
    }

    /**
     * Guarda e descrivi tutto l'ambiente circostante all'utente
     */
    public void guarda() {
        rispondiUtente(Strings.of(StringId.PLAYER_CURRENT_LOCATION, currentRoom.toString()));

        if (currentRoom.getOggettiContenuti().isEmpty()) {
            rispondiUtente(Strings.of(StringId.PLAYER_NO_OBJECTS_SEEN));
        } else {
            var objects = currentRoom.getOggettiContenuti().stream()
                    .map(Entity::getName)
                    .collect(Collectors.joining(", "));
            rispondiUtente(Strings.of(StringId.PLAYER_OBJECTS_SEEN, objects));
        }

        if (currentRoom.getCharacters().isEmpty()) {
            rispondiUtente(Strings.of(StringId.PLAYER_NO_PLAYERS_SEEN));
        } else {
            var coPlayers = currentRoom.getCharacters().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            rispondiUtente(Strings.of(StringId.PLAYER_PLAYERS_SEEN, coPlayers));
        }

        rispondiUtente(Strings.of(StringId.PLAYER_DIRECTIONS));
        currentRoom.getLinks().forEach((direction, link) -> rispondiUtente(
                direction.toString() + " " + link.toStringFrom(getCurrentLocation())
        ));
    }

    /**
     * Descrivi all'utente una specifica entità
     *
     * @param entity Entità da guardare
     */
    public void guarda(Entity entity) {
        rispondiUtente(Strings.of(StringId.PLAYER_ENTITY_SEEN, entity.toString()));
    }

    /**
     * Apri un oggetto che sia apribile senza alcun oggetto
     *
     * @param apribile Oggetto da aprire
     * @throws CommandException Errore nell'apertura
     */
    public void apri(ApribileCon<?> apribile) throws CommandException {
        apribile.apri();
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_OPEN, apribile.toString()));
    }

    /**
     * Apri un oggetto che sia apribile con un altro oggetto dato
     *
     * @param apribile Oggetto da aprire
     * @param oggetto  Oggetto usato per aprire
     * @param <T>      Tipo dell'oggetto che apre
     * @throws ApribileCon.AperturaException Errore nell'apertura
     */
    public <T> void apri(ApribileCon<T> apribile, T oggetto) throws ApribileCon.AperturaException {
        apribile.apri(oggetto);
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_OPEN, apribile.toString()));
    }

    /**
     * Vai verso un collegamento
     *
     * @param link Collegamento da attraversare
     * @throws CommandException Errore nell'attraversamento
     */
    public void prendiDirezione(Link link) throws CommandException {
        vaiIn(link.attraversa(getCurrentLocation()));
    }

    /**
     * Prendi un oggetto e aggiungilo all'inventario
     *
     * @param oggetto Oggetto da prendere
     * @throws CommandException Errore nella presa
     */
    public void prendi(Posizionabile oggetto) throws CommandException {
        oggetto.spostaIn(getInventario());
    }

    /**
     * Prendi un oggetto e aggiungilo all'inventario, da un contenitore fornito
     *
     * @param oggetto     Oggetto da prendere
     * @param contenitore Contenitore dove dovrebbe essere l'oggetto attualmente
     * @throws Contenitore.UnresolvedEntityException Se non è dove indicato
     * @throws CommandException                      Errore nella presa
     */
    public void prendi(Posizionabile oggetto, Contenitore contenitore) throws CommandException {
        if (!Objects.equals(oggetto.getPosizione(), contenitore))
            throw new Contenitore.UnresolvedEntityException(oggetto, contenitore);

        oggetto.spostaIn(getInventario());
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_TAKE, oggetto.toString(), contenitore.toString()));
    }

    /**
     * Mostra tutti gli oggetti contenuti nell'inventario
     */
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

    /**
     * Accarezza un'entità che può essere accarezzata
     *
     * @param accarezzabile Entità da accarezzare
     */
    public void accarezza(Accarezzabile accarezzabile) {
        accarezzabile.accarezza();
    }

    /**
     * Rompi un'entità che può essere rotta con un altro oggetto
     *
     * @param rompibile Entità da rompere
     * @param rompitore Entità che verrà usata per rompere
     * @throws CommandException Errore nella rottura
     */
    public void rompi(Rompibile rompibile, Rompitore rompitore) throws CommandException {
        rompibile.rompi(rompitore);
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_BREAK, rompibile.toString()));
    }

    /**
     * Rompi un'entità che può essere rotta senza oggetti
     *
     * @param rompibile Entità da rompere
     * @throws CommandException Errore nella rottura
     */
    public void rompi(Rompibile rompibile) throws CommandException {
        rompibile.rompi(null);
    }

    /**
     * Usa un'entità che può essere usata con altri oggetti
     *
     * @param oggetto  Entità da usare
     * @param soggetto Oggetto con cui viene usato
     * @param <T>      Tipo dell'oggetto
     * @throws CommandException Errore nell'utilizzo
     */
    public <T> void usa(T oggetto, UsabileCon<T> soggetto) throws CommandException {
        soggetto.usaCon(oggetto);
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_USE, oggetto, soggetto));
    }

    /**
     * Usa un'entità che può essere usata senza altri oggetti
     *
     * @param soggetto Oggetto da usare
     * @throws CommandException Errore nell'utilizzo
     */
    public void usa(Usabile soggetto) throws CommandException {
        soggetto.usa();
    }

    /**
     * Dai un oggetto a un'altra entità, in cambio di altri oggetti che verranno messi nell'inventario
     *
     * @param oggetto    Oggetto da dare
     * @param ricevitore Entità che lo riceve
     * @param <T>        Tipo dell'oggetto da dare
     * @param <R>        Tipo degli oggetti da ricevere
     * @throws CommandException Errore nello scambio
     */
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

    /**
     * Parla con un'altra entità con cui è possibile parlare
     *
     * @param parlatore Entità con cui parlare
     */
    public void parla(Parla parlatore) {
        rispondiUtente(Strings.of(StringId.PLAYER_ACTION_LISTEN_SPOKEN, parlatore, parlatore.parla()));
    }

    /**
     * Gioca con un oggetto con cui è possibile giocare
     *
     * @param gioco Gioco da avviare e far interagire con l'utente
     */
    public void gioca(Gioco gioco) {
        gioco.avviaGioco();
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due entità uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due entità uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Giocatore giocatore = (Giocatore) o;
        return Objects.equals(userOutput, giocatore.userOutput) && Objects.equals(currentRoom, giocatore.currentRoom);
    }

    /**
     * Calcola l'hash del giocatore
     *
     * @return Hash del giocatore
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userOutput, currentRoom);
    }

    /**
     * Eccezione in caso una stanza non sia raggiungibile da dove mi trovo
     */
    private static class UnreachableRoomException extends CommandException {
        /**
         * Errore generico senza indicazione della stanza
         */
        public UnreachableRoomException() {
            super(Strings.of(StringId.UNKNOWN_ROOM_ERROR));
        }

        /**
         * Errore specificando la stanza interessata
         *
         * @param room Stanza non raggiungibile
         */
        public UnreachableRoomException(String room) {
            super(Strings.of(StringId.UNREACHABLE_ROOM_ERROR) + (room == null ? "" : room));
        }
    }
}
