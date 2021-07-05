package it.uniroma1.textadv.engine;

import it.uniroma1.textadv.Gioco;
import it.uniroma1.textadv.entity.pojo.Entity;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;
import it.uniroma1.textadv.utils.MultiMap;
import it.uniroma1.textadv.utils.StreamUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Elabora l'input utente e capisce i comandi dati
 */
public class TextEngine {
    /**
     * Nome del file dei comandi di default.
     * <p>
     * Non il path completo ma solo il nome del file.
     */
    public static final String COMMANDS_FILENAME = "commands.csv";

    /**
     * Nome del file delle stopword di default.
     * <p>
     * Non il path completo ma solo il nome del file.
     */
    public static final String STOPWORDS_FILENAME = "stopwords.txt";

    /**
     * Path del file dei comandi di default, in base alla lingua di default
     *
     * @see Gioco#DEFAULT_LANGUAGE
     */
    public static final String DEFAULT_COMMANDS_FILE = Gioco.DEFAULT_LANGUAGE.getFilePrefix() + COMMANDS_FILENAME;

    /**
     * Path del file delle stop word di default, in base alla lingua di default
     *
     * @see Gioco#DEFAULT_LANGUAGE
     */
    public static final String DEFAULT_STOPWORDS_FILE = Gioco.DEFAULT_LANGUAGE.getFilePrefix() + STOPWORDS_FILENAME;

    /**
     * Oggetto in grado di risolvere un'entità o altro elemento (es: direzione)
     * dal comando testuale dato.
     */
    private final EntityResolver entityResolver = new EntityResolver();

    /**
     * Insieme delle stopwords da considerare, lette dal file
     */
    private final Set<String> stopWords;

    /**
     * Elenco dei comandi disponibili
     */
    private final MultiMap<String, Command> commands;

    /**
     * Crea un nuovo text engine
     *
     * @param stopWords Elenco delle stopwords
     * @param commands  Comandi da considerare
     */
    private TextEngine(Set<String> stopWords, MultiMap<String, Command> commands) {
        this.stopWords = stopWords;
        this.commands = commands;
    }

    /**
     * Ripulisci l'input di informazioni non necessarie, come le <b>stop-word</b>.
     *
     * @param input Testo "sporco" di informazioni inutili
     * @return Testo ripulito, già diviso per parole
     */
    private List<String> cleanInput(String input) {
        return Arrays.stream(input.split("\\s+"))
                .takeWhile(s -> !s.equals("//"))
                .filter(s -> !s.isBlank())
                .filter(s -> !stopWords.contains(s))
                .map(String::trim)
                .toList();
    }

    /**
     * Processa un input dell'utente e fornisci un risultato
     *
     * @param line Linea dell'input utente da processare
     * @return Risultato della computazione
     * @throws CommandException In caso di errori con l'input utente
     */
    public InputParsingResult processInput(String line) throws CommandException {
        var input = cleanInput(line);

        if (input.isEmpty())
            return InputParsingResult.EMPTY;

        // Comandi identificati dalla prima keyword, oppure quelli senza.
        List<Command> overloadCommands;
        List<?> arguments;
        if (commands.containsKey(input.get(0))) {
            overloadCommands = commands.get(input.get(0));
            arguments = parseArguments(input, 1);
        } else {
            overloadCommands = commands.get("");
            arguments = parseArguments(input, 0);
        }

        if (overloadCommands.isEmpty()) {
            // Nessun comando trovato
            throw new CommandNotFoundException(arguments);
        }

        var command = overloadCommands.stream()
                // Controlla se sono parsabili (alcuni comandi sono in overload)
                .filter(c -> c.matchArguments(arguments))
                .findFirst()
                .orElseThrow(() -> new WrongArgumentsException(arguments));
        return new InputParsingResult(command, arguments);
    }

    /**
     * Parsa sintatticamente i parametri richiesti dal comando dell'utente e la loro esistenza.
     * Non controlla la loro accuratezza semantica.
     *
     * @param input  Input dell'utente, già normalizzato
     * @param offset Offset da cui leggere i parametri
     * @return Elenco dei parametri, risolti
     * @throws ParseArgumentsException Errore nel parsing dei parametri forniti
     */
    private List<?> parseArguments(List<String> input, int offset) throws ParseArgumentsException {
        var entities = new ArrayList<>();
        int wordIndex = offset; // La prima parola è il comando, se offset = 1

        while (wordIndex < input.size()) {
            // Parsing del primo argomento in sequenza
            Object foundEntity = null;
            for (int i = input.size() - 1; i >= wordIndex; i--) {
                var entityName = input.stream()
                        .skip(wordIndex)
                        .limit(i)
                        .collect(Collectors.joining(" "));

                var entity = entityResolver.resolveEntity(entityName);
                if (entity.isPresent()) {
                    foundEntity = entity.get();
                    wordIndex = i + 1;
                    break;
                }
            }

            if (foundEntity == null) {
                throw new ParseArgumentsException(input.subList(wordIndex, input.size()));
            } else {
                entities.add(foundEntity);
            }
        }

        return entities;
    }

