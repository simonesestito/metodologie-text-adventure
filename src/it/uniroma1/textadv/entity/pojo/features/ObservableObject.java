package it.uniroma1.textadv.entity.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.objects.Oggetto;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * Oggetto che può essere osservato da altre entità che ne bloccano la presa
 */
public class ObservableObject extends Oggetto implements Observable {
    /**
     * Elenco di observer che decidono se l'oggetto può essere preso
     */
    private final List<BooleanSupplier> observers = new LinkedList<>();

    /**
     * Crea un nuovo oggetto osservabile
     *
     * @param name Nome dell'oggetto
     */
    public ObservableObject(String name) {
        super(name);
    }

    /**
     * Osserva l'oggetto potenzialmente impedendone la presa
     *
     * @param canTake Observer che controlla se è prendibile
     */
    @Override
    public void observe(BooleanSupplier canTake) {
        observers.add(canTake);
    }

    /**
     * Smetti di controllare la presa di un oggetto
     *
     * @param canTake Observer da rimuovere
     */
    @Override
    public void removeObserver(BooleanSupplier canTake) {
        observers.remove(canTake);
    }

    /**
     * Sposta un oggetto in un altro posto
     *
     * @param contenitore Nuovo contenitore dove stare
     * @throws CommandException Errore nello spostamento
     */
    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        var canTake = observers.stream().allMatch(BooleanSupplier::getAsBoolean);
        if (!canTake)
            throw new LockedObjectException();
        super.spostaIn(contenitore);
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due oggetti uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due oggetti uguali
     */
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
