package it.uniroma1.textadv;

/**
 * Classe dei test di base dalle specifiche
 */
public class Test {
    /**
     * Esegui il test di base per eseguire il gioco in interattivo
     *
     * @param args Argomenti della CLI inutilizzati
     * @throws Exception Errore durante l'esecuzione
     */
    public static void main(String[] args) throws Exception {
        Gioco g = new Gioco();
        Mondo m = Mondo.fromFile("minizak.game");
        g.play(m);
    }
}
