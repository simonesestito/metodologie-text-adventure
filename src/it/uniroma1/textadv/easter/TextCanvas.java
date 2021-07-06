package it.uniroma1.textadv.easter;

import java.util.Arrays;

/**
 * Canvas dove tutti gli elementi {@link Drawable} andranno a disegnare testualmente.
 * <p>
 * La superficie di lavoro è una matrice di caratteri.
 */
public class TextCanvas {
    /**
     * Matrice di caratteri che è "buffer" dell'immagine in un'idea di pixel
     */
    private final char[][] canvas;

    /**
     * Larghezza del canvas
     */
    private final int width;

    /**
     * Altezza del canvas
     */
    private final int height;

    /**
     * Crea un nuovo canvas testuale
     *
     * @param width  Larghezza
     * @param height Altezza
     */
    public TextCanvas(int width, int height) {
        this.canvas = new char[height][width];
        this.width = width;
        this.height = height;
    }

    /**
     * Ottieni la larghezza del canvas
     *
     * @return Larghezza del canvas
     */
    public int getWidth() {
        return width;
    }

    /**
     * Ottieni l'altezza del canvas
     *
     * @return Altezza del canvas
     */
    public int getHeight() {
        return height;
    }

    /**
     * Disegna un disegno testuale sul canvas, stando attento alle collisioni
     *
     * @param multiLine "Immagine" da disegnare
     * @param x         Coordinata X dove disegnare
     * @param y         Coordinata Y dove disegnare
     * @throws CollisionException Collisione rilevata con un altro elemento
     */
    public void draw(String multiLine, int x, int y) throws CollisionException {
        var lines = multiLine.split("\n");
        var width = Arrays.stream(lines)
                .mapToInt(String::length)
                .max()
                .orElse(0);
        var height = (int) multiLine.lines().count();

        var drawing = new char[height][width];
        for (int i = 0; i < height; i++) {
            var line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                drawing[i][j] = line.charAt(j);
            }
        }
        draw(drawing, x, y);
    }

    /**
     * Disegna un disegno testuale sul canvas, stando attento alle collisioni
     *
     * @param drawing "Immagine" da disegnare già come matrice
     * @param x       Coordinata X dove disegnare
     * @param y       Coordinata Y dove disegnare
     * @throws CollisionException Collisione rilevata con un altro elemento
     */
    public void draw(char[][] drawing, int x, int y) throws CollisionException {
        for (int i = 0; i < drawing.length; i++) {
            var line = drawing[drawing.length - i - 1];
            for (int j = 0; j < line.length; j++) {
                int column = j + x, row = getHeight() - i - y - 1;

                if (column >= getWidth() || row >= getHeight() || column < 0 || row < 0)
                    continue;

                if (canvas[row][column] != '\0')
                    throw new CollisionException();

                canvas[row][column] = line[j];
            }
        }
    }

    /**
     * Ottieni il disegno finale come stringa, ricopiando il buffer del frame
     *
     * @return Disegno finalizzato come stringa
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (char[] line : canvas) {
            for (char c : line) {
                string.append(c == '\0' ? ' ' : c);
            }
            string.append('\n');
        }
        return string.toString();
    }

    /**
     * Eccezione in caso di collisione rilevata
     */
    static class CollisionException extends Exception {
    }
}
