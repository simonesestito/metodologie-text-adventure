package it.uniroma1.textadv.easter;

import java.util.concurrent.atomic.AtomicBoolean;
import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * Omino del gioco {@link Dino}
 */
public class Man implements Drawable {
    /**
     * Minima coordinata Y dell'omino
     */
    private static final int MIN_Y = 0;

    /**
     * Disegno dell'omino come stringa
     */
    private static final String MAN_ASCII_ART = """
               _
             _(")_
            (_ . _)
             / : \\
            (_/ \\_)
            """;
    /**
     * Indicatore se è richiesto il salto al prossimo frame
     */
    private final AtomicBoolean hasToJump = new AtomicBoolean(false);

    /**
     * Posizione corrente della Y dell'omino
     */
    private int y = MIN_Y;

    /**
     * Disegna l'omino sul canvas
     * @param canvas Canvas dove disegnare
     * @throws TextCanvas.CollisionException Collisione rilevata con un altro elemento
     */
    @Override
    public void draw(TextCanvas canvas) throws TextCanvas.CollisionException {
        y = (int) min(y, canvas.getHeight() - MAN_ASCII_ART.lines().count() - 1);
        canvas.draw(MAN_ASCII_ART, 0, max(y, MIN_Y));
    }

    /**
     * Aggiorna la posizione dell'omino al prossimo frame
     */
    @Override
    public void onClock() {
        if (hasToJump.getAndSet(false)) {
            y++;
        } else if (y > MIN_Y) {
            y--;
        }
    }

    /**
     * Richiedi il salto al prossimo frame
     */
    public void jump() {
        hasToJump.set(true);
    }
}
