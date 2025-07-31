/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utility-Klasse zur zentralen Fehlerbehandlung bei Methoden, die eine
 * {@link List} zurückgeben.
 *
 * Statt jede Service-Methode einzeln in ein try-catch zu packen, kann diese
 * Hilfsmethode verwendet werden, um Exceptions abzufangen und eine leere Liste
 * zurückzugeben.
 *
 * Ideal zur Verwendung in Service-Schichten mit Lambdas.
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
     * aus. Im Fehlerfall wird die Exception ausgegeben und eine leere Liste
     * zurückgegeben.
     *
     * @param <T> der Typ der Listenelemente
     * @param supplier eine Funktion, die eine {@link List} liefert
     * @return die Ergebnisliste oder eine leere Liste bei Exception
     */
    public static <T> List<T> tryCatchList(Supplier<List<T>> supplier) {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static <T> T tryCatch(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
