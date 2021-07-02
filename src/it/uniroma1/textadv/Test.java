package it.uniroma1.textadv;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        Gioco g = new Gioco();
        Mondo m = Mondo.fromFile("minizak.game");
        Path scriptDiGioco = Paths.get("minizak.ff");
        g.play(m, scriptDiGioco);
    }
}
