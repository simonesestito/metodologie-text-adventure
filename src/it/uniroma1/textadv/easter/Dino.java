package it.uniroma1.textadv.easter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Gioco easter egg ispirato a Dino di Chrome
 */
public class Dino {
    /**
     * Larghezza della finestra di gioco
     */
    public final static int WINDOW_WIDTH = 1000;

    /**
     * Altezza della finestra di gioco
     */
    public final static int WINDOW_HEIGHT = 680;

    /**
     * Dimensione del font che compongono i disegni in ASCII art
     */
    public final static int FONT_SIZE = 15;

    /**
     * Millisecondi per il refresh, tempo di clock tra un frame e l'altro
     */
    public final static int CLOCK_TIMING = 64;

    /**
     * Lista con tutti gli elementi di gioco
     */
    private final List<Drawable> drawableList = new LinkedList<>();

    /**
     * Esecutore dei task periodici (es: redraw)
     */
    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(0);

    /**
     * Esecutore del salto sul personaggio, a ripetizione finchè il tasto non viene rilasciato
     */
    private final ExecutorService jumpExecutor = Executors.newSingleThreadExecutor();

    /**
     * Indicatore della pressione di un qualche tasto in questo momento
     */
    private final AtomicBoolean keyPressed = new AtomicBoolean(false);

    /**
     * Personaggio del gioco
     */
    private final Man man = new Man();

    /**
     * Area di disegno, ovvero area di testo dove viene stampato l'output del {@link TextCanvas}
     */
    private final JTextArea textArea = new JTextArea();

    /**
     * Frame della finestra di gioco
     */
    private final JFrame frame = new JFrame();

    /**
     * Crea un nuovo gioco
     */
    public Dino() {
        this.drawableList.add(man);
    }

    /**
     * Avvia il gioco in una finestra
     *
     * @throws InterruptedException In caso il thread di gioco venga interrotto
     */
    public void avvia() throws InterruptedException {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.add(textArea);
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed.set(true);
                jumpExecutor.execute(() -> {
                    while (keyPressed.get())
                        man.jump();
                });
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyPressed.set(false);
            }
        });
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE));
        frame.setVisible(true);

        scheduledExecutor.scheduleAtFixedRate(() -> {
            try {
                draw();
            } catch (TextCanvas.CollisionException e) {
                stop();
            }
        }, 0, CLOCK_TIMING, TimeUnit.MILLISECONDS);

        scheduledExecutor.scheduleAtFixedRate(
                () -> {
                    if (Math.random() > 0.5) drawableList.add(new Cactus());
                    if (Math.random() > 0.8) drawableList.add(new Bat());
                },
                CLOCK_TIMING,
                CLOCK_TIMING * 20,
                TimeUnit.MILLISECONDS
        );

        //noinspection ResultOfMethodCallIgnored
        scheduledExecutor.awaitTermination(1, TimeUnit.HOURS);
    }

    /**
     * Termina il gioco e chiudi la finestra
     */
    private synchronized void stop() {
        scheduledExecutor.shutdownNow();
        jumpExecutor.shutdown();

        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
    }

    /**
     * Aggiorna il "disegno" testuale sul {@link TextCanvas} e riportalo sull'area di testo
     *
     * @throws TextCanvas.CollisionException Collisione tra elementi di gioco
     */
    private void draw() throws TextCanvas.CollisionException {
        var canvas = new TextCanvas(WINDOW_WIDTH / 8, WINDOW_HEIGHT / 22);
        for (var drawable : drawableList) {
            drawable.onClock();
            drawable.draw(canvas);
        }

        textArea.setText(canvas.toString());
    }
}
