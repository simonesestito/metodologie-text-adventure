package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.function.BooleanSupplier;

public interface Observable extends Posizionabile {
    void observe(Observer canTake);
    void removeObserver(Observer canTake);

    interface Observer extends BooleanSupplier {}

    class LockedObjectException extends CommandException {
        public LockedObjectException() {
            super(Strings.of(StringId.OBJECT_LOCKED));
        }
    }
}
