package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.objects.Oggetto;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class ObservableObject extends Oggetto implements Observable {
    private final List<Observer> observers = new LinkedList<>();

    public ObservableObject(String name) {
        super(name);
    }

    @Override
    public void observe(Observer canTake) {
        observers.add(canTake);
    }

    @Override
    public void removeObserver(Observer canTake) {
        observers.remove(canTake);
    }

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        var canTake = observers.stream().allMatch(BooleanSupplier::getAsBoolean);
        if (!canTake)
            throw new LockedObjectException();
        super.spostaIn(contenitore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObservableObject that = (ObservableObject) o;
        return Objects.equals(observers, that.observers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observers);
    }
}
