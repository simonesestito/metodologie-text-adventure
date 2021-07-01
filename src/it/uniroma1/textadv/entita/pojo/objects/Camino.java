package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.UsabileCon;

import java.util.List;

public class Camino extends OggettoContenitore implements UsabileCon<Secchio> {
    private boolean acceso = true;

    public Camino(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    public boolean isAcceso() {
        return acceso;
    }

    private void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public void accendi() {
        setAcceso(true);
    }

    public void spegni() {
        setAcceso(false);
    }

    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (acceso)
            throw new CommandException("Il camino è ancora acceso");
        super.prendiOggetto(oggetto);
    }

    @Override
    public void usaCon(Secchio secchio) {
        if (secchio.isRiempito())
            spegni();
    }
}
