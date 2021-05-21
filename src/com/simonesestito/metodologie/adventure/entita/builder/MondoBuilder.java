package com.simonesestito.metodologie.adventure.entita.builder;

import com.simonesestito.metodologie.adventure.entita.Mondo;
import com.simonesestito.metodologie.adventure.entita.Stanza;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Crea un mondo, ottenendo i parametri passo passo,
 * semplificandone la creazione complessiva
 */
public class MondoBuilder {
    private String name;
    private String description;
    private Supplier<Stanza> startRoomSupplier;

    public MondoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MondoBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MondoBuilder setStartRoom(Stanza startRoomSupplier) {
        this.startRoomSupplier = () -> startRoomSupplier;
        return this;
    }

    public MondoBuilder setStartRoom(String roomName, Function<String, Stanza> roomResolver) {
        this.startRoomSupplier = () -> roomResolver.apply(roomName);
        return this;
    }

    public Mondo build() {
        Objects.requireNonNull(name, "Nome del mondo non fornito");
        Objects.requireNonNull(description, "Descrizione del mondo non fornita");
        Objects.requireNonNull(startRoomSupplier, "Non è stata impostata una stanza iniziale");

        // Risolvi la stanza d'inizio
        Stanza startRoom = startRoomSupplier.get();
        Objects.requireNonNull(startRoom, "La stanza fornita è nulla");

        return new Mondo(name, description, startRoom);
    }
}
