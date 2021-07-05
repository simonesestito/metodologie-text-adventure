package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.Entity;
import it.uniroma1.textadv.entity.pojo.features.Contenitore;
import it.uniroma1.textadv.entity.pojo.features.Posizionabile;
import it.uniroma1.textadv.entity.pojo.features.PosizionabileUnico;

/**
 * Rappresentazione generale di un oggetto nel gioco.
 */
public abstract class Oggetto extends Entity implements Posizionabile {
    /**
     * Implementazione parametrizzata (Strategy) della logica di spostamento
     */
    private final PosizionabileUnico posizionabileUnico = new PosizionabileUnico(this);

    /**
     * Crea un nuovo oggetto
     *
     * @param name Nome dell'oggetto
     */
    public Oggetto(String name) {
        super(name);
    }

    /**
     * Sposta l'oggetto altrove
     *
     * @param contenitore Nuovo contenitore dove stare
     * @throws CommandException Errore nello spostamento
     */
    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        posizionabileUnico.spostaIn(contenitore);
    }

    /**
     * Ottieni la posizione attuale dell'oggetto
     *
     * @return Posizione attuale
     */
    @Override
    public Contenitore getPosizione() {
        return posizionabileUnico.getPosizione();
    }
}
