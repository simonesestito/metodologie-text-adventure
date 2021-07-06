package it.uniroma1.textadv.easter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

/**
 * Funzioni di utilità per rendere i listener meno verbosi
 */
public class SwingUtils {
    /**
     * Crea un nuovo {@link MouseListener} con la sola azione per il click.
     *
     * @param onClick Azione da eseguire al click
     * @return Listener creato per il click
     */
    public static MouseListener getClickListener(Consumer<MouseListener> onClick) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.accept(this);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
}
