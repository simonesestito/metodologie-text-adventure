package com.simonesestito.metodologie.adventure.entita.builder;

import com.simonesestito.metodologie.adventure.entita.Oggetto;
import com.simonesestito.metodologie.adventure.entita.Personaggio;
import com.simonesestito.metodologie.adventure.entita.Stanza;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class StanzaBuilder {
    private String name;
    private String description;
    private LazyList<Oggetto> objectSuppliers = new LazyList<>();
    private LazyList<Personaggio> characterSuppliers = new LazyList<>();

    public StanzaBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StanzaBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public StanzaBuilder addObjects(List<Oggetto> objects) {
        objectSuppliers.add(objects);
        return this;
    }

    public StanzaBuilder addObjects(List<String> objectNames, LazyList.Resolver<Oggetto> objectResolver) {
        objectSuppliers.add(objectNames, objectResolver);
        return this;
    }

    public StanzaBuilder addObject(Oggetto object) {
        objectSuppliers.add(object);
        return this;
    }

    public StanzaBuilder addObject(String objectName, LazyList.Resolver<Oggetto> objectResolver) {
        objectSuppliers.add(objectName, objectResolver);
        return this;
    }

    public Stanza build() {
        Objects.requireNonNull(name, "Nome stanza mancante");
        Objects.requireNonNull(description, "Descrizione stanza mancante");
        List<Oggetto> objects = objectSuppliers.resolve();
        objects.forEach(Objects::requireNonNull, "Oggetto non risolto correttamente");
    }
}
