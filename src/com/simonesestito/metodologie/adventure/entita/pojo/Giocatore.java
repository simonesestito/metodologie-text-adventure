package com.simonesestito.metodologie.adventure.entita.pojo;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.characters.Personaggio;
import com.simonesestito.metodologie.adventure.entita.pojo.links.Direction;

import java.util.Objects;

public class Giocatore extends Personaggio {
    private static Giocatore instance;
    private Stanza currentRoom;

    private Giocatore(String name, Stanza stanza) {
        super(name);
        moveTo(stanza);
    }

    public static Giocatore init(String nome, Mondo mondo) {
        return init(nome, mondo.getStart());
    }

    public static Giocatore init(String nome, Stanza stanza) {
        instance = new Giocatore(nome, stanza);
        return getInstance();
    }

    public static Giocatore getInstance() {
        return Objects.requireNonNull(instance);
    }

    public Stanza getCurrentLocation() {
        return currentRoom;
    }

    public void moveTo(Stanza stanza) {
        if (currentRoom != null) {
            currentRoom.removeCharacter(this);
        }

        currentRoom = stanza;
        currentRoom.addCharacter(this);
    }

    public void moveTo(Direction direction) throws TextEngine.CommandException {
        var destination = getCurrentLocation().getLink(direction)
                .map(l -> l.getDestinazione(getCurrentLocation()))
                .orElseThrow(() -> new TextEngine.CommandException("Destinazione non trovata"));
        moveTo(destination);
    }
}
