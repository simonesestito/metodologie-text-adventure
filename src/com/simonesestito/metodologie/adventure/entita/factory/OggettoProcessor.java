package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.util.List;

@EntityProcessor.ForTag(OggettoProcessor.TAG_NAME)
public class OggettoProcessor implements EntityProcessor {
    public static final String TAG_NAME = "objects";

    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var args = line.getSeparatedArguments().iterator();
            var className = args.next();
            var subject = args.hasNext() ? args.next() : null;

            context.registerDependantEntity(new BuildContext.DependantEntity(
                    className,
                    name,
                    subject == null ? List.of() : List.of(subject)
            ));
        }
    }
}
