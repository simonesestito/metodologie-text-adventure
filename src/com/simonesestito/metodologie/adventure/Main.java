package com.simonesestito.metodologie.adventure;

import com.simonesestito.metodologie.adventure.entita.pojo.Giocatore;
import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;

import java.nio.file.Path;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        var file = Path.of("C:\\Users\\Simone\\Downloads\\minizak_v1.0.5\\minizak.game");
        var mondo = Mondo.fromFile(file);

        System.out.println("Benvenuto!");

        var input = Path.of("C:\\Users\\Simone\\Downloads\\minizak_v1.0.5\\minizak.ff");
        new Gioco().play(mondo, input);
    }
}
