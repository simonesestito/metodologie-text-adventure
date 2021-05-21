package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.Stanza;
import com.simonesestito.metodologie.adventure.entita.parser.ConditionalBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contesto di tutte le altre entità finora parsate dal file,
 * e dello stato generale del parsing.
 *
 * Adatto alle factory che creano un'entità ma non hanno il controllo del parsing.
 * L'unica factory principale dovrà istanziare una sua sottoclasse concreta
 * e passare alle altre factory "client" la vista {@link ParsingBuildContext}.
 */
public abstract class ParsingBuildContext implements AutoCloseable {
    private final ConditionalBufferedReader inputSource;
    private final Map<String, Stanza> stanze = new HashMap<>();
    private Section currentSection;

    public ParsingBuildContext(Path pathSource) throws IOException {
        this(Files.newBufferedReader(pathSource));
    }

    public ParsingBuildContext(BufferedReader inputSource) {
        this.inputSource = new ConditionalBufferedReader(inputSource);
    }

    public Stanza getRoomByName(String name) {
        return Objects.requireNonNull(stanze.get(name), "Nome non trovato: " + name);
    }

    public void addRoom(String name, Stanza stanza) {
        stanze.put(name, stanza);
    }

    protected Section readNextSection() throws IOException {
        var sectionLines = Stream.concat(
                Stream.of(inputSource.readLine()),
                inputSource.readLinesWhile(s -> !s.startsWith("["))
        ).toList();
        currentSection = new Section(sectionLines);
        return currentSection;
    }

    public Section getCurrentSection() {
        return currentSection;
    }

    @Override
    public void close() throws Exception {
        inputSource.close();
    }

    public static final class Section {
        private final List<String> sectionLines;

        public Section(List<String> sectionLines) {
            this.sectionLines = sectionLines;
        }

        public String getTag() {
            // Controlla se contiene un argomento:
            // è preceduto da :
            int argumentSeparatorIndex = getTagLine().indexOf(':');
            if (argumentSeparatorIndex > -1) {
                // Elimina l'argomento
                return getTagLine().substring(1, argumentSeparatorIndex);
            } else {
                // Elimina le sole parentesi quadre
                return getTagLine().substring(1, getTagLine().length() - 1);
            }
        }

        public Optional<String> getTagArgument() {
            int argumentSeparatorIndex = getTagLine().indexOf(':');
            if (argumentSeparatorIndex == -1)
                return Optional.empty();

            return Optional.of(
                    getTagLine().substring(
                            argumentSeparatorIndex + 1,
                            getTagLine().length() - 1)
            );
        }

        public String getTagLine() {
            return sectionLines.get(0);
        }

        public List<String> getBody() {
            return sectionLines.subList(1, sectionLines.size());
        }

        /**
         * Ottiene le varie linee del body,
         * raccolte in base alla chiave (intesa come prima stringa tab-separated),
         * e avente per valore l'elenco dei restanti parametri, anch'essi tab-separated.
         * @return Mappa delle linee del body
         */
        public Map<String, List<String>> getBodyAsMap() {
            return getBody().stream()
                    .map(line -> line.split("\t"))
                    .map(List::of)
                    .collect(Collectors.toMap(
                            lineParams -> lineParams.get(0),
                            lineParams -> lineParams.subList(1, lineParams.size())
                    ));
        }
    }
}
