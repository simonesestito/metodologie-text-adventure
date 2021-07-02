package it.uniroma1.textadv.locale;

import it.uniroma1.textadv.Gioco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Strings {
    public static final String STRINGS_FILENAME = "strings.csv";
    private final Map<StringId, String> stringMap;
    private final Gioco.Lingua lingua;
    private static Strings instance;

    public static Strings getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }

    private Strings(Map<StringId, String> stringMap, Gioco.Lingua lingua) {
        this.stringMap = stringMap;
        this.lingua = lingua;
    }

    public static void localizza(Gioco.Lingua lingua) throws IOException {
        if (instance == null || !lingua.equals(instance.getLingua())) {
            var file = Paths.get(lingua.getFilePrefix() + STRINGS_FILENAME);
            var stringsMap = Files.lines(file)
                    .collect(Collectors.toMap(
                            s -> StringId.valueOf(s.substring(0, s.indexOf(',')).toUpperCase()),
                            s -> s.substring(s.indexOf(',') + 1)
                    ));
            instance = new Strings(stringsMap, lingua);
        }
    }

    public Gioco.Lingua getLingua() {
        return lingua;
    }

    public static String of(StringId id, Object... args) {
        return Strings.getInstance().getForId(id, args);
    }

    public String getForId(StringId id, Object... args) {
        return String.format(stringMap.get(id), args);
    }
}
