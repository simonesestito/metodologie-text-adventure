package it.uniroma1.textadv.entita.pojo.features;

import java.util.Objects;

public class ApribileSemplice implements ApribileCon<Object> {
    private boolean aperto = false;

    @Override
    public boolean isAperto() {
        return aperto;
    }

    @Override
    public void chiudi(Object oggetto) throws ChiusuraException {
        aperto = false;
    }

    @Override
    public void apri(Object oggetto) throws AperturaException {
        aperto = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApribileSemplice that = (ApribileSemplice) o;
        return isAperto() == that.isAperto();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAperto());
    }
}
