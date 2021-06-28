package com.simonesestito.metodologie.adventure.engine;

import com.simonesestito.metodologie.adventure.MultiMap;
import com.simonesestito.metodologie.adventure.entita.parser.ConditionalBufferedReader;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Elabora l'input utente e capisce i comandi
 */
public class TextEngine {
    public static final String DEFAULT_COMMANDS_FILE = "/commands.csv";
    private final EntityResolver entityResolver = new EntityResolver();

    // TODO: più stop word
    public static final Set<String> STOP_WORDS_SET = Set.of(
            "a",
            "in",
            "su",
            "per",
            "la",
            "e",
            "con",
            "il",
            "sulla",
            "dalla",
            "da",
            "una",
            "un"
    );

    private final MultiMap<String, Command> commands = new MultiMap<>();

    public TextEngine(String commandsFile) {
        new ConditionalBufferedReader(getClass().getResourceAsStream(commandsFile))
                .readLines()
                .map(Command::of)
                .collect(Collectors.groupingBy(Command::getLiteralPrefix))
                .forEach(commands::add);
    }

    public TextEngine() {
        this(DEFAULT_COMMANDS_FILE);
    }

    /*
    // TODO: reflection auto-register
    // TODO: Soluzioni migliori?
    public static final List<CommandClassifier<?>> CLASSIFIER_LIST = List.of(
            new MovementCommandClassifier()
    );

    // TODO: Creare il comando, secondo Command Pattern
    public Command processInput(String input) throws CommandException {
        return CLASSIFIER_LIST.stream()
                .map(c -> {
                    try {
                        return c.processInput(cleanInput(input));
                    } catch (CommandException e) {
                        return Optional.<Command>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UnknownCommand(input));
    }
    */

    /**
     * Ripulisci l'input di informazioni non necessarie, come le <b>stop-word</b>.
     *
     * @param input Testo "sporco" di informazioni inutili
     * @return Testo ripulito, già diviso per parole
     */
    private List<String> cleanInput(String input) {
        return Arrays.stream(input.split("\\s+"))
                .filter(s -> !s.isBlank())
                .filter(s -> !STOP_WORDS_SET.contains(s))
                .toList();
    }

    public void processInput(String line) {
        var input = cleanInput(line);

        try {
            var arguments = parseArguments(input);
            var argumentsName = arguments.stream()
                    .map(e -> e instanceof Entity ? ((Entity) e).getName() : e.toString())
                    .collect(Collectors.joining(", "));

            // Comandi identificati dalla prima keyword
            var overloadCommands = commands.get(input.get(0));

            if (overloadCommands.isEmpty()) {
                // Nessun comando trovato
                throw new CommandException("Non ho capito cosa fare" + (
                        arguments.size() > 0 ? " con " + argumentsName : ""
                ));
            }

            overloadCommands.stream()
                    // Controlla se sono parsabili (alcuni comandi sono in overload)
                    .filter(c -> c.matchArguments(arguments))
                    .findFirst()
                    .orElseThrow(() -> new CommandException("Non posso eseguire questa azione " + (
                            arguments.size() > 0 ? "con " + argumentsName : "senza nulla"
                    )))
                    .execute(arguments);
        } catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<?> parseArguments(List<String> input) throws CommandException {
        var entities = new ArrayList<>();
        int wordIndex = 1; // La prima parola è il comando

        while (wordIndex < input.size()) {
            // Parsing del primo argomento in sequenza
            Object foundEntity = null;
            for (int i = wordIndex; i < input.size(); i++) {
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
                throw new CommandException("Non ho capito a che ti riferisci");
            } else {
                entities.add(foundEntity);
            }
        }

        return entities;
    }
}

