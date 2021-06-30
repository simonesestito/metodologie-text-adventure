package com.simonesestito.metodologie.adventure;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.engine.TextEngine;
import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Gioco {
    public void play(Mondo mondo) throws IOException, CommandException.Fatal {
        play(mondo, new BufferedReader(new InputStreamReader(System.in)), GameModeHandler.INTERACTIVE);
    }

    public void play(Mondo mondo, Path script) throws IOException, CommandException.Fatal {
        play(mondo, Files.newBufferedReader(script), GameModeHandler.NON_INTERACTIVE);
    }

    private void play(Mondo mondo, BufferedReader input, GameModeHandler handler) throws IOException, CommandException.Fatal {
        String line;
        while ((line = input.readLine()) != null) {
            try {
                handler.handleCommandEcho(line);
                new TextEngine().processInput(line).execute();
            } catch (CommandException.Fatal e) {
                handler.handleFatalError(e);
            } catch (CommandException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    private interface GameModeHandler {
        GameModeHandler INTERACTIVE = new GameModeHandler() {
            @Override
            public void handleCommandEcho(String command) {
                // Non fare nulla. Non ha senso scrivere il comando se sono già interattivo
            }

            @Override
            public void handleFatalError(CommandException.Fatal e) {
                // In modalità interattiva, dai solo output.
                System.out.println(e.getMessage());
            }
        };

        GameModeHandler NON_INTERACTIVE = new GameModeHandler() {
            @Override
            public void handleCommandEcho(String command) {
                // Fai l'echo del comando per far capire che succede
                System.out.println("> " + command);
            }

            @Override
            public void handleFatalError(CommandException.Fatal e) throws CommandException.Fatal {
                // In modalità non interattiva, interrompi il gioco.
                throw e;
            }
        };

        void handleCommandEcho(String command);

        void handleFatalError(CommandException.Fatal e) throws CommandException.Fatal;
    }
}
