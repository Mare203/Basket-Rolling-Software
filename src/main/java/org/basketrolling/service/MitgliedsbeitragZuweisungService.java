/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.MitgliedsbeitragZuweisungDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von
 * {@link MitgliedsbeitragZuweisung}-Entitäten.
 * <p>
 * Diese Klasse erweitert {@link BaseService} und stellt Methoden zur Verfügung,
 * um Mitgliedsbeiträge von Spielern zu verwalten, offene Beiträge abzufragen
 * sowie neue Zuweisungen zu aktivieren. Die Fehlerbehandlung erfolgt zentral
 * über {@link TryCatchUtil}.
 * </p>
 *
 * @author Marko
 */
public class MitgliedsbeitragZuweisungService extends BaseService<MitgliedsbeitragZuweisung> {

    private final MitgliedsbeitragZuweisungDAO dao;

    /**
     * Erstellt einen neuen {@code MitgliedsbeitragZuweisungService}.
     *
     * @param dao das {@link MitgliedsbeitragZuweisungDAO}-Objekt zur Ausführung
     * von Datenbankoperationen
     */
    public MitgliedsbeitragZuweisungService(MitgliedsbeitragZuweisungDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Ruft alle offenen Mitgliedsbeiträge aus der Datenbank ab.
     *
     * @return eine {@link List} mit allen offenen
     * {@link MitgliedsbeitragZuweisung}-Einträgen, oder eine leere Liste, falls
     * keine offenen Beiträge vorhanden sind
     */
    public List<MitgliedsbeitragZuweisung> getOffeneBeitraege() {
        return TryCatchUtil.tryCatchList(() -> dao.findOffeneBeitraege());
    }

    /**
     * Ruft alle Mitgliedsbeiträge eines bestimmten Spielers ab.
     *
     * @param spieler der {@link Spieler}, dessen Beiträge gesucht werden
     * @return eine {@link List} mit den entsprechenden
     * {@link MitgliedsbeitragZuweisung}-Einträgen, oder eine leere Liste, falls
     * keine vorhanden sind
     */
    public List<MitgliedsbeitragZuweisung> getBySpieler(Spieler spieler) {
        return TryCatchUtil.tryCatchList(() -> dao.findBySpieler(spieler));
    }

    /**
     * Ruft alle aktiven Mitgliedsbeiträge eines bestimmten Spielers ab.
     *
     * @param spieler der {@link Spieler}, dessen aktive Beiträge gesucht werden
     * @return eine {@link List} mit den aktiven
     * {@link MitgliedsbeitragZuweisung}-Einträgen, oder eine leere Liste, falls
     * keine vorhanden sind
     */
    public List<MitgliedsbeitragZuweisung> getAktiveBySpieler(Spieler spieler) {
        return TryCatchUtil.tryCatchList(() -> dao.findAktiveBySpieler(spieler));
    }

    /**
     * Deaktiviert alle bisherigen Mitgliedsbeitragszuweisungen für einen
     * Spieler und erstellt eine neue, die optional als bezahlt markiert werden
     * kann.
     *
     * @param spieler der {@link Spieler}, für den die neue Zuweisung erstellt
     * wird
     * @param beitrag der neue {@link Mitgliedsbeitrag}, der zugewiesen werden
     * soll
     * @param bezahlt {@code true}, wenn der Beitrag sofort als bezahlt markiert
     * werden soll, andernfalls {@code false}
     */
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
