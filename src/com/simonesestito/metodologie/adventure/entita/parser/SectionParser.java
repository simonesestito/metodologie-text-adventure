package com.simonesestito.metodologie.adventure.entita.parser;

import com.simonesestito.metodologie.adventure.entita.factory.ParsingBuildContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Parser dei tipi di una sezione del salvataggio di gioco.
 *
 * @param <R> Tipo del risultato del parsing
 */
public interface SectionParser<R> {
    Converter<String> STRING_CONVERTER = params -> params.get(0);
    Converter<List<String>> COMMA_PARAMS_CONVERTER = params -> Arrays.asList(params.get(0).split(","));

    R parse(ParsingBuildContext.Section section) throws ParseException;

    static String requireTagArgument(ParsingBuildContext.Section section) throws ParseException {
        return section.getTagArgument().orElseThrow(() -> new ParseException("Argomento nel tag mancante"));
    }

    static <R> R requireFieldAs(Map<String, List<String>> sectionBody, String key, Converter<R> converter) throws ParseException {
        return requireFieldAs(sectionBody, key, converter, "Linea mancante per la chiave");
    }

    static <R> R requireFieldAs(Map<String, List<String>> sectionBody, String key, Converter<R> converter, String missingKeyError) throws ParseException {
        return converter.convert(requireField(sectionBody, key));
    }

    static List<String> requireField(Map<String, List<String>> sectionBody, String key) throws ParseException {
        var lineParams = sectionBody.get(key);
        if (lineParams == null) {
            throw new ParseException("Linea con chiave mancante: " + key);
        }
        return lineParams;
    }

    /**
     * Errore durante il parsing, a livello di formato di file.
     * <p>
     * Es: linee mancanti, parametri formattati male, etc
     * Non copre errori semantici (es: riferimenti a oggetti mai dichiarati)
     */
    class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }

        public ParseException(Throwable cause) {
            super(cause);
        }
    }

    @FunctionalInterface
    interface Converter<R> {
        R convert(List<String> params) throws ParseException;
    }
}
