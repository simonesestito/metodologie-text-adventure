package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.UsabileCon;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.List;
import java.util.Objects;

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
            throw new CommandException(Strings.of(StringId.FIREPLACE_LIT));
        super.prendiOggetto(oggetto);
    }

    @Override
    public void usaCon(Secchio secchio) {
        if (secchio.isRiempito())
            spegni();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Camino camino = (Camino) o;
        return isAcceso() == camino.isAcceso();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isAcceso());
    }
}
