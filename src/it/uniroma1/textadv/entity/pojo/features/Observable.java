package it.uniroma1.textadv.entity.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.function.BooleanSupplier;

/**
 * Entità che può essere osservata da altre
 *
 * @see it.uniroma1.textadv.entity.pojo.objects.Tesoro
 * @see it.uniroma1.textadv.entity.pojo.characters.Guardiano
 */
public interface Observable extends Posizionabile {
    /**
     * Osserva l'entità
     * @param canTake Observer che controlla se è prendibile
     */
    void observe(BooleanSupplier canTake);

    /**
     * Smetti di osservare l'entità
     * @param canTake Observer da rimuovere
     */
    void removeObserver(BooleanSupplier canTake);

    /**
     * Eccezione da lanciare se un oggetto osservato e non prendibile, viene provato a prendere
     */
    class LockedObjectException extends CommandException {
        /**
         * Crea un'eccezione con il messaggio di oggetto bloccato di default
         */
        public LockedObjectException() {
            super(Strings.of(StringId.OBJECT_LOCKED));
        }
    }
}
