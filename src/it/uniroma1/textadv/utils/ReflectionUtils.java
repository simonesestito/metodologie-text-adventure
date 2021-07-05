package it.uniroma1.textadv.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Classe di utility sulle reflection.
 * <p>
 * Implementa funzionalità usate nel progetto e verbose da scrivere nelle varie classi che le usano.
 * <p>
 * Inoltre, in caso di uso in più punti indipendenti tra loro, la classe evita duplicazione del codice.
 */
public final class ReflectionUtils {
    /**
     * Scansiona un package ed elenca tutte le classi al suo interno.
     * <p>
     * Basa il suo funzionamento sulla ricerca dei file .class dal {@link ClassLoader}.
     *
     * @param packageName Package name in cui cercare le classi
     * @return Stream delle classi trovate
     */
    public static Stream<? extends Class<?>> scanPackage(String packageName) {
        System.out.println("packageName = " + packageName);
        var packageResource = ReflectionUtils.class
                .getClassLoader()
                .getResource(packageName.replace('.', File.separatorChar));

        if (packageResource == null)
            return Stream.of();

        try {
            return Files.list(Paths.get(packageResource.toURI()))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(s -> s.endsWith(".class"))
                    .map(s -> s.substring(0, s.length() - ".class".length()))
                    .map(s -> {
                        try {
                            return Class.forName(packageName + "." + s);
                        } catch (ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull);
        } catch (IOException | URISyntaxException e) {
            return Stream.of();
        }
    }

    /**
     * Ottieni un costruttore dalla classe data,
     * che sia applicabile con i parametri forniti.
     * <p>
     * L'ordine dei parametri viene tenuto tale, il controllo di applicabilità
     * è sul tipo dei parametri, che possono essere superclassi dei parametri attuali.
     * <p>
     * Se un costruttore ha ultimo parametro {@link List} e il numero di valori forniti è superiore al numero atteso,
     * i parametri in eccesso verranno trasformati in una lista.
     *
     * @param clazz                 Classe su cui cercare il costruttore
     * @param constructorValueTypes Valori da voler passare al costruttore da cercare
     * @return Costruttore corrispondente, se presente
     */
    public static Optional<Constructor<?>> getMatchingCostructor(Class<?> clazz, List<? extends Class<?>> constructorValueTypes) {
        return Arrays.stream(clazz.getConstructors())
                .filter(c -> matchesTypes(c.getParameterTypes(), constructorValueTypes))
                .findAny();
    }

    /**
     * Ottieni un costruttore dalla classe data,
     * che sia applicabile con i parametri forniti.
     * <p>
     * L'ultimo parametro deve essere una {@link List} e il numero di valori forniti deve essere
     * superiore al numero atteso, cosi che i parametri in eccesso verranno trasformati in una lista.
     *
     * @param clazz                 Classe su cui cercare il costruttore
     * @param constructorValueTypes Valori da voler passare al costruttore da cercare
     * @return Costruttore corrispondente, se presente
     */
    public static Optional<Constructor<?>> getListCostructor(Class<?> clazz, List<? extends Class<?>> constructorValueTypes) {
        return Arrays.stream(clazz.getConstructors())
                .filter(c -> {
                    var params = c.getParameterTypes();
                    return params[params.length - 1] == List.class;
                })
                .filter(c -> {
                    var params = c.getParameterTypes();
                    return matchesTypes(
                            Arrays.copyOf(params, params.length - 1),
                            constructorValueTypes.subList(0, params.length - 1)
                    );
                })
                .findAny();
    }

    /**
     * Riunisci i parametri in overflow in una lista per un costruttore con lista alla fine.
     *
     * @param values Valori da voler passare al costruttore
     * @param constructor Costruttore da invocare
     * @return Parametri normalizzati per un costruttore con lista di trabocco
     * @see ReflectionUtils#getListCostructor(Class, List)
     */
    public static Object[] getListConstructorParams(Constructor<?> constructor, List<?> values) {
        var paramsLength = constructor.getParameterTypes().length;
        var standardValues = values.subList(0, paramsLength - 1);
        var overflowValues = values.subList(paramsLength - 1, values.size());
        return Stream.concat(standardValues.stream(), Stream.of(overflowValues)).toArray(Object[]::new);
    }

    /**
     * Controlla se i parametri di un metodo possono ricevere i valori forniti,
     * senza problemi di incompatibilità dei tipi.
     *
     * @param methodParams Tipi del metodo da richiamare
     * @param valueTypes   Valori da voler passare al metodo
     * @return Se è possibile passare i parametri attuali al metodo
     */
    public static boolean matchesTypes(Class<?>[] methodParams, List<? extends Class<?>> valueTypes) {
        if (methodParams.length != valueTypes.size())
            return false;

        return IntStream.range(0, methodParams.length)
                .allMatch(i -> methodParams[i].isAssignableFrom(valueTypes.get(i)));
    }

    /**
     * Ricerca un metodo di init in una classe.
     * <p>
     * Il metodo di init deve:
     * <ul>
     *     <li>essere statico</li>
     *     <li>essere pubblico</li>
     *     <li>accettare i parametri forniti</li>
     *     <li>restituire un valore (non essere void)</li>
     *     <li>chiamarsi <code>init</code></li>
     * </ul>
     *
     * @param clazz                 Classe su cui cercare
     * @param constructorValueTypes Valori da voler passare al metodo void
     * @return Metodo init trovato, se presente
     */
    public static Optional<Method> getInitMethod(Class<?> clazz, List<? extends Class<?>> constructorValueTypes) {
        return Arrays.stream(clazz.getMethods())
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> Objects.equals(m.getName(), "init"))
                .filter(m -> matchesTypes(m.getParameterTypes(), constructorValueTypes))
                .filter(m -> m.getReturnType() != void.class)
                .findAny();
    }
}
