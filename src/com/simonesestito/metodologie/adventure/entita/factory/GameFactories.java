package com.simonesestito.metodologie.adventure.entita.factory;

public class GameFactories {
    public static SectionFactory<?> forSection(ParsingBuildContext.Section section) {
        return forTag(section.getTag());
    }

    public static SectionFactory<?> forTag(String tag) {
        return switch (tag) {
            case "world" -> new MondoFactory();
            default -> throw new RuntimeException(); // TODO
        };
    }
}
