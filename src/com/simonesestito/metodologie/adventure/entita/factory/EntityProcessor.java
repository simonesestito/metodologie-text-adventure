package com.simonesestito.metodologie.adventure.entita.factory;

import com.simonesestito.metodologie.adventure.MultiMap;
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

public interface EntityProcessor {
    void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException;

    static EntityProcessor forTag(String tagName) throws GameFile.ParseException {
        try {
            return (EntityProcessor) ReflectionUtils.scanPackage(EntityProcessor.class.getPackageName())
                    .filter(c -> c.isAnnotationPresent(ForTag.class))
                    .filter(c -> c.getAnnotation(ForTag.class).value().equals(tagName))
                    .filter(EntityProcessor.class::isAssignableFrom)
                    .findAny()
                    .orElseThrow(() -> new ClassNotFoundException("Can't find a factory class for tag" + tagName))
                    .getConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException e) {
            throw new GameFile.ParseException("Unrecognized tag: " + tagName, e);
        }
    }

    class BuildContext {
        private final GameFile gameFile;
        private final Map<String, DependantEntity> hardDependencies = new HashMap<>();
        private final MultiMap<String, DependencyObserver> softDependencies = new MultiMap<>();
        private final Map<String, Entity> resolvedEntities = new HashMap<>();

        public BuildContext(GameFile gameFile) {
            this.gameFile = gameFile;
        }

        public void registerDependantEntity(DependantEntity dependantEntity) throws DependencyException {
            var key = dependantEntity.entityName();
            if (hardDependencies.containsKey(key)) {
                throw new DependencyException("Duplicate dependency registered");
            }
            hardDependencies.put(key, dependantEntity);
        }

        public void observeEntity(DependencyObserver dependencyObserver) {
            softDependencies.add(dependencyObserver.dependency(), dependencyObserver);
        }

        public void addResolvedDependency(Entity entity) throws DependencyException {
            if (resolvedEntities.containsKey(entity.getName()))
                throw new DependencyException("Duplicate dependency found");

            hardDependencies.remove(entity.getName());
            resolvedEntities.put(entity.getName(), entity);
            softDependencies.consume(entity.getName(), dep -> dep.callback().accept(entity));
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
                hardDependencies.remove(entry.entityName());
            }
            return this; // Fluent design
        }

        private Entity createDependencyForEntry(DependantEntity entry) throws DependencyException {
            try {
                var clazz = Class.forName(Entity.class.getPackageName() + "." + entry.className());
                var constructorValuesList = Stream.concat(
                        entry.constructorValues().stream(),
                        entry.dependencies().stream().map(resolvedEntities::get)
                ).toList();

                var constructorValueTypes = constructorValuesList.stream()
                        .map(Object::getClass)
                        .toList();

                var constructorValues = constructorValuesList.toArray(Object[]::new);

                var constructor = ReflectionUtils.getMatchingCostructor(clazz, constructorValueTypes);
                if (constructor.isPresent()) {
                    return (Entity) constructor.get().newInstance(constructorValues);
                }

                var initMethod = ReflectionUtils.getInitMethod(clazz, constructorValueTypes);
                if (initMethod.isPresent()) {
                    return (Entity) initMethod.get().invoke(null, constructorValues);
                }

                throw new DependencyException("Requested entity doesn't match dependencies: " + entry.entityName());
            } catch (ClassNotFoundException e) {
                throw new DependencyException("Unable to find class: " + entry.className(), e);
            } catch (ReflectiveOperationException e) {
                throw new DependencyException("Error creating " + entry.entityName(), e);
            }
        }

        public static record DependantEntity(
                String className,
                String entityName,
                List<String> dependencies,
                List<? super Object> constructorValues) {
            public DependantEntity {
                constructorValues = new LinkedList<>(constructorValues);
                constructorValues.add(0, entityName);
            }

            public DependantEntity(String className,
                                   String entityName,
                                   List<String> dependencies) {
                this(className, entityName, dependencies, List.of());
            }

            public DependantEntity(String className,
                                   String entityName) {
                this(className, entityName, List.of());
            }
        }

        public static record DependencyObserver(
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

        public GameFile getGameFile() {
            return gameFile;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface ForTag {
        String value();
    }
}
