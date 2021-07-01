package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class OggettoContenitore extends Oggetto implements Contenitore {
    private final List<Posizionabile> contenuto;

    public OggettoContenitore(String name, List<Oggetto> contenuto) {
        super(name);
        this.contenuto = new ArrayList<>(contenuto);
        for (var oggetto : contenuto) {
            try {
                oggetto.spostaIn(this);
            } catch (CommandException ignored) {}
        }
    }

    @Override
    public List<Posizionabile> getOggettiContenuti() {
        return Collections.unmodifiableList(contenuto);
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        contenuto.remove(oggetto);
    }

    @Override
    public String toString() {
        String contentDescription;
        if (this instanceof ApribileCon<?> && !((ApribileCon<?>) this).isAperto()) {
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
}
