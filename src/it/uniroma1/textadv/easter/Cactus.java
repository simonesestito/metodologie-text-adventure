package it.uniroma1.textadv.easter;

/**
 * Elemento del gioco {@link DinoGame} che rappresenta un cactus
 */
public class Cactus extends MovingObject {
    /**
     * "Immagine" del cactus
     */
    private static final String CACTUS_ASCII_ART = """
              _  _
             | || | _
            -| || || |
             | || || |-
              \\_  || |
                |  _/
               -| | \\
                |_|-
            """;

    /**
     * Ottieni il disegno del cactus come stringa ASCII art
     *
     * @return Disegno del cactus
     */
    @Override
    protected String getDrawing() {
        return CACTUS_ASCII_ART;
    }

    /**
     * Posizione Y del cactus
     *
     * @return Coordinata Y
     */
    @Override
    protected int getY() {
        return 0;
    }
}
