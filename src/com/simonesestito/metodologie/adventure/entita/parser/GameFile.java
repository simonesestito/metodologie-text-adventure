package com.simonesestito.metodologie.adventure.entita.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameFile implements Iterable<GameFile.Section> {
    public static final String TAG_LINE_PREFIX = "[";
    private final List<Section> sections;

    public GameFile(List<Section> sections) {
        this.sections = sections;
    }

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

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public Optional<Section> getSectionByTag(String tag) {
        var sections = getSectionsByTag(tag);
        return sections.isEmpty() ? Optional.empty() : Optional.of(sections.get(0));
    }

    public List<Section> getSectionsByTag(String tag) {
        return sections.stream()
                .filter(s -> s.getTag().getName().equals(tag))
                .toList();
    }

    public static class Section implements Iterable<Line> {
        private final Tag tag;
        private final Map<String, Line> lines;

        public Section(Tag tag, Map<String, Line> lines) {
            this.tag = tag;
            this.lines = lines;
        }

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

        public Tag getTag() {
            return tag;
        }

        public Collection<Line> getLines() {
            return lines.values();
        }

        public Optional<Line> getLine(String key) {
            return Optional.ofNullable(lines.get(key));
        }

        @Override
        public Iterator<Line> iterator() {
            return getLines().iterator();
        }
    }

    public static class Tag {
        public static final char ARGUMENT_SEPARATOR = ':';
        private final String name;
        private final String argument;

        public Tag(String name, String argument) {
            this.name = name;
            this.argument = argument;
        }

        public static Tag fromTagLine(String line) {
            int argumentSeparatorIndex = line.indexOf(ARGUMENT_SEPARATOR);
            if (argumentSeparatorIndex < 0) {
                // Trovato solo il nome del tag (links, characters, ...)
                return new Tag(line.substring(1, line.length() - 1), null);
            } else {
                return new Tag(
                        line.substring(1, argumentSeparatorIndex),
                        line.substring(argumentSeparatorIndex + 1, line.length() - 1)
                );
            }
        }

        public String getName() {
            return name;
        }

        public Optional<String> getArgument() {
            return Optional.ofNullable(argument);
        }
    }

    public static class Line {
        public static final String ARGUMENTS_SEPARATOR = "\t";
        public static final Function<String, Stream<String>> COMMA_SEPARATED_ARG_MAPPER = s -> Arrays.stream(s.split(","));
        public static final Function<String, Stream<String>> TAB_SEPARATED_ARG_MAPPER = s -> Arrays.stream(s.split(ARGUMENTS_SEPARATOR));

        private final String text;

        public Line(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public String getKey() {
            return text.substring(0, text.indexOf(ARGUMENTS_SEPARATOR));
        }

        public String getArgumentsString() {
            return text.substring(text.indexOf(ARGUMENTS_SEPARATOR) + 1);
        }

        public Stream<String> getSeparatedArguments() {
            return getArgument(TAB_SEPARATED_ARG_MAPPER);
        }

        public Stream<String> getCommaSeparatedArguments() {
            return getArgument(COMMA_SEPARATED_ARG_MAPPER);
        }

        public <R> R getArgument(Function<String, R> mapper) {
            return mapper.apply(getArgumentsString());
        }
    }

    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Override
    public Iterator<Section> iterator() {
        return sections.iterator();
    }
}
