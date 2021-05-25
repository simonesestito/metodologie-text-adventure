package com.simonesestito.metodologie.adventure.entita.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Rappresentazione del file di gioco, dopo essere stato parsato dal file originale.
 * <p>
 * Avere un oggetto di questo tipo, garantisce che il file sia sintatticamente corretto,
 * ma nulla è garantito sulla sua semantica, la quale è verificata dagli
 * {@link com.simonesestito.metodologie.adventure.entita.factory.EntityProcessor}.
 */
public class GameFile implements Iterable<GameFile.Section> {
    /**
     * Prefisso segnalante l'inizio della prima riga di ogni sezione
     */
    public static final String TAG_LINE_PREFIX = "[";

    /**
     * Elenco delle sezioni individuate nel file
     */
    private final List<Section> sections;

    /**
     * Crea un file di gioco partendo dall'elenco delle sezioni in esso contenute.
     *
     * @param sections Sezioni del file di gioco
     */
    private GameFile(List<Section> sections) {
        this.sections = sections;
    }

    /**
     * Elabora un file in input per generare un {@link GameFile} con il suo contenuto
     *
     * @param file File da leggere
     * @return Sua rappresentazione come {@link GameFile}
     * @throws IOException Errore nella lettura del file
     */
    public static GameFile parseFile(Path file) throws IOException {
        try {
            try (var reader = new ConditionalBufferedReader(file)) {
                var sections = Stream.generate(
                        () -> reader.readNextLineAndWhile(s -> !s.startsWith(TAG_LINE_PREFIX))
                )
                        .map(Stream::toList)
                        .takeWhile(l -> !l.isEmpty())
                        .map(GameFile.Section::fromSectionLines)
                        .toList();
                return new GameFile(sections);
            }
        } catch (IOException e) {
            // Fai gestire all'esterno una eccezione specifica di tipo I/O
            throw e;
        } catch (Exception e) {
            // Non propagare un'eccezione generica in quanto non prevista
            throw new RuntimeException(e);
        }
    }

    /**
     * Ottieni l'elenco di tutte le sezioni nel file
     *
     * @return Elenco delle sezioni
     */
    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    /**
     * Ottieni la prima sezione in base al nome del tag
     *
     * @param tag Nome del tag ricercato
     * @return Ottieni la prima sezione, se presente
     */
    public Optional<Section> getSectionByTag(String tag) {
        var sections = getSectionsByTag(tag);
        return sections.isEmpty() ? Optional.empty() : Optional.of(sections.get(0));
    }

    /**
     * Ottieni tutte le sezioni con il tag con il nome dato
     *
     * @param tag Nome del tag ricercato
     * @return Tutte le sezioni col tag ricercato
     */
    public List<Section> getSectionsByTag(String tag) {
        return sections.stream()
                .filter(s -> s.getTag().getName().equals(tag))
                .toList();
    }

    /**
     * Rappresentazione di una singola sezione all'interno del file
     */
    public static class Section implements Iterable<Line> {
        /**
         * Tag header della sezione
         */
        private final Tag tag;

        /**
         * Linee interne alla sezione, indicizzate per la chiave
         *
         * @see Line#getKey()
         */
        private final Map<String, Line> lines;

        /**
         * Crea una nuova sezione dati il tag e le linee del contenuto
         *
         * @param tag   Tag header
         * @param lines Linee contenute
         */
        private Section(Tag tag, Map<String, Line> lines) {
            this.tag = tag;
            this.lines = lines;
        }

        /**
         * Crea una sezione avendo l'elenco delle stringhe
         * di ogni linea della sezione.
         *
         * @param stringLines Linee non ancora elaborate, ma valori raw dal file.
         * @return Sezione ottenuta da quelle linee
         */
        public static Section fromSectionLines(List<String> stringLines) {
            var tag = Tag.fromTagLine(stringLines.get(0));
            var lines = stringLines.stream()
                    .skip(1)
                    .map(Line::new)
                    .collect(Collectors.toMap(
                            Line::getKey,
                            Function.identity()
                    ));
            return new Section(tag, lines);
        }

