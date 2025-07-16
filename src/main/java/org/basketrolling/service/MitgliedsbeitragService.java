/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse für die Geschäftslogik rund um Mitgliedsbeiträge.
 * Diese Klasse dient als Vermittler zwischen Controller und DAO
 * und bietet Methoden zur Abfrage von Mitgliedsbeiträgen.
 * 
 * Erbt Standard-CRUD-Methoden von {@link BaseService}.
 * 
 * @author Marko
 */
public class MitgliedsbeitragService extends BaseService<Mitgliedsbeitrag> {

    private final MitgliedsbeitragDAO dao;

    /**
     * Konstruktor für den MitgliedsbeitragService.
     *
     * @param dao Das DAO-Objekt für den Zugriff auf Mitgliedsbeiträge.
     */
    public MitgliedsbeitragService(MitgliedsbeitragDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Liefert eine Liste aller Mitgliedsbeiträge mit einem bestimmten Betrag.
     * Die Methode kapselt den DAO-Aufruf in eine Fehlerbehandlung.
     *
     * @param betrag Der zu suchende Betrag.
     * @return Eine Liste der Mitgliedsbeiträge mit diesem Betrag,
     * oder eine leere Liste bei Fehlern oder wenn keine vorhanden sind.
     */
    public List<Mitgliedsbeitrag> getByBetrag(double betrag) {
        return TryCatchUtil.tryCatchList(() -> dao.findByBetrag(betrag));
    }
}
