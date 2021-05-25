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

/**
 * Nucleo centrale di tutto il processo di parsing del file del mondo e sua istanziazione.
 * <p>
 * Un processor è responsabile di processare una singola sezione del file,
 * creando nuovi oggetti se possibile, oppure registrando le dipendenze richieste,
 * per creare l'oggetto vero e proprio successivamente.
 * <p>
 * Si occupa di una sola sezione e segue il <b>Single Responsibility Principle</b>.
 * <p>
 * Non propriamente una <b>Factory</b>, ma parte di essa.
 * Non è una vera e propria factory perchè spesso non crea un oggetto direttamente,
 * ma bensì ne rende nota l'esistenza e successivamente lo crea realmente.
 * <p>
 * Può essere visto come un componente di una factory più grande per l'intero mondo,
 * come utilizzato in {@link MondoFactory}
 *
 * @see GameFile
 * @see MondoFactory
 */
public interface EntityProcessor {
    /**
     * Processa una singola sezione del file di gioco,
     * validandone il contenuto e creando (o registrando) gli oggetti da creare.
     * <p>
     * Gli oggetti creati, registrati, vanno tutti segnalati al context {@link BuildContext}
     * condiviso tra i Processor, a permettere una comunicazione controllata.
     *
     * @param section Sezione da processare
     * @param context Context condiviso tra i processor
     * @throws GameFile.ParseException In caso di errori nel parsing del file di gioco
     */
    void registerDependencies(GameFile.Section section, BuildContext context) throws GameFile.ParseException;

    /**
     * Ottieni il processor corrispondente per il tag del file di gioco.
     * <p>
     * La ricerca avviene mediante uso della reflection.
     * Il processor associato deve essere nello <b>stesso package</b> e
     * deve essere <b>annotato</b> con {@link ForTag} a segnalazione del tag di cui si occupa.
     *
     * @param tagName Nome del tag di cui ottenere un processor
     * @return Processor associato trovato
     * @throws GameFile.ParseException Se non viene trovato alcun processor,
     *                                 quindi il file viene considerato invalido,
     *                                 in quanto il tag non è supportato / conosciuto.
     */
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

    /**
     * Contesto condiviso tra tutti i processor.
     * <p>
     * Contiene il {@link GameFile} che si sta processando in generale.
     * <p>
     * Simula un <b>grafo delle dipendenze</b> tra tutte le {@link Entity} di gioco,
     * suddividendo le dipendenze su 2 livelli:
     * <ul>
     *     <li><u>Hard dependency</u>: dipendenze indispensabili al momento della creazione di un oggetto</li>
     *     <li><u>Soft dependency</u>: dipendenze aggiunte all'oggetto successivamente</li>
     * </ul>
     * <p>
     * Avere un solo livello di dipendenze rigide "hard", significava avere situazioni di impossibilità
     * a creare le {@link Entity} a causa delle <b>dipendenze circolari</b>.
     * <p>
     * Esempio:
     * <p>
     * La stanza s1 ha un link a s2, e ipotizziamo la richieda dal costruttore.
     * Ma anche s2 richiederebbe s1 nel costruttore secondo l'ipotesi.
     * Come risultato, nessuna stanza sarebbe costruibile.
     */
    class BuildContext {
        /**
         * File di gioco processato da tutti i processor in questione
         */
        private final GameFile gameFile;

        /**
         * Mappa delle dipendenze rigide, indicizzata
         * per accedere in tempo costante alle dipendenze dato il nome di un'entità dipendente da esse.
         */
        private final Map<String, DependantEntity> hardDependencies = new HashMap<>();

        /**
         * Mappa delle dipendenze leggere, questa indicizzata
         * per accedere in tempo costante agli observer
         * dato il nome della dipendenza osservata.
         * <p>
         * Siccome ci possono essere più observer per singola dipendenza,
         * è rappresentato mediante {@link MultiMap}
         */
        private final MultiMap<String, DependencyObserver> softDependencies = new MultiMap<>();

        /**
         * Mappa delle entità già risolte e correttamente costruite.
         */
        private final Map<String, Entity> resolvedEntities = new HashMap<>();

        /**
         * Crea un nuovo context per i processor
         *
         * @param gameFile File di gioco processato da tutti
         */
        public BuildContext(GameFile gameFile) {
            this.gameFile = gameFile;
        }

        /**
         * Registra una nuova dipendenza forte.
         *
         * @param dependantEntity Descrizione della dipendenza
         * @throws DependencyException In caso ci siano errori sulla dipendenza (es: duplicati)
         */
        public void registerDependantEntity(DependantEntity dependantEntity) throws DependencyException {
            var key = dependantEntity.entityName();
            if (hardDependencies.containsKey(key)) {
                throw new DependencyException("Duplicate dependency registered");
            }
            hardDependencies.put(key, dependantEntity);
        }

        /**
         * Registra una nuova dipendenza leggera,
         * sfruttando i meccanismi dell'<b>observer pattern</b>,
         * dove il Subject è l'entità da cui si dipende.
         *
         * @param dependencyObserver Observer su un'entità
         */
        public void observeEntity(DependencyObserver dependencyObserver) {
            softDependencies.add(dependencyObserver.dependency(), dependencyObserver);
        }

