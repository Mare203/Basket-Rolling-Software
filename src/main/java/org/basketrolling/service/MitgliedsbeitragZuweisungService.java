/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MitgliedsbeitragZuweisungDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Liga}-Entitäten. Diese Klasse
 * erweitert {@link BaseService} und bietet eine zusätzliche Methode zur Suche
 * von Ligen anhand ihres Namens.
 *
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}.
 *
 * @author Marko
 */
public class MitgliedsbeitragZuweisungService extends BaseService<MitgliedsbeitragZuweisung> {

    private final MitgliedsbeitragZuweisungDAO dao;

    /**
     * Konstruktor, der das {@link LigaDAO} übergibt.
     *
     * @param dao das DAO-Objekt zur Datenbankabfrage
     */
    public MitgliedsbeitragZuweisungService(MitgliedsbeitragZuweisungDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public List<MitgliedsbeitragZuweisung> getOffeneBeitraege() {
        return TryCatchUtil.tryCatchList(() -> dao.findOffeneBeitraege());
    }

    public List<MitgliedsbeitragZuweisung> getBySpieler(Spieler spieler) {
        return TryCatchUtil.tryCatchList(() -> dao.findBySpieler(spieler));
    }

    public List<MitgliedsbeitragZuweisung> getAktiveBySpieler(Spieler spieler) {
        return TryCatchUtil.tryCatchList(() -> dao.findAktiveBySpieler(spieler));
    }

    public void aktivereNeueZuweisung(Spieler spieler, Mitgliedsbeitrag beitrag, boolean bezahlt) {
        List<MitgliedsbeitragZuweisung> alteZuweisungen = dao.findBySpieler(spieler);
        for (MitgliedsbeitragZuweisung alt : alteZuweisungen) {
            alt.setAktiv(false);
            dao.update(alt);
        }

        MitgliedsbeitragZuweisung neu = new MitgliedsbeitragZuweisung();
        neu.setSpieler(spieler);
        neu.setMitgliedsbeitrag(beitrag);
        neu.setBezahlt(bezahlt);
        neu.setAktiv(true);
        dao.save(neu);
    }
}
