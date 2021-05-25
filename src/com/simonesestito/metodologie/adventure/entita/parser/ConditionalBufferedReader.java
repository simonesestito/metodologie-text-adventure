package com.simonesestito.metodologie.adventure.entita.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Lettore in grado di visionare la linea successiva,
 * senza doverla per forza leggere e far avanzare il cursore.
 */
public class ConditionalBufferedReader implements AutoCloseable {
    private final BufferedReader sourceReader;
    private String previousLine;

    /**
     * Crea un nuovo reader che permette il peek di una linea
     *
     * @param sourceReader Reader da cui leggere le linee
     */
    public ConditionalBufferedReader(BufferedReader sourceReader) {
        this.sourceReader = sourceReader;
    }

    public ConditionalBufferedReader(Path file) throws IOException {
        this(Files.newBufferedReader(file));
    }

    public String readLine() throws IOException {
        return readLine(__ -> true);
    }

    public String readNotBlank() throws IOException {
        String line;
        do {
            line = sourceReader.readLine();
        } while (line != null && line.isBlank());
        return line;
    }

    public String readLine(Predicate<String> readCondition) throws IOException {
        if (previousLine == null) {
            // La linea precedente, se esistente, è già stata restituita.
            // Vai avanti a leggere la prossima
            previousLine = readNotBlank();
            if (previousLine == null) {
                // EOF segnalato dal reader sottostante
                return null;
            }
        }

        if (readCondition.test(previousLine)) {
            // Linea letta, eliminala da questo lettore
            String readLine = previousLine;
            previousLine = null;
            return readLine;
        } else {
            // Linea non richiesta
            return null;
        }
    }

    public String readLineOrNull() {
        try {
            return readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public String readLineOrNull(Predicate<String> readCondition) {
        try {
            return readLine(readCondition);
        } catch (IOException e) {
            return null;
        }
    }

    public Stream<String> readLinesWhile(Predicate<String> readCondition) {
        Predicate<String> notNull = Objects::nonNull;
        return Stream.generate(() -> readLineOrNull(readCondition))
                .takeWhile(notNull)
                .filter(s -> !s.isBlank());
    }

    public Stream<String> readNextLineAndWhile(Predicate<String> readCondition) {
        var firstLine = readLineOrNull();
        if (firstLine == null)
            return Stream.empty();

        return Stream.concat(
                Stream.of(firstLine),
                readLinesWhile(readCondition)
        );
    }

    @Override
    public void close() throws Exception {
        sourceReader.close();
    }
}
