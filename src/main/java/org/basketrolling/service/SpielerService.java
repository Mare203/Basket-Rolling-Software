/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Spieler}-Entitäten.
 * <p>
 * Diese Klasse erweitert {@link BaseService} und stellt zusätzliche
 * Suchmethoden bereit, um Spieler gezielt anhand von Namen, Körpergröße,
 * Mannschaftszugehörigkeit oder Aktivitätsstatus zu finden.
 * </p>
 * <p>
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}, sodass
 * Ausnahmen kontrolliert behandelt und konsistente Rückgabewerte (z. B. leere
 * Listen) gewährleistet werden.
 * </p>
 *
 * @author Marko
 */
public class SpielerService extends BaseService<Spieler> {

    private final SpielerDAO dao;

    /**
     * Erstellt einen neuen {@code SpielerService}.
     *
     * @param dao das {@link SpielerDAO}-Objekt für Datenbankabfragen zu
     * Spielern
     */
    public SpielerService(SpielerDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht Spieler anhand des Vornamens (case-insensitive, Teilstring
     * erlaubt).
     *
     * @param vorname der gesuchte Vorname oder ein Teil davon
     * @return eine {@link List} mit passenden {@link Spieler}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Spieler> getByVorname(String vorname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByVorname(vorname));
    }

    /**
     * Sucht Spieler anhand des Nachnamens (case-insensitive, Teilstring
     * erlaubt).
     *
     * @param nachname der gesuchte Nachname oder ein Teil davon
     * @return eine {@link List} mit passenden {@link Spieler}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Spieler> getByNachname(String nachname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByNachname(nachname));
    }

    /**
     * Sucht Spieler mit exakt der angegebenen Körpergröße.
     *
     * @param groesse die exakte Körpergröße in Zentimetern
     * @return eine {@link List} mit passenden {@link Spieler}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Spieler> getByGroesse(double groesse) {
        return TryCatchUtil.tryCatchList(() -> dao.findByGroesse(groesse));
    }

    /**
     * Sucht Spieler mit einer Mindestgröße (größer oder gleich dem angegebenen
     * Wert).
     *
     * @param minGroesse die Mindestgröße in Zentimetern
     * @return eine {@link List} mit passenden {@link Spieler}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Spieler> getByGroesseAb(double minGroesse) {
        return TryCatchUtil.tryCatchList(() -> dao.findByGroesseAb(minGroesse));
    }

    /**
     * Sucht Spieler mit einer maximalen Größe (kleiner oder gleich dem
     * angegebenen Wert).
     *
     * @param maxGroesse die Maximalgröße in Zentimetern
     * @return eine {@link List} mit passenden {@link Spieler}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Spieler> getByGroesseBis(double maxGroesse) {
        return TryCatchUtil.tryCatchList(() -> dao.findByGroesseBis(maxGroesse));
    }

    /**
     * Sucht Spieler anhand ihres Aktivitätsstatus und ihrer Mannschaft.
     *
     * @param aktiv {@code true} für aktive Spieler, {@code false} für inaktive
     * Spieler
     * @param mannschaft die {@link MannschaftIntern}, nach der gefiltert werden
     * soll
     * @return eine {@link List} mit passenden {@link Spieler}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Spieler> getByAktivUndMannschaft(boolean aktiv, MannschaftIntern mannschaft) {
        return TryCatchUtil.tryCatchList(() -> dao.findByAktivUndMannschaft(aktiv, mannschaft));
    }

    /**
     * Sucht alle Spieler einer bestimmten Mannschaft.
     *
     * @param mannschaft die {@link MannschaftIntern}, deren Spieler gesucht
     * werden
     * @return eine {@link List} mit passenden {@link Spieler}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Spieler> getByMannschaft(MannschaftIntern mannschaft) {
        return TryCatchUtil.tryCatchList(() -> dao.findByMannschaft(mannschaft));
    }
}
