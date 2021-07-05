package it.uniroma1.textadv.entity.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

import java.util.Optional;

/**
 * Entità che può essere posizionata in qualche punto del mondo
 */
public interface Posizionabile {
    /**
     * Ottieni la sua posizione attuale
     *
     * @return Posizione attuale
     */
    Contenitore getPosizione();

    /**
     * Sposta l'entità in un altro posto.
     * Dovranno essere aggiornati i suoi riferimenti anche nei contenitori precedente e successivo.
     *
     * @param contenitore Nuovo contenitore dove stare
     * @throws CommandException Errore nello spostamento
     */
    void spostaIn(Contenitore contenitore) throws CommandException;

    /**
     * Ottieni la propria posizione solo se questa permette l'aggiunta di nuovi oggetti.
     * @return {@link Optional} con la posizione attuale, se questa è "aggiungibile"
     */
    default Optional<ContenitoreAggiungibile> getPosizioneAggiungibile() {
        var posizione = getPosizione();
        if (posizione instanceof ContenitoreAggiungibile)
            return Optional.of((ContenitoreAggiungibile) posizione);
        else
            return Optional.empty();
    }
}
