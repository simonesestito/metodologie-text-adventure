package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.entita.pojo.objects.Chiave;

import java.util.Objects;

public class ApribileConChiave implements ApribileCon<Chiave> {
    private Chiave chiave;
    private boolean aperto = false;

    @Override
    public void apri(Chiave chiave) throws AperturaException {
        if (chiave != this.chiave)
            throw new AperturaException();
        aperto = true;
    }

    @Override
    public void chiudi(Chiave chiave) throws ChiusuraException {
        if (this.chiave != null)
            throw new ChiusuraException();
        this.chiave = chiave;
        aperto = false;
    }

    @Override
    public boolean isAperto() {
        return aperto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApribileConChiave that = (ApribileConChiave) o;
        return isAperto() == that.isAperto() && Objects.equals(chiave, that.chiave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chiave, isAperto());
    }
}
