package it.uniroma1.textadv.entity.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.features.Posizionabile;
import it.uniroma1.textadv.entity.pojo.features.UsabileCon;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.List;

/**
 * Rappresenta un camino, spegnibile con un secchio e che contiene altri oggetti dentro
 */
public class Camino extends OggettoContenitore implements UsabileCon<Secchio> {
    /**
     * Stato del camino
     */
    private boolean acceso = true;

    /**
     * Crea un nuovo camino, già acceso
     * @param name Nome del camino
     * @param contenuto Contenuto del camino
     */
    public Camino(String name, List<Oggetto> contenuto) {
        super(name, contenuto);
    }

    /**
     * Controlla se il camino è acceso
     * @return <code>true</code> se è acceso
     */
    public boolean isAcceso() {
        return acceso;
    }

    /**
     * Imposta lo stato di accensione del camino
     * @param acceso Stato di accensione
     */
    private void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    /**
     * Accendi il camino
     */
    public void accendi() {
        setAcceso(true);
    }

    /**
     * Spegni il camino
     */
    public void spegni() {
        setAcceso(false);
    }

    /**
     * Prendi un oggetto nel camino, avendo cura che questo sia stato spento
     * @param oggetto Oggetto da prendere
     * @throws CommandException Errore nella presa dell'oggetto
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (acceso)
            throw new CommandException("""
                               (  .      )
                           )           (              )
                                 .  '   .   '  .  '  .
                        (    , )       (.   )  (   ',    )
                         .' ) ( . )    ,  ( ,     )   ( .
                      ). , ( .   (  ) ( , ')  .' (  ,    )
                     (_,) . ), ) _) _,')  (, ) '. )  ,. (' )
                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                    
                    """+Strings.of(StringId.FIREPLACE_LIT));
        super.prendiOggetto(oggetto);
    }

    /**
     * Un camino è usabile con un secchio per spegnerlo
     * @param secchio Secchio da usare, deve essere riempito
     */
    @Override
    public void usaCon(Secchio secchio) {
        if (secchio.isRiempito()) {
            spegni();
            secchio.svuota();
        }
    }

    /**
     * Controlla se due oggetti sono camini uguali
     * @param o Altro oggetto
     * @return <code>true</code> se l'oggetto è uguale al camino corrente
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Camino camino = (Camino) o;
        return isAcceso() == camino.isAcceso();
    }
}
