package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.Gioco;
import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.features.Contenitore;
import it.uniroma1.textadv.entity.pojo.features.ObservableObject;

/**
 * Il tesoro è l'oggetto la cui presa nell'inventario fa cessare il gioco
 */
public class Tesoro extends ObservableObject {
    /**
     * Crea il tesoro
     *
     * @param name Nome del tesoro
     */
    public Tesoro(String name) {
        super(name);
    }

    /**
     * Sposta il tesoro in un alto posto
     *
     * @param contenitore Dove mettere il tesoro
     * @throws Gioco.GameOverException Deve far terminare il gioco
     * @throws CommandException        Errore nello spostamento del tesoro
     */
    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        var posizioneOriginale = getPosizione();
        super.spostaIn(contenitore);
        if (posizioneOriginale != null && posizioneOriginale != contenitore && contenitore instanceof Inventario)
            throw new Gioco.GameOverException();
    }
}
