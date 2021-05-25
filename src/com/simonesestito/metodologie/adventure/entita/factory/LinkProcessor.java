package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.util.List;

@EntityProcessor.ForTag("links")
public class LinkProcessor implements EntityProcessor {
    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var args = line.getSeparatedArguments().iterator();
            var className = args.next();
            var roomAName = args.next();
            var roomBName = args.next();
            context.registerDependantEntity(new BuildContext.DependantEntity(
                    className, name, List.of(roomAName, roomBName)
            ));
        }
    }
}
