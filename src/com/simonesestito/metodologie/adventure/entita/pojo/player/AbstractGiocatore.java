package com.simonesestito.metodologie.adventure.entita.pojo.player;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Personaggio;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Apribile;
import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;
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

    public void vai(Direction direction) throws TextEngine.CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .map(l -> l.getDestinazione(getCurrentLocation()))
                .orElseThrow(() -> new TextEngine.CommandException("Destinazione non trovata"));
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
                    .map(Object::toString)
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

    public void apri(Apribile apribile) throws TextEngine.CommandException {
        apribile.apri();
        rispondiUtente("Ho aperto " + apribile);
    }

    public void prendi(Oggetto oggetto) throws TextEngine.CommandException {
        prendi(oggetto, currentRoom);
    }

    public void prendi(Oggetto oggetto, Contenitore contenitore) throws TextEngine.CommandException {
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
}
