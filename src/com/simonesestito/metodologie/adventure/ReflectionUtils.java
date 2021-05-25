package com.simonesestito.metodologie.adventure;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public static Constructor<?> getMatchingCostructor(Class<?> clazz, List<? extends Class<?>> constructorValueTypes) throws NoSuchMethodException {
        return Arrays.stream(clazz.getConstructors())
                .filter(c -> matchesConstructor(c, constructorValueTypes))
                .findAny()
                .orElseThrow(NoSuchMethodException::new);
    }

    public static boolean matchesConstructor(Constructor<?> constructor, List<? extends Class<?>> constructorValueTypes) {
        if (constructor.getParameterCount() != constructorValueTypes.size())
            return false;

        var paramTypes = constructor.getParameterTypes();
        return IntStream.range(0, constructor.getParameterCount())
                .allMatch(i -> paramTypes[i].isAssignableFrom(constructorValueTypes.get(i)));
    }
}