        /**
         * Fornisci un'entità già risolta da usare per il resto delle costruzioni.
         *
         * @param entity Entità fornita
         * @throws DependencyException In caso di duplicati o errori sull'entità fornita
         */
        public void addResolvedDependency(Entity entity) throws DependencyException {
            if (resolvedEntities.containsKey(entity.getName()))
                throw new DependencyException("Duplicate dependency found");

            hardDependencies.remove(entity.getName());
            resolvedEntities.put(entity.getName(), entity);
            softDependencies.consume(entity.getName(), dep -> dep.callback().accept(entity));
        }

        /**
         * Ottieni un'entità risolta, se presente, in base al suo nome
         *
         * @param name Nome dell'entità cercata
         * @return Entità trovata, se presente
         */
        public Optional<Entity> getResolvedDependency(String name) {
            return Optional.ofNullable(resolvedEntities.get(name));
        }

        /**
         * Ottieni un'entità risolta, se presente, in base alla sua classe.
         * <p>
         * In caso ne siano presenti più di una, ne restituisce una senza criteri.
         *
         * @param clazz Classe dell'entità cercata
         * @return Entità trovata, se presente
         */
        public <T> Optional<? extends T> getResolvedDependency(Class<T> clazz) {
            return resolvedEntities.values()
                    .stream()
                    .filter(v -> clazz.isAssignableFrom(v.getClass()))
                    .map(clazz::cast)
                    .findAny();
        }

        /**
         * Operazione terminale di tutto il processo.
         * <p>
         * Se consideriamo le varie registrazioni di dipendenza come operazioni intermedie,
         * e questa come operazione terminale,
         * ci sono analogie tra {@link BuildContext} e generalmente un <b>Builder di un grafo delle dipendenze</b>.
         *
         * @return Lo stesso grafo ma senza più alcuna dipendenza irrisolta.
         * @throws DependencyException Errore durante la risoluzione delle dipendenze
         */
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

        /**
         * Crea l'entità partendo dalla descrizione della dipendenza.
         * <p>
         * Rappresenta il lato <b>Factory</b> del Processor,
         * in quanto crea realmente gli oggetti.
         *
         * @param entry Descrizione della dipendenza da creare
         * @return Entità creata
         * @throws DependencyException Errore durante la risoluzione delle dipendenze
         */
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

        /**
         * Record descrivente le dipendenze di un'entità
         */
        public static record DependantEntity(
                String className,
                String entityName,
                List<String> dependencies,
                List<? super Object> constructorValues) {

            /**
             * Crea una dipendenza con tutti i parametri
             *
             * @param className         Nome della classe dell'entità dipendente
             * @param entityName        Nome logico dell'entità, usato dalle altre per riferirsi a questa come loro dipendenza
             * @param constructorValues Valori pre-forniti da passare al costruttore dell'entità.
             *                          Dato che tutte le entità vogliono un nome, non va specificato e sarà il primo.
             *                          I parametri sono forniti come primi parametri.
             * @param dependencies      Elenco dei nomi delle dipendenze richieste, passate in fondo al costruttore.
             */
            public DependantEntity {
                constructorValues = new LinkedList<>(constructorValues);
                constructorValues.add(0, entityName);
            }

            /**
             * Crea una dipendenza senza valori aggiuntivi nel costruttore
             *
             * @param className         Nome della classe dell'entità dipendente
             * @param entityName        Nome logico dell'entità, usato dalle altre per riferirsi a questa come loro dipendenza
             * @param dependencies      Elenco dei nomi delle dipendenze richieste, passate in fondo al costruttore.
             */
            public DependantEntity(String className,
                                   String entityName,
                                   List<String> dependencies) {
                this(className, entityName, dependencies, List.of());
            }

            /**
             * Crea una dipendenza passando al costruttore solo il nome dell'entità
             *
             * @param className         Nome della classe dell'entità dipendente
             * @param entityName        Nome logico dell'entità, usato dalle altre per riferirsi a questa come loro dipendenza
             */
            public DependantEntity(String className, String entityName) {
                this(className, entityName, List.of());
            }
        }

        /**
         * Descrizione di una dipendenza leggera usando l'<b>observer pattern</b>
         * @param dependency Nome della dipendenza da ascoltare
         * @param callback Callback da eseguire quando la dipendenza sarà pronta
         */
        public static record DependencyObserver(
                String dependency,
                Consumer<Entity> callback) {
        }

        /**
         * Eccezione in caso di errore sulle dipendenze o loro risoluzione.
         */
        public static class DependencyException extends GameFile.ParseException {
            /**
             * Eccezione per errore sulle dipendenze, con motivazione, ma senza causa esplicita.
             * @param message Descrizione dell'errore
             */
            public DependencyException(String message) {
                super(message);
            }

            /**
             * Eccezione per errore sulle dipendenze, con motivazione e causa
             * @param message Descrizione dell'errore
             * @param cause Eccezione scatenante
             */
            public DependencyException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        /**
         * Ottieni il file di gioco attualmente in processing
         * @return File di gioco corrente
         */
        public GameFile getGameFile() {
            return gameFile;
        }
    }

    /**
     * Annotazione usata tramite reflection per
     * individuare il tag processabile da ogni processor
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface ForTag {
        String value();
    }
}
