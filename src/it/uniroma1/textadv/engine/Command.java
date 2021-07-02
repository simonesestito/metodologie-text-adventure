package it.uniroma1.textadv.engine;

import it.uniroma1.textadv.ReflectionUtils;
import it.uniroma1.textadv.entita.pojo.Giocatore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Command {
    public final static Command EMPTY = new Command("", null) {
        @Override
        public void execute(List<?> arguments) { /* Empty */ }
    };

    private final String literalPrefix;
    private final Method mappingMethod;

    private Command(String literalPrefix, Method mappingMethod) {
        this.literalPrefix = literalPrefix;
        this.mappingMethod = mappingMethod;
    }

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

    public String getLiteralPrefix() {
        return literalPrefix;
    }

    public boolean matchArguments(List<?> arguments) {
        return ReflectionUtils.matchesTypes(
                mappingMethod.getParameterTypes(),
                arguments.stream().map(Object::getClass).toList()
        );
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(getLiteralPrefix(), command.getLiteralPrefix()) && Objects.equals(mappingMethod, command.mappingMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLiteralPrefix(), mappingMethod);
    }

    @Override
    public String toString() {
        return mappingMethod.toString();
    }

    public static class FileCommandException extends TextEngine.CommandNotFoundException {
        public FileCommandException(String commandLine) {
            super("Comando non trovato, verifica la correttezza del file: " + commandLine);
        }
    }
}
