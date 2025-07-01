/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link MannschaftExtern}-Entitäten.
 * Diese Klasse erweitert {@link BaseService} und stellt eine zusätzliche
 * Methode zur Suche externer Mannschaften anhand ihres Namens zur Verfügung.
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
     * Sucht {@link MannschaftExtern}-Einträge, deren Name (case-insensitive)
     * den angegebenen String enthält.
     *
     * @param name der gesuchte Name oder Teil davon
     * @return Liste der passenden {@link MannschaftExtern}-Objekte
     */
    public List<MannschaftExtern> getByName(String name) {
        return TryCatchUtil.tryCatchList(() -> dao.findByName(name));
    }
}
