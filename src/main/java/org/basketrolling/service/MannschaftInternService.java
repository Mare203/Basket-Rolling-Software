/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link MannschaftIntern}-Entitäten. Diese
 * Klasse erweitert {@link BaseService} und bietet eine Methode zur Suche
 * interner Mannschaften anhand ihres Namens.
 *
 * Die Fehlerbehandlung erfolgt über {@link TryCatchUtil}.
 *
 * @author Marko
 */
public class MannschaftInternService extends BaseService<MannschaftIntern> {

    private final MannschaftInternDAO dao;

    /**
     * Konstruktor, der das {@link MannschaftInternDAO} übergibt.
     *
     * @param dao das DAO-Objekt für interne Mannschaften
     */
    public MannschaftInternService(MannschaftInternDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Gibt alle {@link MannschaftIntern}-Einträge zurück, die der übergebenen
     * {@link Liga} zugeordnet sind.
     *
     * @param liga die {@link Liga}, für die zugehörige interne Mannschaften
     * gesucht werden
     * 
     * @return Liste der {@link MannschaftIntern}-Objekte, die in der
     * angegebenen Liga spielen; bei Fehlern eine leere Liste
     */
    public List<MannschaftIntern> getByLiga(Liga liga) {
        return TryCatchUtil.tryCatchList(() -> dao.findByLiga(liga));
    }
}
