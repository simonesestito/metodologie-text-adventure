package com.simonesestito.metodologie.adventure.entita.pojo;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORD("N"),
    SUD("S"),
    OVEST("O", "W"),
    EST("E");

    private final List<String> abbreviations;

    Direction(String... abbreviations) {
        this.abbreviations = List.of(abbreviations);
    }

    public static Direction of(String abbreviation) {
        return Arrays.stream(Direction.values())
                .filter(d -> d.abbreviations.contains(abbreviation))
                .findAny()
                .orElse(null);
    }
}
