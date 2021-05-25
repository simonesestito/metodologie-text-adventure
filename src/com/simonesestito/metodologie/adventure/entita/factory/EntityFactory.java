package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.ReflectionUtils;
import com.simonesestito.metodologie.adventure.entita.parser.GameFile;
import com.simonesestito.metodologie.adventure.entita.pojo.Entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface EntityFactory {
    void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException;

    static EntityFactory forTag(String tagName) throws GameFile.ParseException {
        try {
            return (EntityFactory) ReflectionUtils.scanPackage(EntityFactory.class.getPackageName())
                    .filter(c -> c.isAnnotationPresent(ForTag.class))
                    .filter(c -> c.getAnnotation(ForTag.class).value().equals(tagName))
                    .findAny()
                    .orElseThrow(() -> new ClassNotFoundException("Can't find a factory class for tag" + tagName))
                    .getConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException | ClassCastException e) {
            throw new GameFile.ParseException("Unrecognized tag: " + tagName, e);
        }
    }

    class BuildContext {
        private final Map<String, HardDependency> hardDependencies = new HashMap<>();
        private final Map<String, List<SoftDependency>> softDependencies = new HashMap<>();
        private final Map<String, Entity> resolvedEntities = new HashMap<>();

        public void registerHardDependency(HardDependency hardDependency) throws DependencyException {
            var key = hardDependency.dependantName();
            if (hardDependencies.containsKey(key)) {
                throw new DependencyException("Duplicate dependency registered");
            }
            hardDependencies.put(key, hardDependency);
        }

        public void registerSoftDependency(SoftDependency softDependency) {
            softDependencies
                    .computeIfAbsent(softDependency.dependency(), __ -> new ArrayList<>())
                    .add(softDependency);
        }

        public void addResolvedDependency(Entity entity) throws DependencyException {
            if (resolvedEntities.containsKey(entity.getName()))
                throw new DependencyException("Duplicate dependency found");

            hardDependencies.remove(entity.getName());
            resolvedEntities.put(entity.getName(), entity);

            var dependencyObservers = softDependencies.remove(entity.getName());
            if (dependencyObservers != null)
                dependencyObservers.forEach(dep -> dep.callback().accept(entity));
        }

        public Optional<Entity> getResolvedDependency(String name) {
            return Optional.ofNullable(resolvedEntities.get(name));
        }

        public <T> Optional<? extends T> getResolvedDependency(Class<T> clazz) {
            return resolvedEntities.values()
                    .stream()
                    .filter(v -> clazz.isAssignableFrom(v.getClass()))
                    .map(clazz::cast)
                    .findAny();
        }

        public BuildContext resolve() throws DependencyException {
            while (!hardDependencies.isEmpty()) {
                var entry = hardDependencies.values()
                        .stream()
                        .filter(dep -> dep.dependencies()
                                .stream()
                                .allMatch(resolvedEntities::containsKey))
                        .findFirst()
                        .orElseThrow(() -> new DependencyException("Circular dependencies detected"));
                addResolvedDependency(createDependencyForEntry(entry));
                hardDependencies.remove(entry.dependantName());
            }
            return this; // Fluent design
        }

        private Entity createDependencyForEntry(HardDependency entry) throws DependencyException {
            try {
                var clazz = Class.forName(Entity.class.getPackageName() + "." + entry.className());
                var constructorValues = Stream.concat(
                        entry.constructorValues().stream(),
                        entry.dependencies().stream().map(resolvedEntities::get)
                ).toList();

                var constructorValueTypes = constructorValues.stream()
                        .map(Object::getClass)
                        .toList();

                return (Entity) ReflectionUtils.getMatchingCostructor(clazz, constructorValueTypes)
                        .newInstance(constructorValues.toArray(Object[]::new));
            } catch (ClassNotFoundException e) {
                throw new DependencyException("Unable to find class: " + entry.className(), e);
            } catch (NoSuchMethodException e) {
                throw new DependencyException("Requested entity doesn't match dependencies: " + entry.dependantName(), e);
            } catch (ReflectiveOperationException e) {
                throw new DependencyException("Error creating " + entry.dependantName(), e);
            }
        }

        public static record HardDependency(
                String className,
                String dependantName,
                List<?> constructorValues,
                List<String> dependencies) {
            @Override
            public int hashCode() {
                return Objects.hash(dependantName);
            }
        }

        public static record SoftDependency(
                String dependency,
                Consumer<Entity> callback) {
        }

        public static class DependencyException extends GameFile.ParseException {
            public DependencyException(String message) {
                super(message);
            }

            public DependencyException(String message, Throwable cause) {
                super(message, cause);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface ForTag {
        String value();
    }
}
