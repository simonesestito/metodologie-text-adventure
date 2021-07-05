package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.entita.pojo.Entity;
import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.features.Contenitore;
import it.uniroma1.textadv.entita.pojo.features.ObservableObject;
import it.uniroma1.textadv.entita.pojo.features.Posizionabile;
import it.uniroma1.textadv.entita.pojo.features.Ricevitore;

import java.util.Objects;
import java.util.Set;

/**
 * Guardiano di un oggetto generico,
 * anche del {@link it.uniroma1.textadv.entita.pojo.objects.Tesoro} ma non necessariamente.
 */
public class Guardiano extends Personaggio implements Ricevitore<Entity, Object>, Contenitore {
    /**
     * Oggetto che preso dal guardiano, lo fa distrarre
     */
    private final Posizionabile distrazione;

    /**
     * Controlla se il guardiano è distratto
     */
    private boolean distratto;

    /**
     * Crea un nuovo guardiano
     *
     * @param name        Nome del guardiano
     * @param oggetto     Oggetto che deve proteggere
     * @param distrazione Entità che lo andrà a distrarre
     */
    public Guardiano(String name, ObservableObject oggetto, Posizionabile distrazione) {
        super(name);
        this.distrazione = distrazione;
        oggetto.observe(this::puoPrendereOggetto);
    }

    /**
     * Controlla se l'oggetto che il guardiano protegge può essere preso
     *
     * @return <code>true</code> se il guardiano lo consente
     */
    private boolean puoPrendereOggetto() {
        if (isDistratto()) {
            return true;
        }

        Giocatore.getInstance().rispondiUtente("""
                    |`.             /
                    | \\`.          / |
                    |  \\ \\.------./ '|
                    |  .'          / |
                    |  |             |
                    \\  |  ___ ' __  /
                     \\  \\ `0 ) /0 //
                      `--\\    v  /-
                        / \\ -  /
                      .--. '---'_
                     /      ./ //-
                    (`-.    .-'/- \\
                    /`- `--'    \\__|
                   /  ,          )_|
                  /    Y        -  (\s
                """);
        return false;
    }

    /**
     * Controlla se il guardiano è distratto
     *
     * @return <code>true</code> se è distratto
     */
    private boolean isDistratto() {
        return distratto;
    }

    /**
     * Ottieni gli oggetti tenuti dal guardiano, in questo caso può prendere l'entità distraente.
     *
     * @return Insieme con l'entità che lo distrae, se la ha in mano, o vuoto.
     */
    @Override
    public Set<? extends Posizionabile> getOggettiContenuti() {
        return distratto ? Set.of(distrazione) : Set.of();
    }

    /**
     * Dai al guardiano un oggetto. Se è quello che lo distrae, si distrarrà.
     *
     * @param oggetto Oggetto da dare al guardiano
     * @return Insieme vuoto, il guardiano riceve solo, non dà nulla.
     */
    @Override
    public Set<Object> ricevi(Entity oggetto) {
        distratto = Objects.equals(oggetto, distrazione);
        return Set.of(); // Il guardiano non restituisce alcun oggetto, è solo distratto
    }

    /**
     * Prende un oggetto. Se è quello che lo distrae, si distrarrà
     *
     * @param oggetto Oggetto da prendere
     */
    @Override
    public void prendiOggetto(Posizionabile oggetto) {
        ricevi((Entity) oggetto);
    }

    /**
     * Verifica se due guardiani sono uguali
     *
     * @param o Altro oggetto da controllare
     * @return <code>true</code> se i due guardiani sono uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Guardiano guardiano = (Guardiano) o;
        return isDistratto() == guardiano.isDistratto() && Objects.equals(distrazione, guardiano.distrazione);
    }

    /**
     * Calcola l'hash dell'oggetto
     *
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), distrazione, isDistratto());
    }
}
