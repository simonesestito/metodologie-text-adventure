package com.simonesestito.metodologie.adventure;

import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Gioco {
    public void play(Mondo mondo) throws IOException {
        play(mondo, new BufferedReader(new InputStreamReader(System.in)));
    }

    public void play(Mondo mondo, Path script) throws IOException {
        play(mondo, Files.newBufferedReader(script));
    }

    public void play(Mondo mondo, BufferedReader input) throws IOException {
        // TODO
        var line = input.readLine();
        try {
            new TextEngine().processInput(line).execute(mondo);
        } catch (TextEngine.CommandException e) {
            System.out.println(e.getMessage());
        }
    }
}
