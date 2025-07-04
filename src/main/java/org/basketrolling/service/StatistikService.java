/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Statistik;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse für die Geschäftslogik rund um Spielerstatistiken.
 * Diese Klasse stellt Methoden zur Verfügung, um Statistiken zu einem bestimmten Spiel abzufragen.
 * 
 * Erbt Standard-CRUD-Methoden von {@link BaseService}.
 * 
 * @author Marko
 */
public class StatistikService extends BaseService<Statistik> {

    private final StatistikDAO dao;

    /**
     * Konstruktor für StatistikService.
     *
     * @param dao Das DAO-Objekt für den Zugriff auf Spielerstatistiken.
     */
    public StatistikService(StatistikDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Gibt alle Statistiken zurück, die einem bestimmten Spiel zugeordnet sind.
     * Die Methode kapselt den DAO-Aufruf in eine Fehlerbehandlung.
     *
     * @param spiel Das {@link Spiele}-Objekt, für das die Statistiken abgerufen werden sollen.
     * @return Eine Liste der {@link Statistik}-Objekte zum angegebenen Spiel,
     *         oder eine leere Liste bei Fehlern oder wenn keine Statistiken vorhanden sind.
     */
    public List<Statistik> getBySpiel(Spiele spiel) {
        return TryCatchUtil.tryCatchList(() -> dao.findBySpiel(spiel));
    }
}
