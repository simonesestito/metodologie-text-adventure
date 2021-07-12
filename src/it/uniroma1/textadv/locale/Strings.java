package it.uniroma1.textadv.locale;

import it.uniroma1.textadv.Gioco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gestore della localizzazione del gioco.
 * <p>
 * Le stringhe sono universalmente identificate da un ID, poi nel file della lingua c'è la stringa.
 * <p>
 * Le stringhe accettano parametri stringa, in formato printf
 * (ovvero al posto del parametro avranno %s se è una stringa, etc).
 * <p>
 * Esiste un'istanza di Strings per volta.
 * <p>
 * Quando si carica una nuova lingua, il caricamento vecchio viene rilasciato e ne si crea un altro.
 * Ciò rende il resto del programma ignaro di quale sia la lingua attuale,
 * di variazioni della stessa, od ogni altro dettaglio inutile.
 * <p>
 * Per ottenere una stringa, si usa {@link Strings#of(StringId, Object...)} in maniera chiara, immediata e compatta.
 */
public class Strings {
    /**
     * Nome del file (non path) delle stringhe
     */
    public static final String STRINGS_FILENAME = "strings.csv";
    /**
     * Istanza da usare delle stringhe per la lingua attuale
     */
    private static Strings instance;
    /**
     * Mappa da ID a stringa printf corrispondente,
     * per un accesso in base all'ID in tempo costante
     */
    private final Map<StringId, String> stringMap;
    /**
     * Lingua dell'attuale caricamento delle stringhe
     */
    private final Gioco.Language language;

    /**
     * Crea una nuova traduzione
     *
     * @param stringMap Mappa delle stringhe
     * @param language  Lingua del caricamento
     */
    private Strings(Map<StringId, String> stringMap, Gioco.Language language) {
        this.stringMap = stringMap;
        this.language = language;
    }

    /**
     * Ottieni l'istanza delle stringhe per la lingua corrente
     *
     * @return Stringhe per la lingua corrente
     */
    public static Strings getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }

    /**
     * Localizza tutto il programma, ricreando una nuova istanza delle stringhe,
     * e modificando l'istanza condivisa da tutti.
     * <p>
     * Così facendo, il cambiamento è silenzioso in tutto il programma.
     *
     * @param language Nuova lingua
     * @throws IOException Errore nel caricamento del file della lingua
     */
    public static void localizza(Gioco.Language language) throws IOException {
        if (instance == null || !language.equals(instance.getLingua())) {
            var file = Paths.get(language.getFilePrefix() + STRINGS_FILENAME);
            var stringsMap = Files.lines(file)
                    .collect(Collectors.toMap(
                            s -> StringId.valueOf(s.substring(0, s.indexOf(',')).toUpperCase()),
                            s -> s.substring(s.indexOf(',') + 1)
                    ));
            instance = new Strings(stringsMap, language);
        }
    }

    /**
     * Modo raccomandato per ottenere una traduzione nella maniera migliore e più compatta.
     *
     * @param id   ID della stringa richiesta
     * @param args Eventuali parametri
     * @return Stringa nella lingua attuale, parametrizzata
     */
    public static String of(StringId id, Object... args) {
        return Strings.getInstance().getForId(id, args);
    }

    /**
     * Ottieni la lingua dell'attuale caricamento
     *
     * @return Lingua attuale
     */
    public Gioco.Language getLingua() {
        return language;
    }

    /**
     * Ottieni la stringa per l'ID dato
     *
     * @param id   ID della stringa richiesta
     * @param args Eventuali parametri
     * @return Stringa nella lingua attuale, parametrizzata
     */
    private String getForId(StringId id, Object... args) {
        return String.format(stringMap.get(id), args);
    }
}
