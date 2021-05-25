package com.simonesestito.metodologie.adventure;

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

public final class ReflectionUtils {
    public static Stream<? extends Class<?>> scanPackage(String packageName) {
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

    public static Optional<Constructor<?>> getMatchingCostructor(Class<?> clazz, List<? extends Class<?>> constructorValueTypes) {
        return Arrays.stream(clazz.getConstructors())
                .filter(c -> matchesTypes(c.getParameterTypes(), constructorValueTypes))
                .findAny();
    }

    public static boolean matchesTypes(Class<?>[] methodParams, List<? extends Class<?>> valueTypes) {
        if (methodParams.length != valueTypes.size())
            return false;

        return IntStream.range(0, methodParams.length)
                .allMatch(i -> methodParams[i].isAssignableFrom(valueTypes.get(i)));
    }

    public static Optional<Method> getInitMethod(Class<?> clazz, List<? extends Class<?>> constructorValueTypes) {
        return Arrays.stream(clazz.getMethods())
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> Objects.equals(m.getName(), "init"))
                .filter(m -> matchesTypes(m.getParameterTypes(), constructorValueTypes))
                .findAny();
    }
}
