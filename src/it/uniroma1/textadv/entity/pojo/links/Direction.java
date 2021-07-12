package it.uniroma1.textadv.entity.pojo.links;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * Direzione in cui andare nel gioco
 * <p>
 * Si usano i punti cardinali
 */
public enum Direction {
    /**
     * Direzione nord
     */
    NORD("↑", "N"),

    /**
     * Direzione sud
     */
    SUD("↓", "S"),

    /**
     * Direzione ovest
     */
    OVEST("←", "O", "W"),

    /**
     * Direzione est
     */
    EST("→", "E");

    /**
     * Simbolo da stampare a video per la direzione
     */
    private final String symbol;

    /**
     * Elenco delle abbreviazioni per questa direzione
     */
    private final Set<String> abbreviations;

    /**
     * Crea la direzione nell'enum
     *
     * @param symbol        Simbolo per l'utente
     * @param abbreviations Abbreviazioni equivalenti
     */
    Direction(String symbol, String... abbreviations) {
        this.symbol = symbol;
        this.abbreviations = Set.of(abbreviations);
    }

    /**
     * Ottieni una direzione dall'abbreviazione o dal nome completo
     *
     * @param abbreviation Abbreviazione o nome da risolvere
     * @return Direzione trovata o {@link Optional#empty()}
     */
    public static Optional<Direction> of(String abbreviation) {
        return Arrays.stream(Direction.values())
                .filter(d -> d.abbreviations.contains(abbreviation.toUpperCase()))
                .findAny()
                .or(() -> {
                    try {
                        return Optional.of(Direction.valueOf(abbreviation.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        return Optional.empty();
                    }
                });
    }

    /**
     * Rappresentazione della direzione come stringa
     *
     * @return Simbolo della direzione
     */
    @Override
    public String toString() {
        return symbol;
    }
}
