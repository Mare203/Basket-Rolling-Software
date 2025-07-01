/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Halle;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Halle}-Entitäten.
 * Diese Klasse erweitert {@link BaseService} und bietet zusätzliche
 * Suchfunktionen nach Name, Postleitzahl und Ort.
 *
 * Die Fehlerbehandlung erfolgt über das Utility {@link TryCatchUtil}.
 * 
 * @author Marko
 */
public class HalleService extends BaseService<Halle> {

    private final HalleDAO dao;

    /**
     * Konstruktor, der das {@link HalleDAO} übergibt.
     *
     * @param dao das DAO-Objekt für Hallen
     */
    public HalleService(HalleDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht Hallen anhand eines Namens oder Namensfragments (case-insensitive).
     *
     * @param name der gesuchte Hallenname oder ein Teil davon
     * @return Liste der passenden {@link Halle}-Objekte
     */
    public List<Halle> getByName(String name) {
        return TryCatchUtil.tryCatchList(() -> dao.findByName(name));
    }

    /**
     * Sucht Hallen anhand der Postleitzahl.
     *
     * @param plz die gesuchte Postleitzahl
     * @return Liste der passenden {@link Halle}-Objekte
     */
    public List<Halle> getByPostleitzahl(int plz) {
        return TryCatchUtil.tryCatchList(() -> dao.findByPostleitzahl(plz));
    }

    /**
     * Sucht Hallen anhand eines Ortsnamens oder Ortsteils (case-insensitive).
     *
     * @param ort der gesuchte Ort
     * @return Liste der passenden {@link Halle}-Objekte
     */
    public List<Halle> getByOrt(String ort) {
        return TryCatchUtil.tryCatchList(() -> dao.findByOrt(ort));
    }
}
