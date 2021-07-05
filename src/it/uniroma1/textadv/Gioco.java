package it.uniroma1.textadv;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.engine.TextEngine;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;
import it.uniroma1.textadv.utils.Lazy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Gioco {
    public static final Lingua DEFAULT_LANGUAGE = Lingua.IT;
    public static final String CLI_INPUT_PREFIX = "> ";
    private Lazy<TextEngine, IOException> textEngine;

    public enum Lingua {
        EN("en/"),
        IT("");

        private final String filePrefix;

        Lingua(String filePrefix) {
            this.filePrefix = filePrefix;
        }

        public String getFilePrefix() {
            return filePrefix;
        }
    }

    {
        try {
            localizza(DEFAULT_LANGUAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(Mondo mondo) {
        try {
            play(mondo, new BufferedReader(new InputStreamReader(System.in)), GameModeHandler.INTERACTIVE);
        } catch (IOException | CommandException.Fatal e) {
            e.printStackTrace();
        }
    }

    public void play(Mondo mondo, Path script) {
        try {
            play(mondo, Files.newBufferedReader(script), GameModeHandler.NON_INTERACTIVE);
        } catch (IOException | CommandException.Fatal e) {
            e.printStackTrace();
        }
    }

    private void play(Mondo mondo, BufferedReader input, GameModeHandler handler) throws IOException, CommandException.Fatal {
        System.out.println(mondo);

        String line;
        System.out.print(CLI_INPUT_PREFIX);
        while ((line = input.readLine()) != null) {
            try {
                handler.handleCommandEcho(line);
                textEngine.get().processInput(line).execute();
            } catch (GameOverException e) {
                System.out.println(e.getMessage());
                return;
            } catch (CommandException.Fatal e) {
                handler.handleFatalError(e);
            } catch (CommandException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
            System.out.print(CLI_INPUT_PREFIX);
        }
    }

    public void localizza(Lingua lingua) throws IOException {
        Strings.localizza(lingua);

        textEngine = new Lazy<>(
                () -> new TextEngine.Builder()
                        .setCommandsFile(lingua.getFilePrefix() + TextEngine.COMMANDS_FILENAME)
                        .setStopWordsFile(lingua.getFilePrefix() + TextEngine.STOPWORDS_FILENAME)
                        .build()
        );
        textEngine.get();
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
                System.out.println(command);
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

    public static class GameOverException extends CommandException {
        public GameOverException() {
            super("""
                    
                    -----------------------------------------------------
                       _                             .-.
                      / )  .-.    ___          __   (   )
                     ( (  (   ) .'___)        (__'-._) (
                      \\ '._) (,'.'               '.     '-.
                       '.      /  "\\               '    -. '.
                         )    /   \\ \\   .-.   ,'.   )  (  ',_)    _
                       .'    (     \\ \\ (   \\ . .' .'    )    .-. ( \\
                      (  .''. '.    \\ \\|  .' .' ,',--, /    (   ) ) )
                       \\ \\   ', :    \\    .-'  ( (  ( (     _) (,' /
                        \\ \\   : :    )  / _     ' .  \\ \\  ,'      /
                      ,' ,'   : ;   /  /,' '.   /.'  / / ( (\\    (
                      '.'      "   (    .-'. \\       ''   \\_)\\    \\
                                    \\  |    \\ \\__             )    )
                                  ___\\ |     \\___;           /  , /
                                 /  ___)                    (  ( (
                      PN         '.'                         ) ;) ;
                                                            (_/(_/
                    ----------------------------------------------------
                    
                    
                    """ + Strings.of(StringId.GAME_OVER));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gioco gioco = (Gioco) o;
        return Objects.equals(textEngine, gioco.textEngine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textEngine);
    }
}
