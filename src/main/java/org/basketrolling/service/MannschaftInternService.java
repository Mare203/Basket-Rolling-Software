/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link MannschaftIntern}-Entitäten.
 * Diese Klasse erweitert {@link BaseService} und bietet eine Methode zur
 * Suche interner Mannschaften anhand ihres Namens.
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
     * Sucht {@link MannschaftIntern}-Einträge, deren Name (case-insensitive)
     * den angegebenen Suchbegriff enthält.
     *
     * @param name der Name oder ein Teil des Namens der Mannschaft
     * @return Liste der passenden {@link MannschaftIntern}-Objekte
     */
    public List<MannschaftIntern> getByName(String name) {
        return TryCatchUtil.tryCatchList(() -> dao.findByName(name));
    }
}
