package it.uniroma1.textadv.engine;

import it.uniroma1.textadv.MultiMap;
import it.uniroma1.textadv.StreamUtils;
import it.uniroma1.textadv.entita.parser.ConditionalBufferedReader;
import it.uniroma1.textadv.entita.pojo.Entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Elabora l'input utente e capisce i comandi
 */
public class TextEngine {
    private final EntityResolver entityResolver = new EntityResolver();
    private final Set<String> stopWords;

    private final MultiMap<String, Command> commands = new MultiMap<>();

    private TextEngine(Path commandsFile, Path stopWordsFile) throws CommandNotFoundException, IOException {
        var commandsLines = Files.lines(commandsFile);
        StreamUtils.<CommandNotFoundException, String, Command>wrap(checked ->
                commandsLines
                        .map(checked.wrap(Command::of))
                        .collect(Collectors.groupingBy(Command::getLiteralPrefix))
                        .forEach(commands::add));

        this.stopWords = Files.lines(stopWordsFile)
                .skip(1)
                .collect(Collectors.toSet());

    }

    public static class Builder {
        public static final String DEFAULT_COMMANDS_FILE = "commands.csv";
        public static final String DEFAULT_STOPWORDS_FILE = "stopwords.txt";
        private Path commandsFile = Path.of(DEFAULT_COMMANDS_FILE);
        private Path stopWordsFile = Path.of(DEFAULT_STOPWORDS_FILE);

        public Builder setCommandsFile(Path commandsFile) {
            this.commandsFile = commandsFile;
            return this;
        }

        public Builder setCommandsFile(String commandsFile) {
            return setCommandsFile(Path.of(commandsFile));
        }

        public Builder setStopWordsFile(Path stopWordsFile) {
            this.stopWordsFile = stopWordsFile;
            return this;
        }

        public Builder setStopWordsFile(String stopWordsFile) {
            return setStopWordsFile(Path.of(stopWordsFile));
        }

        public TextEngine build() throws IOException, CommandNotFoundException {
            return new TextEngine(commandsFile, stopWordsFile);
        }
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

    public InputParsingResult processInput(String line) throws CommandException {
        var input = cleanInput(line);
        var arguments = parseArguments(input);

        if (input.isEmpty())
            return InputParsingResult.EMPTY;

        // Comandi identificati dalla prima keyword
        var overloadCommands = commands.get(input.get(0));

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

    public List<?> parseArguments(List<String> input) throws ParseArgumentsException {
        var entities = new ArrayList<>();
        int wordIndex = 1; // La prima parola è il comando

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

    public static class CommandNotFoundException extends CommandException.Fatal {
        public CommandNotFoundException(String message) {
            super(message);
        }

        public CommandNotFoundException(List<?> arguments) {
            this("Non ho capito cosa fare" + (
                    arguments.size() > 0 ? " con " + getArgumentsName(arguments) : ""
            ));
        }

        private static String getArgumentsName(List<?> arguments) {
            return arguments.stream()
                    .map(e -> e instanceof Entity ? ((Entity) e).getName() : e.toString())
                    .collect(Collectors.joining(", "));
        }
    }

    public static class ParseArgumentsException extends CommandNotFoundException {
        public ParseArgumentsException(String input) {
            super("Non ho capito a che ti riferisci con " + input);
        }

        public ParseArgumentsException(List<String> input) {
            this(String.join(" ", input));
        }
    }

    public static class WrongArgumentsException extends CommandNotFoundException {
        public WrongArgumentsException(List<?> arguments) {
            super("Non posso eseguire questa azione " + (
                    arguments.size() > 0
                            ? "con " + CommandNotFoundException.getArgumentsName(arguments)
                            : "senza nulla"
            ));
        }
    }
}

