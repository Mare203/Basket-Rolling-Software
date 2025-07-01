/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Liga;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Liga}-Entitäten.
 * Diese Klasse erweitert {@link BaseService} und bietet eine zusätzliche
 * Methode zur Suche von Ligen anhand ihres Namens.
 * 
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}.
 * 
 * @author Marko
 */
public class LigaService extends BaseService<Liga> {

    private final LigaDAO dao;

    /**
     * Konstruktor, der das {@link LigaDAO} übergibt.
     *
     * @param dao das DAO-Objekt zur Datenbankabfrage
     */
    public LigaService(LigaDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht {@link Liga}-Einträge, deren Name (case-insensitive) den angegebenen String enthält.
     *
     * @param name der gesuchte Name oder ein Teil davon
     * @return Liste der passenden {@link Liga}-Objekte
     */
    public List<Liga> getByName(String name) {
        return TryCatchUtil.tryCatchList(() -> dao.findByName(name));
    }
}