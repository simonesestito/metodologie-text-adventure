package com.simonesestito.metodologie.adventure.entita.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Il vero context, visibile in questa forma solo dal parser principale,
 * che dirige la creazione di tutto il mondo.
 * @see ParsingBuildContext
 */
public class ParserContext extends ParsingBuildContext {
    public ParserContext(Path pathSource) throws IOException {
        super(pathSource);
    }

    public ParserContext(BufferedReader inputSource) {
        super(inputSource);
    }

    /**
     * Esponi il metodo al pubblico, in quanto il parser può gestire queste operazioni,
     * non visibili al fruitore di questo context come build client.
     * @throws IOException In caso di errore di I/O nella lettura
     */
    @Override
    public Section readNextSection() throws IOException {
        return super.readNextSection();
    }
}
