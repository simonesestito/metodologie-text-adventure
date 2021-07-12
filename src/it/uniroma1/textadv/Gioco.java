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

/**
 * Rappresentazione del gioco principale
 */
public class Gioco {
    /**
     * Lingua di default
     */
    public static final Language DEFAULT_LANGUAGE = Language.IT;

    /**
     * Prefisso da stampare per accettare un input utente
     */
    public static final String CLI_INPUT_PREFIX = "> ";

    /**
     * Text engine che verrà caricato lazily
     */
    private Lazy<TextEngine, IOException> textEngine;

    {
        try {
            localizza(DEFAULT_LANGUAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gioca in un mondo dato, prendendo l'input da {@link System#in} in modalità interattiva
     *
     * @param mondo Mondo da giocare
     * @throws IOException Errore di lettura dell'input utente
     */
    public void play(Mondo mondo) throws IOException {
        try {
            play(mondo, new BufferedReader(new InputStreamReader(System.in)), GameModeHandler.INTERACTIVE);
        } catch (CommandException.Fatal e) {
            e.printStackTrace();
        }
    }

    /**
     * Gioca in un mondo dato, prendendo l'input da file in modalità non interattiva (fast-forward)
     *
     * @param mondo  Mondo da giocare
     * @param script File da cui prendere i comandi
     * @throws IOException            Errore di lettura del file dell'input utente
     * @throws CommandException.Fatal Errore fatale per il comando dato, che interrompe l'esecuzione non interattiva
     */
    public void play(Mondo mondo, Path script) throws IOException, CommandException.Fatal {
        play(mondo, Files.newBufferedReader(script), GameModeHandler.NON_INTERACTIVE);
    }

    /**
     * Gioca in un mondo, prendendo i comandi da un input generico
     *
     * @param mondo   Mondo da giocare
     * @param input   Fonte dei comandi
     * @param handler Strategy pattern applicato alle situazioni di gioco interattivo/non interattivo
     * @throws IOException            Errore nella lettura dei file
     * @throws CommandException.Fatal Errore di tipo fatale nel senso del fast-forward
     */
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

    /**
     * Localizza il gioco intero cambiando la lingua ovunque
     *
     * @param language Lingua da usare
     * @throws IOException Errore nella lettura dei file di lingua
     */
    public void localizza(Language language) throws IOException {
        Strings.localizza(language);

        textEngine = new Lazy<>(
                () -> new TextEngine.Builder()
                        .setCommandsFile(language.getFilePrefix() + TextEngine.COMMANDS_FILENAME)
                        .setStopWordsFile(language.getFilePrefix() + TextEngine.STOPWORDS_FILENAME)
                        .build()
        );
        textEngine.get();
    }

    /**
     * Controlla se l'oggetto corrente e quello dato sono due giochi uguali
     *
     * @param o Altro oggetto
     * @return <code>true</code> se sono due giochi uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gioco gioco = (Gioco) o;
        return Objects.equals(textEngine, gioco.textEngine);
    }

    /**
     * Calcola l'hash del gioco
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(textEngine);
    }

    /**
     * Lingua del gioco
     */
    public enum Language {
        /**
         * Inglese
         */
        EN("en/"),

        /**
         * Italiano
         */
        IT("");

        /**
         * Prefisso della cartella dove trovare i file
         */
        private final String filePrefix;

        /**
         * Crea una lingua dell'enum con il prefisso per le cartelle
         *
         * @param filePrefix Prefisso dei file
         */
        Language(String filePrefix) {
            this.filePrefix = filePrefix;
        }

        /**
         * Ottieni il prefisso per tutti i file in questa lingua
         *
         * @return Prefisso della lingua
         */
        public String getFilePrefix() {
            return filePrefix;
        }
    }

    /**
     * Strategy pattern applicato alle diverse situazioni di gioco,
     * interattivo o fast-forward
     */
    private interface GameModeHandler {
        /**
         * Implementazione dei comportamenti in modalità interattiva
         */
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

        /**
         * Implementazione dei comportamenti in modalità fast-forward
         */
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

        /**
         * Gestisci l'output di un comando ricevuto
         *
         * @param command Comando utente
         */
        void handleCommandEcho(String command);

        /**
         * Gestisci la situazione di errore considerata grave
         *
         * @param e Errore accaduto
         * @throws CommandException.Fatal In caso serva rilanciare lo stesso errore
         */
        void handleFatalError(CommandException.Fatal e) throws CommandException.Fatal;
    }

    /**
     * Eccezione che causa la fine del gioco
     */
    public static class GameOverException extends CommandException {
        /**
         * Crea l'eccezione con il messaggio di default
         */
        public GameOverException() {
            super("""
                    *******************************************************************************
                               |                   |                  |                     |
                      _________|________________.=""_;=.______________|_____________________|_______
                     |                   |  ,-"_,=""     `"=.|                  |
                     |___________________|__"=._o`"-._        `"=.______________|___________________
                               |                `"=._o`"=._      _`"=._                     |
                      _________|_____________________:=._o "=._."_.-="'"=.__________________|_______
                     |                   |    __.--" , ; `"=._o." ,-""\"-._ ".   |
                     |___________________|_._"  ,. .` ` `` ,  `"-._"-._   ". '__|___________________
                               |           |o`"=._` , "` `; .". ,  "-._"-._; ;              |
                      _________|___________| ;`-.o`"=._; ." ` '`."\\` . "-._ /_______________|_______
                     |                   | |o;    `"-.o`"=._``  '` " ,__.--o;   |
                     |___________________|_| ;     (#) `-.o `"=.`_.--"_o.-; ;___|___________________
                     ____/______/______/___|o;._    "      `".o|o_.--"    ;o;____/______/______/____
                     /______/______/______/_"=._o--._        ; | ;        ; ;/______/______/______/_
                     ____/______/______/______/__"=._o--._   ;o|o;     _._;o;____/______/______/____
                     /______/______/______/______/____"=._o._; | ;_.--"o.--"_/______/______/______/_
                     ____/______/______/______/______/_____"=.o|o_.--""___/______/______/______/____
                     /______/______/______/______/______/______/______/______/______/______/______/_
                     *******************************************************************************
                     
                    """ + Strings.of(StringId.GAME_OVER));
        }
    }
}
