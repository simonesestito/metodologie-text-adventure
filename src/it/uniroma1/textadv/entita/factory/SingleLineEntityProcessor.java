package it.uniroma1.textadv.entita.factory;

import it.uniroma1.textadv.entita.parser.GameFile;

/**
 * Processor per gli elementi del mondo definiti su unica linea,
 * con nome entità, classe e argomenti.
 * Siccome sia oggetti, che links, che personaggi, seguono tutti lo stesso
 * formato, questo processor è condiviso.
 */
@EntityProcessor.ForTag({
        SingleLineEntityProcessor.OBJECTS_TAG_NAME,
        SingleLineEntityProcessor.CHARACTERS_TAG_NAME,
        SingleLineEntityProcessor.LINKS_TAG_NAME,
})
public class SingleLineEntityProcessor implements EntityProcessor {
    /**
     * Tag della sezione degli oggetti
     */
    public static final String OBJECTS_TAG_NAME = "objects";

    /**
     * Tag della sezione dei personaggi
     */
    public static final String CHARACTERS_TAG_NAME = "characters";

    /**
     * Tag della sezione dei link
     */
    public static final String LINKS_TAG_NAME = "links";


    /**
     * Elabora la sezione degli oggetti, gestendo gli oggetti indipendenti
     * e quelli che dipendono da un altro oggetto sul quale vengono usati.
     *
     * @param section Sezione da processare
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException Errore di elaborazione della sezione
     */
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();

            var className = line.getSeparatedArguments()
                    .findFirst()
                    .orElseThrow(() -> new GameFile.ParseException("Unable to find Class Name"));

            var arguments = line.getSeparatedArguments()
                    .skip(1)
                    .toList();

            context.registerDependantEntity(new BuildContext.DependantEntity(
                    getClassByTag(section, className),
                    name,
                    arguments
            ));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
