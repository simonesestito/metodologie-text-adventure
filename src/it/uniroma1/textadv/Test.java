package it.uniroma1.textadv;

import it.uniroma1.textadv.entity.pojo.characters.Giocatore;

import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) throws Exception {
        Gioco g = new Gioco();
        Mondo m = Mondo.fromFile("minizak.game");
        g.localizza(Gioco.Lingua.IT);
        Giocatore.getInstance().dormi();
        g.play(m, Paths.get("minizak.ff"));
    }
}