        /**
         * Ottieni il tag della sezione.
         * <p>
         * Si identifica come la prima linea della sezione.
         *
         * @return Tag della sezione
         */
        public Tag getTag() {
            return tag;
        }

        /**
         * Linee della sezione,
         * esclusa la prima linea, elaborata come {@link Section#getTag()}
         *
         * @return Linee del contenuto della sezione
         */
        public Collection<Line> getLines() {
            return lines.values();
        }

        /**
         * Ottieni una linea in base alla chiave (chiave definita in {@link Line#getKey()}).
         *
         * @param key Chiave della linea cercata
         * @return Linea trovata, se c'è
         */
        public Optional<Line> getLine(String key) {
            return Optional.ofNullable(lines.get(key));
        }

        /**
         * Itera sulle linee di una sezione, nell'ordine in cui compaiono.
         *
         * @return Iteratore sulle linee della sezione
         * @see Section#getLines()
         */
        @Override
        public Iterator<Line> iterator() {
            return getLines().iterator();
        }
    }

    /**
     * Rappresentazione del tag di una sezione
     */
    public static class Tag {
        /**
         * Carattere che divide il nome del tag dal suo argomento,
         * se presente nel tag.
         */
        public static final char ARGUMENT_SEPARATOR = ':';

        /**
         * Nome del tag
         */
        private final String name;

        /**
         * Argomento del tag, se presente
         */
        private final String argument;

        /**
         * Crea un nuovo tag con i valori delle due stringhe
         *
         * @param name     Nome del tag
         * @param argument Argomento del tag, o <code>null</code>
         */
        private Tag(String name, String argument) {
            this.name = name;
            this.argument = argument;
        }

        /**
         * Crea un nuovo tag con la stringa del nome del tag.
         * Non è presente un argomento, che sarà <code>null</code>.
         *
         * @param name Nome del tag
         */
        private Tag(String name) {
            this(name, null);
        }

        /**
         * Crea un nuovo tag a partire dalla stringa che lo rappresenta.
         *
         * @param line Stringa raw del tag
         * @return Tag creato
         */
        public static Tag fromTagLine(String line) {
            int argumentSeparatorIndex = line.indexOf(ARGUMENT_SEPARATOR);
            if (argumentSeparatorIndex < 0) {
                // Trovato solo il nome del tag (links, characters, ...)
                return new Tag(line.substring(1, line.length() - 1));
            } else {
                return new Tag(
                        line.substring(1, argumentSeparatorIndex),
                        line.substring(argumentSeparatorIndex + 1, line.length() - 1)
                );
            }
        }

        /**
         * Ottieni il nome del tag.
         * <p>
         * Sempre presente, è individuato come la stringa tra le quadre nel tag stringa,
         * senza la parte dopo i :, che si definisce come argomento, che può non esserci.
         *
         * @return Nome del tag
         */
        public String getName() {
            return name;
        }

        /**
         * Ottieni il possibile argomento del tag.
         * <p>
         * Un tag può non avere alcun argomento, pertanto è opzionale.
         * <p>
         * In caso sia presente, è la parte all'interno delle parentesi quadre,
         * ma posta successivamente ai due punti.
         *
         * @return Argomento del tag, se presente
         */
        public Optional<String> getArgument() {
            return Optional.ofNullable(argument);
        }
    }

    /**
     * Rappresentazione della linea del contenuto della sezione.
     * <p>
     * Non rappresenta la prima linea, che è individuata come {@link Tag}
     */
    public static class Line {
        /**
         * Separatore standard degli argomenti nella linea
         */
        public static final String ARGUMENTS_SEPARATOR = "\t";

        /**
         * Funzione di conversione degli argomenti della linea,
         * interpretati come comma-separated values.
         *
         * @see Line#getCommaSeparatedArguments()
         */
        public static final Function<String, Stream<String>> COMMA_SEPARATED_ARG_MAPPER = s -> Arrays.stream(s.split(","));

