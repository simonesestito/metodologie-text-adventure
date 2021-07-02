package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.entita.pojo.features.ApribileCon;

import java.util.List;
import java.util.Objects;

public class Armadio extends OggettoContenitore implements ApribileCon<Tronchesi> {
    private boolean aperto = true;

    public Armadio(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    @Override
    public void apri(Tronchesi oggetto) throws AperturaException {
        if (oggetto != null)
            aperto = true;
    }

    @Override
    public boolean isAperto() {
        return aperto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armadio armadio = (Armadio) o;
        return isAperto() == armadio.isAperto();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAperto());
    }
}
