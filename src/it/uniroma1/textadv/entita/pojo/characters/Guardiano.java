package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.ObservableObject;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.Ricevitore;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Guardiano extends Personaggio implements Ricevitore<Entity, Object>, Contenitore {
    private final Posizionabile distrazione;
    private boolean distratto;

    public Guardiano(String name, ObservableObject oggetto, Posizionabile distrazione) {
        super(name);
        this.distrazione = distrazione;
        oggetto.observe(this::isDistratto);
    }

    private boolean isDistratto() {
        return distratto;
    }

    @Override
    public Set<Object> ricevi(Entity oggetto) {
        distratto = Objects.equals(oggetto, distrazione);
        return Set.of(); // Il guardiano non restituisce alcun oggetto, è solo distratto
    }

    @Override
    public Set<? extends Posizionabile> getOggettiContenuti() {
        return distratto ? Set.of(distrazione) : Set.of();
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (Objects.equals(oggetto, distrazione))
            distratto = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Guardiano guardiano = (Guardiano) o;
        return isDistratto() == guardiano.isDistratto() && Objects.equals(distrazione, guardiano.distrazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), distrazione, isDistratto());
    }
}
