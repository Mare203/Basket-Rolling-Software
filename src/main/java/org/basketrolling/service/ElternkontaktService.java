/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Elternkontakt;
import org.basketrolling.dao.ElternkontaktDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Elternkontakt}-Entitäten.
 * Diese Klasse erweitert {@link BaseService} und stellt zusätzliche
 * Methoden zur Verfügung, um nach Vor- und Nachnamen zu suchen.
 * 
 * Die Fehlerbehandlung erfolgt über das Utility {@link TryCatchUtil}.
 * 
 * @author Marko
 */
public class ElternkontaktService extends BaseService<Elternkontakt> {

    private final ElternkontaktDAO dao;

    /**
     * Konstruktor, der das zugehörige DAO übergibt.
     *
     * @param dao das {@link ElternkontaktDAO}-Objekt
     */
    public ElternkontaktService(ElternkontaktDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Gibt eine Liste von {@link Elternkontakt}-Einträgen zurück,
     * deren Vorname den angegebenen Begriff enthält (case-insensitive).
     *
     * @param vorname der gesuchte Vorname oder Teil davon
     * @return Liste der passenden {@link Elternkontakt}-Objekte
     */
    public List<Elternkontakt> getByVorname(String vorname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByVorname(vorname));
    }

    /**
     * Gibt eine Liste von {@link Elternkontakt}-Einträgen zurück,
     * deren Nachname den angegebenen Begriff enthält (case-insensitive).
     *
     * @param nachname der gesuchte Nachname oder Teil davon
     * @return Liste der passenden {@link Elternkontakt}-Objekte
     */
    public List<Elternkontakt> getByNachname(String nachname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByNachname(nachname));
    }
}
