/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link MannschaftExtern}-Entitäten. Diese
 * Klasse erweitert {@link BaseService} und stellt eine zusätzliche Methode zur
 * Suche externer Mannschaften anhand ihres Namens zur Verfügung.
 *
 * Die Fehlerbehandlung erfolgt über {@link TryCatchUtil}.
 *
 * @author Marko
 */
public class MannschaftExternService extends BaseService<MannschaftExtern> {

    private final MannschaftExternDAO dao;

    /**
     * Konstruktor, der das {@link MannschaftExternDAO} übergibt.
     *
     * @param dao das DAO-Objekt für externe Mannschaften
     */
    public MannschaftExternService(MannschaftExternDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Gibt alle {@link MannschaftExtern}-Einträge zurück, die der übergebenen
     * {@link Liga} zugeordnet sind.
     *
     * @param liga die {@link Liga}, für die zugehörige interne Mannschaften
     * gesucht werden
     *
     * @return Liste der {@link MannschaftExtern}-Objekte, die in der
     * angegebenen Liga spielen; bei Fehlern eine leere Liste
     */
    public List<MannschaftExtern> getByLiga(Liga liga) {
        return TryCatchUtil.tryCatchList(() -> dao.findByLiga(liga));
    }
}
