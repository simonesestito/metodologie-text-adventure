package it.uniroma1.textadv;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        Gioco g = new Gioco();
        Mondo m = Mondo.fromFile("en/minizak.game");
        Path scriptDiGioco = Paths.get("en/minizak.ff");
        g.localizza(Gioco.Lingua.EN);
        g.play(m, scriptDiGioco);
    }
}
