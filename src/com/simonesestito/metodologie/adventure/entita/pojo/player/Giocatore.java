package com.simonesestito.metodologie.adventure.entita.pojo.player;

import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;
import com.simonesestito.metodologie.adventure.entita.pojo.Stanza;

import java.util.Objects;

/**
 * Giocatore che si interfaccia con l'utente via CLI
 */
public class Giocatore extends AbstractGiocatore {
    private static Giocatore INSTANCE;

    private Giocatore(String name, Stanza stanza) {
        super(name, stanza);
    }

    public static Giocatore init(String nome, Mondo mondo) {
        return init(nome, mondo.getStart());
    }

    public static Giocatore init(String nome, Stanza stanza) {
        INSTANCE = new Giocatore(nome, stanza);
        return getInstance();
    }

    public static Giocatore getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }

    @Override
    protected void rispondiUtente(String messaggio) {
        System.out.println(messaggio);
    }
}
