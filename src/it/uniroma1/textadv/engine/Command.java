package it.uniroma1.textadv.engine;

import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.locale.StringId;
import it.uniroma1.textadv.locale.Strings;
import it.uniroma1.textadv.utils.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Implementa il Command pattern dove un oggetto di questa classe rappresenta
 * il "cosa fare", derivante dal comando testuale dato.
 */
public class Command {
    /**
     * Istanza predefinita per un comando vuoto.
     */
    public final static Command EMPTY = new Command("", null) {
        @Override
        public void execute(List<?> arguments) { /* Empty */ }
    };

    /**
     * Separatore per le parti dei comandi nel file
     */
    public final static String COMMAND_PART_SEPARATOR = ",";

    /**
     * Prefisso letterale del comando (vai, dai, ...)
     */
    private final String literalPrefix;

    /**
     * Corrispondente metodo che deve essere richiamato via reflection
     */
    private final Method mappingMethod;

    /**
     * Costruttore privato del comando. Creabile esternamente da factory method
     *
     * @param literalPrefix Prefisso del comando, può essere vuoto
     * @param mappingMethod Metodo da richiamare
     * @see Command#of(String)
     */
    private Command(String literalPrefix, Method mappingMethod) {
        this.literalPrefix = literalPrefix;
        this.mappingMethod = mappingMethod;
    }

    /**
     * Static factory method per la creazione del comando da testo (file dei comandi)
     *
     * @param commandLine Testo inserito da cui ricavare il comando
     * @return Comando corrispondente all'istruzione data
     * @throws FileCommandException In caso il file dei comandi sia errato (quindi l'input dato)
     */
    public static Command of(String commandLine) throws FileCommandException {
        if (commandLine.isEmpty())
            return EMPTY;

        var commandParts = commandLine.split(",");
        return new Command(
                commandParts[0],
                Arrays.stream(Giocatore.class.getMethods())
                        .filter(m -> m.getName().equals(commandParts[1]))
                        .filter(m -> m.getParameterCount() == commandParts.length - 2)
                        .findFirst()
                        .orElseThrow(() -> new FileCommandException(commandLine))
        );
    }

    /**
     * Controlla che il metodo corrispondente faccia match con i parametri desiderati
     *
     * @param arguments Parametri attuali da voler passare
     * @return <code>true</code> se i parametri sono accettati
     */
    public boolean matchArguments(List<?> arguments) {
        return ReflectionUtils.matchesTypes(
                mappingMethod.getParameterTypes(),
                arguments.stream().map(Object::getClass).toList()
        );
    }

    /**
     * Esegui i comandi con i parametri passati in input.
     * <p>
     * Controllare se i parametri sono accettati tramite {@link Command#matchArguments(List)}
     *
     * @param arguments Parametri da passare in esecuzione al comando
     * @throws CommandException In caso logici di errori sul comando stesso
     */
    public void execute(List<?> arguments) throws CommandException {
        try {
            mappingMethod.invoke(Giocatore.getInstance(), arguments.toArray(new Object[0]));
        } catch (InvocationTargetException e) {
            if (e.getCause() != null && e.getCause() instanceof CommandException) {
                throw (CommandException) e.getCause();
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ottieni il prefisso letterale del comando
     *
     * @return Prefisso del comando
     */
    public String getLiteralPrefix() {
        return literalPrefix;
    }

    /**
     * Controlla che due istanze rappresentino la stessa cosa, logicamente
     *
     * @param o Altro oggetto da verificare
     * @return <code>true</code> se i due oggetti sono logicamente uguali
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(mappingMethod, command.mappingMethod);
    }

    /**
     * Calcola l'hash dell'oggetto
     *
     * @return Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(mappingMethod);
    }

    /**
     * Ottieni una rappresentazione come stringa del metodo corrente
     *
     * @return Descrizione del metodo
     */
    @Override
    public String toString() {
        return mappingMethod.toString();
    }

    /**
     * Eccezione per gli errori del file dei comandi
     */
    public static class FileCommandException extends IOException {
        /**
         * Crea un errore sul file dei comandi, per un dato comando
         *
         * @param commandLine Comando che ha generato l'errore
         */
        public FileCommandException(String commandLine) {
            super(Strings.of(StringId.COMMAND_NOT_FOUND, commandLine));
        }
    }
}
