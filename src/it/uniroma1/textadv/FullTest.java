package it.uniroma1.textadv;

import java.nio.file.Path;

/**
 * Classe per eseguire il programma eseguendo più cose del {@link Test} di base
 */
public class FullTest {
    /**
     * Esegui i vari test del gioco
     *
     * @param args Argomenti della CLI (inutilizzati)
     * @throws Exception Errore durante l'esecuzione
     */
    public static void main(String[] args) throws Exception {
        Gioco g = new Gioco();

        // Test sul mondo inglese e da FF
        Mondo mondoInglese = Mondo.fromFile("minizak.game", Gioco.Language.EN);
        g.localizza(Gioco.Language.EN);
        g.play(mondoInglese, Path.of(Gioco.Language.EN.getFilePrefix(), "minizak.ff"));

        // Torna in italiano FF
        g.localizza(Gioco.Language.IT);
        Mondo mondoItaliano = Mondo.fromFile("minizak.game");
        g.play(mondoItaliano, Path.of("minizak.ff"));
    }
}
