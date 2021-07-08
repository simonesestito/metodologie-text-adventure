package it.uniroma1.textadv.utils;

/**
 * Calcolo della distanza tra due stringhe.
 * <p>
 * Può essere implementato con tanti algoritmi diversi.
 */
public interface StringDistance {
    /**
     * Calcola la distanza normalizzata [0,1] tra due stringhe.
     *
     * @param x Una delle due stringhe da controllare
     * @param y Una delle due stringhe da controllare
     * @return Distanza normalizzata tra le due stringhe
     */
    double calculateDistance(String x, String y);
}