    /**
     * Controlla se due text engine sono uguali
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se i due oggetti sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextEngine that = (TextEngine) o;
        return Objects.equals(entityResolver, that.entityResolver) && Objects.equals(stopWords, that.stopWords) && Objects.equals(commands, that.commands);
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(entityResolver, stopWords, commands);
    }

    /**
     * BUILDER PATTERN
     * Applicato per creare un text engine, specificando opzionalmente
     * i file da cui prendere i dati che necessita.
     */
    public static class Builder {
        /**
         * File dei comandi
         */
        private Path commandsFile = Path.of(DEFAULT_COMMANDS_FILE);

        /**
         * File delle stop word
         */
        private Path stopWordsFile = Path.of(DEFAULT_STOPWORDS_FILE);

        /**
         * Usa un dato file dei comandi, come {@link Path}
         * @param commandsFile File dei comandi da usare
         * @return <code>this</code> builder
         */
        public Builder setCommandsFile(Path commandsFile) {
            this.commandsFile = commandsFile;
            return this;
        }

        /**
         * Usa un dato file dei comandi, come {@link String}
         * @param commandsFile File dei comandi da usare
         * @return <code>this</code> builder
         */
        public Builder setCommandsFile(String commandsFile) {
            return setCommandsFile(Path.of(commandsFile));
        }

        /**
         * Usa un dato file delle stopword, come {@link Path}
         * @param stopWordsFile File delle stop word da usare
         * @return <code>this</code> builder
         */
        public Builder setStopWordsFile(Path stopWordsFile) {
            this.stopWordsFile = stopWordsFile;
            return this;
        }

        /**
         * Usa un dato file delle stopword, come {@link String}
         * @param stopWordsFile File delle stop word da usare
         * @return <code>this</code> builder
         */
        public Builder setStopWordsFile(String stopWordsFile) {
            return setStopWordsFile(Path.of(stopWordsFile));
        }

        /**
         * Crea il text engine dai file forniti
         * @return Un nuovo TextEngine
         * @throws IOException Errori nei file forniti
         */
        public TextEngine build() throws IOException {
            var commands = new MultiMap<String, Command>();
            var commandsLines = Files.lines(commandsFile);
            StreamUtils.<IOException, String, Command>wrap(checked ->
                    commandsLines
                            .map(checked.wrap(Command::of))
                            .collect(Collectors.groupingBy(Command::getLiteralPrefix))
                            .forEach(commands::add));

            var stopWords = Files.lines(stopWordsFile)
                    .skip(1)
                    .collect(Collectors.toSet());

            return new TextEngine(stopWords, commands);
        }

        /**
         * Controlla se due builder sono uguali
         * @param o Altro oggetto da controllare
         * @return <code>true</code> se sono uguali
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(commandsFile, builder.commandsFile) && Objects.equals(stopWordsFile, builder.stopWordsFile);
        }

        /**
         * Calcola l'hash dell'oggetto
         * @return Hash
         */
        @Override
        public int hashCode() {
            return Objects.hash(commandsFile, stopWordsFile);
        }
    }

    /**
     * Errore di comando non trovato. Considerato fatale ai fini del Fast-Forward
     */
    public static class CommandNotFoundException extends CommandException.Fatal {
        /**
         * Genera un errore con un dato messaggio
         * @param message Messaggio d'errore
         */
        public CommandNotFoundException(String message) {
            super(message);
        }

        /**
         * Genera l'errore su un comando, dove si hanno già gli argomenti forniti
         * @param arguments Argomenti del comando non compreso
         */
        public CommandNotFoundException(List<?> arguments) {
            this(
                    arguments.size() > 0
                            ? Strings.of(StringId.ACTION_ARGS_UNKNOWN, CommandNotFoundException.getArgumentsName(arguments))
                            : Strings.of(StringId.ACTION_UNKNOWN)
            );
        }

        /**
         * Formatta in maniera human-readable gli argomenti per il messaggio d'errore
         * @param arguments Argomenti da formattare
         * @return Human-readable argomenti
         */
        private static String getArgumentsName(List<?> arguments) {
            return arguments.stream()
                    .map(e -> e instanceof Entity ? ((Entity) e).getName() : e.toString())
                    .collect(Collectors.joining(", "));
        }
    }

    /**
     * Errore nel parsing degli argomenti, a livello sintattico.
     * Considerato fatale ai fini del Fast-Forward.
     */
    public static class ParseArgumentsException extends CommandException.Fatal {
        /**
         * Errore, dato l'input utente come stringa
         * @param input Input utente del comando
         */
        public ParseArgumentsException(String input) {
            super(Strings.of(StringId.ARGUMENT_COMMAND_NOT_FOUND, input));
        }

        /**
         * Errore, dato l'input utente come stringa nelle sue componenti
         * @param input Input dell'utente
         */
        public ParseArgumentsException(List<String> input) {
            this(String.join(" ", input));
        }
    }

    /**
     * Errore logico nei parametri forniti dall'utente
     */
    public static class WrongArgumentsException extends CommandException.Fatal {
        /**
         * Errore, dati i parametri sintatticamente corretti ma non semanticamente
         * @param arguments Argomenti dell'utente
         */
        public WrongArgumentsException(List<?> arguments) {
            super(
                    arguments.size() > 0
                            ? Strings.of(StringId.ACTION_ARGS_NOT_ALLOWED, CommandNotFoundException.getArgumentsName(arguments))
                            : Strings.of(StringId.ACTION_NOT_ALLOWED)
            );
        }
    }
}

