package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.engine.EntityResolver;
import it.uniroma1.textadv.entita.pojo.features.ContenitoreAggiungibile;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Inventario implements ContenitoreAggiungibile {
    private final List<Posizionabile> inventario = new ArrayList<>();

    @Override
    public List<Posizionabile> getOggettiContenuti() {
        return Collections.unmodifiableList(inventario);
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!inventario.contains(oggetto))
            throw new EntityResolver.UnresolvedEntityException(oggetto, this);
        inventario.remove(oggetto);
    }

    @Override
    public void aggiungiOggetto(Posizionabile oggetto) {
        inventario.add(oggetto);
    }

    public boolean isEmpty() {
        return inventario.isEmpty();
    }

    public Stream<Posizionabile> stream() {
        return inventario.stream();
    }

    @Override
    public String toString() {
        return "inventario";
    }
}
