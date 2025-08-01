/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utility-Klasse zur zentralen Fehlerbehandlung bei Methoden, die Ergebnisse
 * liefern, insbesondere {@link List}-Objekte.
 *
 * Statt jede Service-Methode einzeln mit try-catch abzusichern, können diese
 * statischen Hilfsmethoden verwendet werden, um Exceptions abzufangen und
 * Standardwerte zurückzugeben – typischerweise eine leere Liste oder
 * {@code null}.
 *
 * Ideal zur Verwendung in Service-Schichten, insbesondere in Kombination mit
 * Lambdas.
 *
 * Beispiel:
 * <pre>{@code
 * return TryCatchUtil.tryCatchList(() -> dao.findByName(name));
 * }</pre>
 *
 * @author Marko
 */
public class TryCatchUtil {

    /**
     * Führt den übergebenen {@link Supplier} innerhalb eines try-catch-Blocks
     * aus, wobei im Fehlerfall eine leere Liste zurückgegeben wird.
     *
     * @param <T> der Typ der Listenelemente
     * @param supplier eine Funktion, die eine {@link List} liefert
     * @return das Ergebnis der Funktion oder eine leere Liste bei Exception
     */
    public static <T> List<T> tryCatchList(Supplier<List<T>> supplier) {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Führt den übergebenen {@link Supplier} innerhalb eines try-catch-Blocks
     * aus und gibt {@code null} zurück, falls eine Exception auftritt.
     *
     * @param <T> der Rückgabetyp der Funktion
     * @param supplier eine Funktion, die ein Ergebnis liefert
     * @return das Ergebnis der Funktion oder {@code null} bei Exception
     */
    public static <T> T tryCatch(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
