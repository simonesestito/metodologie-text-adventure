package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.util.List;

@EntityFactory.ForTag("objects")
public class OggettoFactory implements EntityFactory {
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var args = line.getSeparatedArguments().iterator();
            var className = args.next();
            var subject = args.hasNext() ? args.next() : null;

            context.registerHardDependency(new BuildContext.HardDependency(
                    className,
                    name,
                    List.of(name),
                    subject == null ? List.of() : List.of(subject)
            ));
        }
    }
}
