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
 * Service-Klasse zur Verwaltung von {@link Spieler}-Entitäten. Diese Klasse
 * erweitert {@link BaseService} und bietet zusätzliche Methoden zur gezielten
 * Suche nach Spielern anhand von Namen, Größe oder Aktivitätsstatus.
 *
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}.
 *
 * @author Marko
 */
public class SpielerService extends BaseService<Spieler> {

    private final SpielerDAO dao;

    /**
     * Konstruktor, der das {@link SpielerDAO} übergibt.
     *
     * @param dao das DAO-Objekt für Spieler
     */
    public SpielerService(SpielerDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht Spieler anhand des Vornamens (case-insensitive, Teilstring
     * erlaubt).
     *
     * @param vorname der gesuchte Vorname oder Teil davon
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> getByVorname(String vorname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByVorname(vorname));
    }

    /**
     * Sucht Spieler anhand des Nachnamens (case-insensitive, Teilstring
     * erlaubt).
     *
     * @param nachname der gesuchte Nachname oder Teil davon
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> getByNachname(String nachname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByNachname(nachname));
    }

    /**
     * Sucht Spieler mit exakt der angegebenen Körpergröße.
     *
     * @param groesse die exakte Körpergröße in cm
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> getByGroesse(double groesse) {
        return TryCatchUtil.tryCatchList(() -> dao.findByGroesse(groesse));
    }

    /**
     * Sucht Spieler mit einer Mindestgröße (größer oder gleich dem angegebenen
     * Wert).
     *
     * @param minGroesse die Mindestgröße in cm
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> getByGroesseAb(double minGroesse) {
        return TryCatchUtil.tryCatchList(() -> dao.findByGroesseAb(minGroesse));
    }

    /**
     * Sucht Spieler mit einer maximalen Größe (kleiner oder gleich dem
     * angegebenen Wert).
     *
     * @param maxGroesse die Maximalgröße in cm
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> getByGroesseBis(double maxGroesse) {
        return TryCatchUtil.tryCatchList(() -> dao.findByGroesseBis(maxGroesse));
    }

    /**
     * Sucht Spieler anhand ihres Aktivitätsstatus.
     *
     * @param aktiv {@code true} für aktive Spieler, {@code false} für inaktive
     * Spieler
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> getByAktiv(boolean aktiv) {
        return TryCatchUtil.tryCatchList(() -> dao.findByAktiv(aktiv));
    }

    public List<Spieler> getByMannschaft(MannschaftIntern mannschaft) {
        return TryCatchUtil.tryCatchList(() -> dao.findByMannschaft(mannschaft));
    }
}
