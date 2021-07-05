package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.Set;

/**
 * Contenitore di oggetti, anche non nel vero senso di oggetto, ma anche logico.
 *
 * @see it.uniroma1.textadv.entita.pojo.objects.OggettoContenitore
 * @see it.uniroma1.textadv.entita.pojo.characters.Venditore
 */
public interface Contenitore {
    /**
     * Ottieni gli oggetti contenuti.
     * La vista potrebbe essere immutabile direttamente.
     *
     * @return Oggetti contenuti
     */
    Set<? extends Posizionabile> getOggettiContenuti();

    /**
     * Prendi un oggetto da questo contenitore
     *
     * @param oggetto Oggetto da prendere
     * @throws CommandException Errore nella presa dell'oggetto
     */
    void prendiOggetto(Posizionabile oggetto) throws CommandException;

    /**
     * Eccezione in caso l'entità richiesta non sia stata risolta
     */
    class UnresolvedEntityException extends CommandException.Fatal {
        /**
         * Crea un errore di oggetto non trovato in una data posizione
         *
         * @param nome        Nome dell'oggetto non trovato
         * @param contenitore Contenitore in cui non è stato trovato
         */
        public UnresolvedEntityException(Object nome, Contenitore contenitore) {
            super(Strings.of(StringId.ENTITY_NOT_FOUND, nome.toString(), contenitore.toString()));
        }
    }
}
