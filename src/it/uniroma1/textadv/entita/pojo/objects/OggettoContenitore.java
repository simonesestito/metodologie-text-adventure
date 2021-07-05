package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.engine.EntityResolver;
import it.uniroma1.textadv.entita.pojo.features.ApribileCon;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Oggetto in grado di contenere altri oggetti al suo interno
 */
public abstract class OggettoContenitore extends Oggetto implements Contenitore {
    /**
     * Contenuto dell'oggetto
     */
    protected final Set<Posizionabile> contenuto;

    /**
     * Crea un nuovo contenitore
     * @param name Nome del contenitore
     * @param contenuto Suo contenuto
     */
    public OggettoContenitore(String name, List<? extends Oggetto> contenuto) {
        super(name);
        this.contenuto = new HashSet<>(contenuto);
        for (var oggetto : contenuto) {
            try {
                oggetto.spostaIn(this);
            } catch (CommandException ignored) {}
        }
    }

    /**
     * Ottieni una vista immutabile del contenuto dell'oggetto
     * @return Contenuto dell'oggetto
     */
    @Override
    public Set<Posizionabile> getOggettiContenuti() {
        return Collections.unmodifiableSet(contenuto);
    }

    /**
     * Prendi un oggetto dal suo interno
     * @param oggetto Oggetto da prendere
     * @throws CommandException Errore nella presa
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) throws CommandException {
        if (!contenuto.contains(oggetto))
            throw new UnresolvedEntityException(oggetto, this);
        contenuto.remove(oggetto);
    }

    /**
     * Verifica che il contenuto all'interno debba essere nascosto all'utente
     * @return <code>true</code> se il contenuto è nascosto
     * @see OggettoContenitore#toString()
     */
    protected boolean isContentHidden() {
        return this instanceof ApribileCon<?> && !((ApribileCon<?>) this).isAperto();
    }

    /**
     * Rappresentazione come stringa dell'oggetto e del suo contenuto, se visibile
     * @return Rappresentazione dell'oggetto come stringa
     * @see OggettoContenitore#isContentHidden()
     */
    @Override
    public String toString() {
        String contentDescription;
        if (isContentHidden()) {
            contentDescription = Strings.of(StringId.CONTAINER_HAS_HIDDEN_CONTENT);
        } else if (getOggettiContenuti().isEmpty()) {
            contentDescription = Strings.of(StringId.CONTAINER_HAS_NO_CONTENT);
        } else {
            var objects = getOggettiContenuti()
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));
            contentDescription = Strings.of(StringId.CONTAINER_HAS_VISIBLE_CONTENT, objects);
        }
        return super.toString() + ", " + contentDescription;
    }

    /**
     * Controlla che due oggetti siano contenitori e uguali
     * @param o Altro oggetto
     * @return <code>true</code> se i due oggetti sono contenitori uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OggettoContenitore that = (OggettoContenitore) o;
        return Objects.equals(contenuto, that.contenuto);
    }

    /**
     * Calcola l'hash dell'oggetto e del suo contenuto
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contenuto);
    }
}
