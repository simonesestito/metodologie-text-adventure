package com.simonesestito.metodologie.adventure.entita.builder;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Insieme di oggetti che possono essere risolti in maniera lazy.
 * Alcuni oggetti, se già disponibili, possono essere inseriti direttamente.
 *
 * Usato nei builder quando un dato è risolto successivamente a build()
 *
 * @param <T> Tipo del singolo oggetto
 */
public class LazyList<T> {
    /**
     * Istanze di oggetti già risolti e pronti per essere usati
     */
    private final Map<String, T> resolvedObjects = new HashMap<>();

    /**
     * Oggetti di cui conosciamo l'identità ma che verranno risolti in futuro lazily
     */
    private final Map<String, Supplier<T>> resolvableObjects = new HashMap<>();

    public void add(List<? extends T> elements) {
        elements.forEach(this::add);
    }

    public void add(List<String> ids, Resolver<T> resolver) {
        ids.forEach(id -> add(id, resolver));
    }

    public void add(T element) {
        resolvedObjects.add(element);
    }

    public void add(String id, Resolver<T> resolver) {
        resolvableObjects.add(() -> resolver.apply(id));
    }

    public Map<String, T> resolveAsMap() {
        var lazyObjects = resolvableObjects.stream()
                .map(id -> Objects.requireNonNull())
                .toList();

        resolvedObjects.addAll(lazyObjects);
        resolvableObjects.clear();

        return resolvedObjects;
    }

    public List<T> resolveAsList() {

    }

    @FunctionalInterface
    public interface Resolver<T> extends Function<String, T> {}
}
