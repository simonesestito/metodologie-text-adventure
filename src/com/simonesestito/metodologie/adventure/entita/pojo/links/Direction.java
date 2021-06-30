package com.simonesestito.metodologie.adventure.entita.pojo.links;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public enum Direction {
    NORD("N"),
    SUD("S"),
    OVEST("O", "W"),
    EST("E");

    private final Set<String> abbreviations;

    Direction(String... abbreviations) {
        this.abbreviations = Set.of(abbreviations);
    }

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
}
