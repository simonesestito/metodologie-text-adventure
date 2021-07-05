package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.features.ContenitoreAggiungibile;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Inventario contenente oggetti raccolti dai personaggi
 */
public class Inventario implements ContenitoreAggiungibile {
    /**
     * Insieme sottostante l'inventario
     */
    private final Set<Posizionabile> inventario = new HashSet<>();

    /**
     * Ottieni una vista immutabile degli oggetti nell'inventario
     * @return Oggetti nell'inventario
     */
    @Override
    public Set<Posizionabile> getOggettiContenuti() {
        return Collections.unmodifiableSet(inventario);
    }

    /**
     * Prendi un oggetto da dentro l'inventario
     * @param oggetto Oggetto da prendere
     * @throws CommandException Errore durante lo spostamento dell'oggetto
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!inventario.contains(oggetto))
            throw new UnresolvedEntityException(oggetto, this);
        inventario.remove(oggetto);
    }

    /**
     * Aggiungi un oggetto nell'inventario
     * @param oggetto Oggetto da aggiungere
     * @throws CommandException Errore durante l'aggiunta
     */
    @Override
    public void aggiungiOggetto(Posizionabile oggetto) throws CommandException {
        oggetto.spostaIn(this);
        inventario.add(oggetto);
    }

    /**
     * Verifica che l'inventario sia vuoto
     * @return <code>true</code> se l'inventario è vuoto
     */
    public boolean isEmpty() {
        return inventario.isEmpty();
    }

    /**
     * Ottieni uno stream sugli oggetti contenuti nell'inventario
     * @return Stream degli oggetti contenuti
     */
    public Stream<Posizionabile> stream() {
        return inventario.stream();
    }

    /**
     * Rappresenta l'inventario come stringa tramite il suo nome tradotto
     * @return Nome dell'inventario
     */
    @Override
    public String toString() {
        return Strings.of(StringId.INVENTARY_NAME);
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due inventari uguali
     * @param o Altro oggetto
     * @return <code>true</code> se sono due inventari uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventario that = (Inventario) o;
        return Objects.equals(inventario, that.inventario);
    }

    /**
     * Calcola l'hash dell'inventario
     * @return Hash calcolato
     */
    @Override
    public int hashCode() {
        return Objects.hash(inventario);
    }
}
