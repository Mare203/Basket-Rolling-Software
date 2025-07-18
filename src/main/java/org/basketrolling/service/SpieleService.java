/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.time.LocalDate;
import java.util.List;
import org.basketrolling.beans.Spiele;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Spiele}-Entitäten. Diese Klasse
 * erweitert {@link BaseService} und stellt eine Methode zur Suche von Spielen
 * anhand ihres Datums bereit.
 *
 * Die Fehlerbehandlung erfolgt über {@link TryCatchUtil}, um Ausnahmen sauber
 * zu behandeln.
 *
 * @author Marko
 */
public class SpieleService extends BaseService<Spiele> {

    private final SpieleDAO dao;

    /**
     * Konstruktor, der das {@link SpieleDAO} übergibt.
     *
     * @param dao das DAO-Objekt für Spiele
     */
    public SpieleService(SpieleDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht {@link Spiele}-Einträge, die an einem bestimmten Datum stattfinden.
     *
     * @param datum das Datum, an dem das Spiel stattfindet
     * @return Liste der passenden {@link Spiele}-Objekte
     */
    public List<Spiele> getByDatum(LocalDate datum) {
        return TryCatchUtil.tryCatchList(() -> dao.findByDatum(datum));
    }
}
