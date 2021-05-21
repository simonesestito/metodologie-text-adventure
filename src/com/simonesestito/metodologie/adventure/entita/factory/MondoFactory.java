package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.Mondo;
import com.simonesestito.metodologie.adventure.entita.Stanza;
import com.simonesestito.metodologie.adventure.entita.parser.MondoSectionParser;

import java.nio.file.Path;
import java.util.List;

/**
 * Crea un nuovo mondo avendo un dato file come sorgente
 */
public class MondoFactory implements SectionFactory<Mondo, MondoSectionParser.Result> {
    @Override
    public Mondo create(ParsingBuildContext context, MondoSectionParser.Result result) throws SectionFactory.CreationException {
        try {
            var constructor = Mondo.class.getConstructor(String.class, String.class, Stanza.class);
            var
        } catch () {
        } catch (ReflectiveOperationException e) {
            throw new CreationException(e);
        }
    }

    public Mondo parseFromFile(Path path) throws Exception {
        try (var context = new ParserContext(path)) {
            ParsingBuildContext.Section section;
            while ((section = context.readNextSection()) != null) {

            }
        }
    }
}
