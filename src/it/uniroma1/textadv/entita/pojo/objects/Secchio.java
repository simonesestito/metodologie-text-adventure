package it.uniroma1.textadv.entita.pojo.objects;


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
}
