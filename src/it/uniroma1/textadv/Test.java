package it.uniroma1.textadv;

import it.uniroma1.textadv.entity.parser.GameFile;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, GameFile.ParseException {
        Gioco g = new Gioco();
        Mondo m = Mondo.fromFile("minizak.game");
        g.localizza(Gioco.Lingua.IT);
        g.play(m); //, Paths.get("minizak.ff"));
    }
}
