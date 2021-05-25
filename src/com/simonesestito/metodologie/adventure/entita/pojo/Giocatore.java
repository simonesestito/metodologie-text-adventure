package com.simonesestito.metodologie.adventure.entita.pojo;

import java.util.Objects;

public class Giocatore extends Personaggio {
    private static Giocatore instance;
    private Stanza stanza;

    private Giocatore(String name, Stanza stanza) {
        super(name);
        this.stanza = stanza;
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

    public Stanza getStanza() {
        return stanza;
    }
}