        /**
         * Funzione di conversione degli argomenti della linea,
         * interpretati come separati dal carattere di default.
         *
         * @see Line#ARGUMENTS_SEPARATOR
         * @see Line#getSeparatedArguments()
         */
        public static final Function<String, Stream<String>> TAB_SEPARATED_ARG_MAPPER = s -> Arrays.stream(s.split(ARGUMENTS_SEPARATOR));

        /**
         * Testo raw della linea
         */
        private final String text;

        /**
         * Crea una nuova linea a partire dal testo raw.
         * <p>
         * Verrà interpretato quando richiesto dai metodi.
         *
         * @param text Testo raw della linea
         */
        public Line(String text) {
            this.text = text;
        }

        /**
         * Ottieni il testo originale di tutta la linea completa
         *
         * @return Testo originale completo della linea
         */
        public String getText() {
            return text;
        }

        /**
         * Ottieni la chiave della linea.
         * <p>
         * Definita come la prima parte nella linea,
         * se considerata divisa in base al carattere di divisione standard.
         *
         * @return Prima parte della linea
         * @see Line#ARGUMENTS_SEPARATOR
         */
        public String getKey() {
            return text.substring(0, text.indexOf(ARGUMENTS_SEPARATOR));
        }

        /**
         * Ottieni la parte degli argomenti della linea,
         * senza trattarla in nessun modo.
         * <p>
         * Non contiene la parte della chiave.
         *
         * @return Stringa degli argomenti della linea
         * @see Line#getText
         */
        public String getArgumentsString() {
            return text.substring(text.indexOf(ARGUMENTS_SEPARATOR) + 1);
        }

        /**
         * Ottieni uno stream degli argomenti della linea,
         * intesi come separati dal carattere di divisione standard.
         *
         * @return Stream degli argomenti separati standard
         * @see Line#ARGUMENTS_SEPARATOR
         * @see Line#TAB_SEPARATED_ARG_MAPPER
         */
        public Stream<String> getSeparatedArguments() {
            return getArgument(TAB_SEPARATED_ARG_MAPPER);
        }

        /**
         * Ottieni uno stream degli argomenti della linea,
         * intesi come comma-separated values.
         *
         * @return Stream degli argomenti separati da virgola
         * @see Line#ARGUMENTS_SEPARATOR
         * @see Line#COMMA_SEPARATED_ARG_MAPPER
         */
        public Stream<String> getCommaSeparatedArguments() {
            return getArgument(COMMA_SEPARATED_ARG_MAPPER);
        }

        /**
         * Ottieni gli argomenti usando una funzione di mapping custom.
         *
         * @param mapper Funzione di trasformazione degli argomenti,
         *               forniti come stringa raw.
         * @param <R>    Tipo finale degli argomenti
         * @return Argomenti della linea, trasformati in base alla funzione
         * @see Line#getArgumentsString()
         */
        public <R> R getArgument(Function<String, R> mapper) {
            return mapper.apply(getArgumentsString());
        }

        /**
         * Ottieni il testo della linea.
         *
         * @return Testo della linea
         * @see Line#getText()
         */
        @Override
        public String toString() {
            return getText();
        }
    }

    /**
     * Errore generico in fase di elaborazione del file,
     * non solo a livello sintattico.
     * <p>
     * Può riguardare un errore successivo, anche semantico.
     * Rappresenta un generico errore sul file di gioco.
     *
     * @see com.simonesestito.metodologie.adventure.entita.factory.EntityProcessor.BuildContext.DependencyException
     */
    public static class ParseException extends Exception {
        /**
         * Crea un errore generico sul file di gioco, indicando una descrizione
         *
         * @param message Descrizione dell'errore
         */
        public ParseException(String message) {
            super(message);
        }

        /**
         * Crea un errore generico sul file di gioco, indicando una descrizione e la causa
         *
         * @param message Descrizione dell'errore
         * @param cause   Causa dell'errore
         */
        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Itera un file di gioco sulle sue sezioni,
     * in ordine di come appaiono nel file originale.
     *
     * @return Iteratore sulle sezioni
     */
    @Override
    public Iterator<Section> iterator() {
        return sections.iterator();
    }
}
