package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.SectionParser;

import java.util.List;

/**
 * Factory di un elemento di gioco (una <b>sezione</b> nel file),
 * parsandola dalla rappresentazione a stringa su file
 * @param <T> Elemento costruito dalla Factory
 */
public interface SectionFactory<T, R> {
    default T create(ParsingBuildContext context) throws CreationException, SectionParser.ParseException {
        var section = context.getCurrentSection();
        SectionParser<R> parser = null; // TODO .......
        return create(context, parser.parse(section));
    }

    T create(ParsingBuildContext context, R parsingResult) throws CreationException;

    /**
     * Eccezione in caso di errore nella creazione di un oggetto
     */
    final class CreationException extends Exception {
        public CreationException(String message) {
            super(message);
        }

        public CreationException(String message, Throwable cause) {
            super(message, cause);
        }

        public CreationException(Throwable cause) {
            super(cause);
        }
    }
}
