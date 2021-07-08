package it.uniroma1.textadv.utils;

import java.util.Arrays;

/**
 * Implementazione dell'algoritmo di distanza tra stringhe di Levenshtein.
 * <a href="https://en.wikipedia.org/wiki/Levenshtein_distance#Iterative_with_full_matrix">Fonte</a>
 */
public class LevenshteinDistance implements StringDistance {
    /**
     * Calcola il numero minimo tra quelli dati
     *
     * @param numbers Numeri di cui trovare il minimo
     * @return Minimo numero tra quelli dati
     */
    private static double min(double... numbers) {
        return Arrays.stream(numbers).min().orElseThrow();
    }

    /**
     * Calcola la distanza normalizzata [0,1] tra due stringhe.
     * <p>
     * Utilizza l'algoritmo di Levenshtein:
     * <a href="https://en.wikipedia.org/wiki/Levenshtein_distance#Iterative_with_full_matrix">Fonte</a>
     *
     * @param x Una delle due stringhe da controllare
     * @param y Una delle due stringhe da controllare
     * @return Distanza normalizzata tra le due stringhe
     */
    @Override
    public double calculateDistance(String x, String y) {
        var distances = new double[x.length() + 1][y.length() + 1];

        for (int i = 0; i < distances.length; i++)
            Arrays.fill(distances[i], i);

        for (int i = 0; i < distances[0].length; i++)
            distances[0][i] = i;

        for (int j = 0; j < y.length(); j++) {
            for (int i = 0; i < x.length(); i++) {
                var substitutionCost = x.charAt(i) == y.charAt(j) ? 0 : 1;
                var deletionCost = distances[i][j + 1] + 1;
                var insertionCost = distances[i + 1][j] + 0.8;
                substitutionCost += distances[i][j];
                distances[i + 1][j + 1] = min(substitutionCost, deletionCost, insertionCost);
            }
        }

        double rawDistance = distances[x.length()][y.length()];
        var maxValue = Math.max(x.length(), y.length());
        return rawDistance / maxValue;
    }
}
