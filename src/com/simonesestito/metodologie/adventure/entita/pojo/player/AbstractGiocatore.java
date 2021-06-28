package com.simonesestito.metodologie.adventure.entita.pojo.player;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Personaggio;
import com.simonesestito.metodologie.adventure.entita.pojo.features.*;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Chiave;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGiocatore extends Personaggio {
    private Stanza currentRoom;
    private final List<Oggetto> inventario;

    protected AbstractGiocatore(String name, Stanza stanza) {
        super(name);
        inventario = new ArrayList<>();
        vaiIn(stanza);
    }

    // Template method design pattern
    protected abstract void rispondiUtente(String messaggio);

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
    }

    public void guarda(Entity entity) {
        rispondiUtente("Sto vedendo " + entity);
    }

    public void apri(Apribile apribile) throws CommandException {
        apribile.apri();
        rispondiUtente("Ho aperto " + apribile);
    }

    public void apri(ApribileConChiave apribile, Chiave chiave) throws CommandException {
        apribile.apri(chiave);
        rispondiUtente("Ho aperto " + apribile);
    }

    public void prendi(Oggetto oggetto) throws CommandException {
        prendi(oggetto, currentRoom);
    }

    public void prendi(Oggetto oggetto, Contenitore contenitore) throws CommandException {
        if (contenitore.getOggettiContenuti().contains(oggetto)) {
            contenitore.prendiOggetto(oggetto);
            inventario.add(oggetto);
            rispondiUtente("Ho preso " + oggetto);
        } else {
            rispondiUtente("Non trovo " + oggetto.getName() + " in " + contenitore);
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
        rompibile.rompi(rompitore);
        rispondiUtente("Fatto!");
        rispondiUtente(rompibile.toString());
    }

    public void rompi(Rompibile rompibile) throws CommandException {
        rompibile.rompi(null);
    }

    public void usa(Rompitore rompitore, Rompibile rompibile) throws CommandException {
        rompi(rompibile, rompitore);
    }
}
