package it.uniroma1.textadv.entita.pojo.features;


public class ApribileConBlocco implements ApribileCon<BloccoApertura> {
    private BloccoApertura blocco;

    @Override
    public void apri(BloccoApertura blocco) throws AperturaException {
        if (!isAperto())
            throw new AperturaException();
    }

    @Override
    public void chiudi(BloccoApertura blocco) throws ChiusuraException {
        if (this.blocco != null && this.blocco.isBloccato())
            throw new ChiusuraException();
        this.blocco = blocco;
    }

    @Override
    public boolean isAperto() {
        return !blocco.isBloccato();
    }
}
