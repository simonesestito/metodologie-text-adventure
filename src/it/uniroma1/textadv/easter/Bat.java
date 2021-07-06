package it.uniroma1.textadv.easter;

/**
 * Elemento del gioco {@link Dino} che rappresenta un pipistrello
 */
public class Bat extends MovingObject {
    /**
     * "Immagine" del pipistrello
     */
    private static final String CACTUS_ASCII_ART = """
               _   ,_,   _
              / `'=) (='` \\
             /.-.-.\\ /.-.-.\\\s
             `      "      `
            """;

    /**
     * Posizione Y del pipistrello
     * @return Coordinata Y
     */
    @Override
    protected int getY() {
        return 23;
    }

    /**
     * Ottieni il disegno del pipistrello come stringa ASCII art
     * @return Disegno del pipistrello
     */
    @Override
    protected String getDrawing() {
        return CACTUS_ASCII_ART;
    }
}
