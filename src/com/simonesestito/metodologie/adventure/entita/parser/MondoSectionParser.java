package com.simonesestito.metodologie.adventure.entita.parser;

import com.simonesestito.metodologie.adventure.entita.factory.ParsingBuildContext;

public class MondoSectionParser implements SectionParser<MondoSectionParser.Result> {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_START_ROOM = "start";

    public Result parse(ParsingBuildContext.Section section) throws ParseException {
        var sectionBody = section.getBodyAsMap();
        return new Result(
                SectionParser.requireTagArgument(section),
                SectionParser.requireFieldAs(sectionBody, KEY_DESCRIPTION, STRING_CONVERTER),
                SectionParser.requireFieldAs(sectionBody, KEY_START_ROOM, STRING_CONVERTER)
        );
    }

    public static record Result(
            String name,
            String description,
            String startRoomName
    ) {
    }
}
