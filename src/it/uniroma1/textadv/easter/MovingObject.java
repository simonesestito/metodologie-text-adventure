package it.uniroma1.textadv.easter;

/**
 * Elemento di gioco generico che si muove col tempo
 */
public abstract class MovingObject implements Drawable {
    /**
     * Spostamento sulla coordinata X per ogni frame
     */
    private static final int X_AXIS_INCREMENT = 1;

    /**
     * Posizione attuale dell'elemento sulla coordinata X
     */
    private int x;

    /**
     * Ottieni il disegno dell'elemento di gioco
     *
     * @return Disegno dell'elemento
     */
    protected abstract String getDrawing();

    /**
     * Posizione dell'elemento sulla coordinata Y
     *
     * @return Coordinata Y
     */
    protected abstract int getY();

    /**
     * Disegna l'elemento di gioco sul canvas
     *
     * @param canvas Canvas dove disegnare
     * @throws TextCanvas.CollisionException Collisione rilevata con un altro elemento
     */
    @Override
    public void draw(TextCanvas canvas) throws TextCanvas.CollisionException {
        canvas.draw(getDrawing(), canvas.getWidth() - x, getY());
    }

    /**
     * Sposta l'elemento della dimensione prefissata al prossimo frame
     */
    @Override
    public void onClock() {
        x += X_AXIS_INCREMENT;
    }
}
