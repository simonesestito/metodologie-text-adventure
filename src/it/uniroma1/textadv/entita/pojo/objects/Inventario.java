package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.engine.EntityResolver;
import it.uniroma1.textadv.entita.pojo.features.ContenitoreAggiungibile;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;

import java.util.*;
import java.util.stream.Stream;

public class Inventario implements ContenitoreAggiungibile {
    private final Set<Posizionabile> inventario = new HashSet<>();

    @Override
    public Set<Posizionabile> getOggettiContenuti() {
        return Collections.unmodifiableSet(inventario);
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!inventario.contains(oggetto))
            throw new EntityResolver.UnresolvedEntityException(oggetto, this);
        inventario.remove(oggetto);
    }

    @Override
    public void aggiungiOggetto(Posizionabile oggetto) throws CommandException {
        oggetto.spostaIn(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventario that = (Inventario) o;
        return Objects.equals(inventario, that.inventario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventario);
    }
}
