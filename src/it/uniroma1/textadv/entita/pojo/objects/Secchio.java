package it.uniroma1.textadv.entita.pojo.objects;


import java.util.Objects;

public class Secchio extends Oggetto {
    private boolean riempito;

    public Secchio(String name) {
        super(name);
    }

    public boolean isRiempito() {
        return riempito;
    }

    private void setRiempito(boolean riempito) {
        this.riempito = riempito;
    }

    /* package */ void riempi() {
        setRiempito(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Secchio secchio = (Secchio) o;
        return isRiempito() == secchio.isRiempito();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isRiempito());
    }
}
