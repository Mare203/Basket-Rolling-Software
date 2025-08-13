/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Statistik;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse für die Geschäftslogik rund um {@link Statistik}-Entitäten.
 * <p>
 * Diese Klasse erweitert {@link BaseService} und stellt zusätzliche Methoden
 * bereit, um Spielerstatistiken gezielt anhand von Spielen, Spielern oder
 * Mannschaften abzurufen. Außerdem können Top-Scorer ermittelt und Kennzahlen
 * wie Punkte pro Spiel berechnet werden.
 * </p>
 * <p>
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}, sodass
 * Ausnahmen kontrolliert behandelt und konsistente Rückgabewerte (z. B. leere
 * Listen oder {@code null}) gewährleistet werden.
 * </p>
 *
 * @author Marko
 */
public class StatistikService extends BaseService<Statistik> {

    private final StatistikDAO dao;

    /**
     * Erstellt einen neuen {@code StatistikService}.
     *
     * @param dao das {@link StatistikDAO}-Objekt für Datenbankabfragen zu
     * Spielerstatistiken
     */
    public StatistikService(StatistikDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Gibt alle Statistiken zurück, die einem bestimmten Spiel zugeordnet sind.
     *
     * @param spiel das {@link Spiele}-Objekt, für das die Statistiken abgerufen
     * werden sollen
     * @return eine {@link List} mit {@link Statistik}-Objekten, oder eine leere
     * Liste, falls keine Statistiken vorhanden sind
     */
    public List<Statistik> getBySpiel(Spiele spiel) {
        return TryCatchUtil.tryCatchList(() -> dao.findBySpiel(spiel));
    }

    /**
     * Ermittelt die fünf besten Punktesammler (Scorer) über alle Spiele hinweg.
     *
     * @return eine {@link List} von {@code Object[]} mit den Scorer-Daten (z.
     * B. Spielername, Punkte), oder eine leere Liste, falls keine Daten
     * vorhanden sind
     */
    public List<Object[]> getTop5Scorer() {
        return TryCatchUtil.tryCatchList(() -> dao.findTop5Scorer());
    }

    /**
     * Ermittelt den besten Punktesammler einer bestimmten Mannschaft.
     *
     * @param mannschaft die {@link MannschaftIntern}, deren Top-Scorer
     * ermittelt werden soll
     * @return ein {@code Object[]} mit den Scorer-Daten (z. B. Spielername,
     * Punkte), oder {@code null}, falls keine Daten vorhanden sind
     */
    public Object[] getTopScorerByMannschaft(MannschaftIntern mannschaft) {
        return TryCatchUtil.tryCatch(() -> dao.findTopScorerByMannschaft(mannschaft));
    }

    /**
     * Gibt alle Statistiken eines bestimmten Spielers zurück.
     *
     * @param spieler der {@link Spieler}, dessen Statistiken abgerufen werden
     * sollen
     * @return eine {@link List} mit {@link Statistik}-Objekten, oder eine leere
     * Liste, falls keine Statistiken vorhanden sind
     */
    public List<Statistik> getBySpieler(Spieler spieler) {
        return TryCatchUtil.tryCatch(() -> dao.findBySpieler(spieler));
    }

    /**
     * Berechnet den Durchschnitt der erzielten Punkte pro Spiel (PPG) für einen
     * Spieler.
     *
     * @param spieler der {@link Spieler}, dessen PPG berechnet werden soll
     * @return der Punkte-pro-Spiel-Wert als {@link Double}, oder {@code null},
     * falls keine Daten vorliegen
     */
    public Double getPpgBySpieler(Spieler spieler) {
        return TryCatchUtil.tryCatch(() -> dao.findPpgBySpieler(spieler));
    }
}
