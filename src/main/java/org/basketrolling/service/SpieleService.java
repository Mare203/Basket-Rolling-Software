/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.time.LocalDate;
import java.util.List;
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Spiele}-Entitäten.
 * <p>
 * Diese Klasse erweitert {@link BaseService} und stellt Methoden bereit, um
 * Spiele anhand verschiedener Kriterien wie Datum, Mannschaft oder Halle zu
 * suchen. Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}, um
 * Ausnahmen kontrolliert zu behandeln und leere Ergebnisse sicher
 * zurückzugeben.
 * </p>
 *
 * @author Marko
 */
public class SpieleService extends BaseService<Spiele> {

    private final SpieleDAO dao;

    /**
     * Erstellt einen neuen {@code SpieleService}.
     *
     * @param dao das {@link SpieleDAO}-Objekt zur Ausführung von
     * Datenbankabfragen für {@link Spiele}
     */
    public SpieleService(SpieleDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht alle {@link Spiele}, die an einem bestimmten Datum stattfinden.
     *
     * @param datum das {@link LocalDate}, an dem die Spiele stattfinden
     * @return eine {@link List} mit allen passenden {@link Spiele}-Objekten,
     * oder eine leere Liste, falls keine vorhanden sind
     */
    public List<Spiele> getByDatum(LocalDate datum) {
        return TryCatchUtil.tryCatchList(() -> dao.findByDatum(datum));
    }

    /**
     * Sucht alle {@link Spiele}, an denen eine bestimmte interne Mannschaft
     * teilnimmt.
     *
     * @param mannschaft die {@link MannschaftIntern}, nach der gefiltert werden
     * soll
     * @return eine {@link List} mit allen passenden {@link Spiele}-Objekten,
     * oder eine leere Liste, falls keine vorhanden sind
     */
    public List<Spiele> getByMannschaftIntern(MannschaftIntern mannschaft) {
        return TryCatchUtil.tryCatchList(() -> dao.findByMannschaftIntern(mannschaft));
    }

    /**
     * Sucht alle {@link Spiele}, an denen eine bestimmte externe Mannschaft
     * teilnimmt.
     *
     * @param mannschaft die {@link MannschaftExtern}, nach der gefiltert werden
     * soll
     * @return eine {@link List} mit allen passenden {@link Spiele}-Objekten,
     * oder eine leere Liste, falls keine vorhanden sind
     */
    public List<Spiele> getByMannschaftExtern(MannschaftExtern mannschaft) {
        return TryCatchUtil.tryCatchList(() -> dao.findByMannschaftExtern(mannschaft));
    }

    /**
     * Ermittelt den durchschnittlichen Punkteschnitt einer internen Mannschaft
     * über alle gespielten Spiele.
     *
     * @param mannschaft die {@link MannschaftIntern}, deren Punkteschnitt
     * berechnet werden soll
     * @return der Punkteschnitt als {@link Double}, oder {@code null}, falls
     * keine Daten vorliegen
     */
    public Double getPunkteschnittProMannschaft(MannschaftIntern mannschaft) {
        return TryCatchUtil.tryCatch(() -> dao.findPunkteschnittProMannschaft(mannschaft));
    }

    /**
     * Sucht alle {@link Spiele}, die in einer bestimmten Halle stattfinden.
     *
     * @param halle die {@link Halle}, nach der gefiltert werden soll
     * @return eine {@link List} mit allen passenden {@link Spiele}-Objekten,
     * oder eine leere Liste, falls keine vorhanden sind
     */
    public List<Spiele> getByHalle(Halle halle) {
        return TryCatchUtil.tryCatchList(() -> dao.findByHalle(halle));
    }
}
