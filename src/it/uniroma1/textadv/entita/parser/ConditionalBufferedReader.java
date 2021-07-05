package it.uniroma1.textadv.entita.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Lettore in grado di subordinare la lettura da input,
 * se una condizione in input viene soddisfatta.
 * <p>
 * Utilizza il <b>Decorator pattern</b> su BufferedReader
 */
public class ConditionalBufferedReader extends BufferedReader implements AutoCloseable {
    /**
     * Lettore sorgente, da cui prendere i dati in origine
     */
    private final BufferedReader sourceReader;

    /**
     * Riga precedentemente letta, ma non voluta dalla condizione di lettura.
     * <p>
     * Resta in memoria finchè non arriverà una lettura che la accetterà.
     */
    private String previousLine;

    /**
     * Crea un nuovo reader che permette la lettura condizionata
     *
     * @param sourceReader Reader da cui leggere le linee
     */
    public ConditionalBufferedReader(BufferedReader sourceReader) {
        super(sourceReader);
        this.sourceReader = sourceReader;
    }

    /**
     * Crea un nuovo reader da un file dato in input
     *
     * @param file File da leggere con questo lettore
     * @throws IOException Errore di apertura del file
     */
    public ConditionalBufferedReader(Path file) throws IOException {
        this(Files.newBufferedReader(file));
    }

    /**
     * Crea un nuovo reader da un {@link java.io.InputStream}
     *
     * @param inputStream Stream da leggere con questo lettore
     */
    public ConditionalBufferedReader(InputStream inputStream) {
        this(new BufferedReader(new InputStreamReader(inputStream)));
    }

    /**
     * Leggi la prossima linea, se disponibile.
     *
     * @return Prossima linea non letta, o <code>null</code> se EOF
     * @throws IOException Errore di I/O nella lettura del file
     */
    public String readLine() throws IOException {
        return readLine(__ -> true);
    }

    /**
     * Leggi la prossima linea non vuota, se disponibile.
     * <p>
     * Tutte le linee precedenti a quella restituita verranno saltate e perse definitivamente.
     *
     * @return Prossima linea non letta, o <code>null</code> se EOF
     * @throws IOException Errore di I/O nella lettura del file
     */
    public String readNotBlank() throws IOException {
        String line;
        do {
            line = sourceReader.readLine();
        } while (line != null && line.isBlank());
        return line;
    }

    /**
     * Leggi la prossima linea non vuota,
     * che rispetti una data condizione.
     * <p>
     * Tutte le linee vuote verranno perse, e la prima linea non vuota
     * che non rispetta la condizione, verrà salvata e usata alla prossima chiamata.
     *
     * @param readCondition Condizione che la stringa deve soddisfare per venire letta
     * @return Linea non vuota che soddisfa la condizione,
     * o <code>null</code> se EOF o condizione non soffisfatta
     * @throws IOException Errore di I/O nella lettura del file
     */
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

    /**
     * Leggi la prossima linea, restituendo <code>null</code> se
     * c'è stato un errore di I/O o se è raggiunto EOF.
     *
     * @return Prossima linea, anche vuota, o <code>null</code>
     */
    public String readLineOrNull() {
        try {
            return readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Leggi la prossima linea, restituendo <code>null</code> se
     * c'è stato un errore di I/O o se è raggiunto EOF.
     * Inoltre, viene applicata una condizione di lettura, restituendo <code>null</code>
     * se non soddisfatta.
     *
     * @param readCondition Condizione affinchè prosegui la lettura
     * @return Prossima linea che soddisfa la condizione, o <code>null</code>
     * @see ConditionalBufferedReader#readLine(Predicate)
     */
    public String readLineOrNull(Predicate<String> readCondition) {
        try {
            return readLine(readCondition);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Leggi tutte le linee in uno {@link Stream} saltando le linee vuote.
     *
     * @return Stream delle linee
     */
    public Stream<String> readLines() {
        return readLinesWhile(__ -> true);
    }

    /**
     * Leggi tutte le linee che soddisfano la condizione,
     * interrompendo lo {@link Stream} alla prima linea che non la soddisfa,
     * la quale non verrà inclusa nel risultato.
     * <p>
     * Le linee vuote verranno saltate.
     *
     * @param readCondition Condizione di lettura
     * @return Stream delle linee finchè la condizione è verificata
     */
    public Stream<String> readLinesWhile(Predicate<String> readCondition) {
        return Stream.generate(() -> readLineOrNull(readCondition))
                .takeWhile(Objects::nonNull)
                .filter(s -> !s.isBlank());
    }

    /**
     * Leggi tutte le linee che soddisfano la condizione,
     * restituendole sotto forma di {@link Stream},
     * includendo la prima linea anche se essa non verifica la condizione di lettura.
     *
     * @param readCondition Condizione di lettura
     * @return Stream della prima linea e delle successive rispettanti la condizione
     * @see ConditionalBufferedReader#readLinesWhile(Predicate)
     */
    public Stream<String> readNextLineAndWhile(Predicate<String> readCondition) {
        var firstLine = readLineOrNull();
        if (firstLine == null)
            return Stream.empty();

        return Stream.concat(
                Stream.of(firstLine),
                readLinesWhile(readCondition)
        );
    }

    /**
     * Chiudi il lettore corrente, chiudendo il lettore sottostante originale.
     */
    @Override
    public void close() throws IOException {
        sourceReader.close();
    }

    /**
     * Controlla se i due reader sono uguali
     * @param o Altro reader, od oggetto, da controllare
     * @return <code>true</code> se i reader sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionalBufferedReader that = (ConditionalBufferedReader) o;
        return Objects.equals(sourceReader, that.sourceReader) && Objects.equals(previousLine, that.previousLine);
    }

    /**
     * Calcola l'hash dell'oggetto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(sourceReader, previousLine);
    }
}
