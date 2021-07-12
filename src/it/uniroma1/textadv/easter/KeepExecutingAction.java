package it.uniroma1.textadv.easter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Azione da continuare ad eseguire in continuazione finchè non viene interrotta.
 * <p>
 * Deve essere terminato con {@link KeepExecutingAction#shutdown()} alla fine cosi da liberare il Thread
 */
public class KeepExecutingAction {
    /**
     * Esecutore dell'azione, a ripetizione finchè non viene terminata
     */
    private final ExecutorService actionExecutor = Executors.newSingleThreadExecutor();

    /**
     * Indicatore della continua esecuzione dell'azione in questo momento
     */
    private final AtomicBoolean keepExecuting = new AtomicBoolean(false);

    /**
     * Azione da eseguire
     */
    private final Runnable action;

    /**
     * Crea un nuovo esecutore di azioni a ripetizione
     *
     * @param action Azione da eseguire
     */
    public KeepExecutingAction(Runnable action) {
        this.action = action;
    }

    /**
     * Spegni definitivamente l'esecutore, terminando il thread associato
     */
    public void shutdown() {
        actionExecutor.shutdown();
    }

    /**
     * Interrompi l'esecuzione dell'azione a ripetizione. Può ricominciare successivamente.
     */
    public void stop() {
        keepExecuting.set(false);
    }

    /**
     * Inizia o ricomincia l'esecuzione dell'azione a ripetizione
     */
    public void start() {
        keepExecuting.set(true);
        actionExecutor.execute(() -> {
            while (keepExecuting.get())
                action.run();
        });
    }
}
