package it.uniroma1.textadv;

import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) throws Exception {
        Gioco g = new Gioco();
        Mondo m = Mondo.fromFile("minizak.game");
        g.localizza(Gioco.Lingua.IT);
        g.play(m, Paths.get("minizak.ff"));
    }
}
