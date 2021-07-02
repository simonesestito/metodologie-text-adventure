package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

public interface Rompibile extends UsabileCon<Rompitore> {
    default void rompi(Rompitore rompitore) throws CommandException {
        if (isRotto())
            throw new ObjectBrokenException(toString());

        if (rompitore == null)
            throw new ObjectRequiredException(toString());
    }

    boolean isRotto();

    @Override
    default void usaCon(Rompitore oggetto) throws CommandException {
        rompi(oggetto);
    }

    class ObjectRequiredException extends CommandException {
        public ObjectRequiredException(String oggetto) {
            super(Strings.of(StringId.UNABLE_TO_BREAK, oggetto));
        }
    }

    class ObjectBrokenException extends CommandException {
        public ObjectBrokenException(String oggetto) {
            super(Strings.of(StringId.OBJECT_BROKEN, oggetto));
        }
    }

    class ObjectUnbrokenException extends CommandException {
        public ObjectUnbrokenException(String oggetto) {
            super(Strings.of(StringId.OBJECT_UNBROKEN, oggetto));
        }
    }
}
