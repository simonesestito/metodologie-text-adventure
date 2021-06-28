package com.simonesestito.metodologie.adventure.engine;

import com.simonesestito.metodologie.adventure.ReflectionUtils;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;
import com.simonesestito.metodologie.adventure.entita.pojo.player.Giocatore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Command {
    private final String literalPrefix;
    private final Method mappingMethod;

    private Command(String literalPrefix, Method mappingMethod) {
        this.literalPrefix = literalPrefix;
        this.mappingMethod = mappingMethod;
    }

    public static Command of(String commandLine) {
        var commandParts = commandLine.split(",");
        return new Command(
                commandParts[0],
                Arrays.stream(Giocatore.class.getMethods())
                        .filter(m -> m.getName().equals(commandParts[1]))
                        .filter(m -> m.getParameterCount() == commandParts.length - 2)
                        .findFirst()
                        // Unrecoverable exception
                        .orElseThrow(() -> new RuntimeException("Comando non trovato, verifica la correttezza del file"))
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

    public void execute(List<?> arguments) throws TextEngine.CommandException {
        try {
            mappingMethod.invoke(Giocatore.getInstance(), arguments.toArray(new Object[0]));
        } catch (InvocationTargetException e) {
            if (e.getCause() != null && e.getCause() instanceof TextEngine.CommandException) {
                throw (TextEngine.CommandException) e.getCause();
            }
        } catch (ReflectiveOperationException e) {
            // TODO
            e.printStackTrace();
        }
    }
}
