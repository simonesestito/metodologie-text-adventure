package com.simonesestito.metodologie.adventure.entita.parser;

import java.io.BufferedReader;
import java.io.IOException;
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

    public String readLine() throws IOException {
        return readLine(__ -> true);
    }

    public String readLine(Predicate<String> readCondition) throws IOException {
        if (previousLine == null) {
            // La linea precedente, se esistente, è già stata restituita.
            // Vai avanti a leggere la prossima
            previousLine = sourceReader.readLine();
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

    public String readLineOrNull(Predicate<String> readCondition) {
        try {
            return readLine(readCondition);
        } catch (IOException e) {
            return null;
        }
    }

    public Stream<String> readLinesWhile(Predicate<String> readCondition) {
        Predicate<String> notNull = Objects::nonNull;
        return Stream.generate(() -> readLineOrNull(readCondition)).takeWhile(notNull);
    }

    @Override
    public void close() throws Exception {
        sourceReader.close();
    }
}
