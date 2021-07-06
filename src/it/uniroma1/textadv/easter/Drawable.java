package it.uniroma1.textadv.easter;

/**
 * Elemento disegnabile su un canvas
 */
public interface Drawable {
    /**
     * Disegna l'elemento sul canvas dato
     * @param canvas Canvas dove disegnare
     * @throws TextCanvas.CollisionException In caso si rilevi una collisione nel disegno
     */
    void draw(TextCanvas canvas) throws TextCanvas.CollisionException;

    /**
     * Aggiorna lo stato dell'elemento al prossimo frame
     */
    default void onClock() {}
}
