package com.simonesestito.metodologie.adventure;

import com.simonesestito.metodologie.adventure.entita.pojo.Giocatore;
import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        var file = Path.of("C:\\Users\\Simone\\Downloads\\minizak_v1.0.5\\minizak.game");
        var mondo = Mondo.fromFile(file);
        System.out.println(mondo.getStart().getDescription());
        System.out.println(Giocatore.getInstance().getName());

        System.out.println("\n\nBenvenuto!");

        while (true) {
            System.out.println("Ora sono a " + Giocatore.getInstance().getCurrentLocation().getName());
            System.out.print("> ");
            new Gioco().play(mondo);
        }
    }
}
