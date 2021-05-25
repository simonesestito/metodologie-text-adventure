package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.entita.parser.GameFile;

import java.util.List;

public class LinkFactory implements EntityFactory {
    public static final String TAG_NAME = "links";

    @Override
    public void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException {
        for (var line : section) {
            var name = line.getKey();
            var args = line.getSeparatedArguments().toList();
            var className = args.get(0);
            var roomAName = args.get(1);
            var roomBName = args.get(2);
            context.registerHardDependency(new BuildContext.HardDependency(
                    className, name, List.of(name), List.of(roomAName, roomBName)
            ));
        }
    }
}
