package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.Gioco;
import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.ObservableObject;

public class Tesoro extends ObservableObject {
    public Tesoro(String name) {
        super(name);
    }

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        var posizioneOriginale = getPosizione();
        super.spostaIn(contenitore);
        if (posizioneOriginale != null && posizioneOriginale != contenitore)
            throw new Gioco.GameOverException();
    }
}
