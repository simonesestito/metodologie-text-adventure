package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

@EntityProcessor.ForTag(PersonaggioProcessor.TAG_NAME)
public class PersonaggioProcessor implements EntityProcessor {
    public static final String TAG_NAME = "characters";

    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var className = line.getArgumentsString();
            context.registerDependantEntity(
                    new BuildContext.DependantEntity(className, name)
            );
        }
    }
}
