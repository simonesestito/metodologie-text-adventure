package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

import java.util.function.BooleanSupplier;

public interface Observable extends Posizionabile {
    void observe(Observer canTake);
    void removeObserver(Observer canTake);

    interface Observer extends BooleanSupplier {}

    class LockedObjectException extends CommandException {
        public LockedObjectException() {
            super("Non è possibile prendere l'oggetto");
        }
    }
}
