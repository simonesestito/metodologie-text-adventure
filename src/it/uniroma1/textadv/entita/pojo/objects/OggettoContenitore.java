package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.engine.EntityResolver;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;

import java.util.*;
import java.util.stream.Collectors;

public abstract class OggettoContenitore extends Oggetto implements Contenitore {
    protected final Set<Posizionabile> contenuto;

    public OggettoContenitore(String name, List<? extends Oggetto> contenuto) {
        super(name);
        this.contenuto = new HashSet<>(contenuto);
        for (var oggetto : contenuto) {
            try {
                oggetto.spostaIn(this);
            } catch (CommandException ignored) {}
        }
    }

    @Override
    public Set<Posizionabile> getOggettiContenuti() {
        return Collections.unmodifiableSet(contenuto);
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!contenuto.contains(oggetto))
            throw new EntityResolver.UnresolvedEntityException(oggetto, this);
        contenuto.remove(oggetto);
    }

    protected boolean isContentHidden() {
        return this instanceof ApribileCon<?> && !((ApribileCon<?>) this).isAperto();
    }

    @Override
    public String toString() {
        String contentDescription;
        if (isContentHidden()) {
            contentDescription = "c'è qualcosa dentro";
        } else if (getOggettiContenuti().isEmpty()) {
            contentDescription = "dentro non c'è nulla";
        } else {
            contentDescription = "dentro c'è: " + getOggettiContenuti()
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));
        }
        return super.toString() + ", " + contentDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OggettoContenitore that = (OggettoContenitore) o;
        return Objects.equals(contenuto, that.contenuto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contenuto);
    }
}
