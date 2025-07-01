/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Trainer}-Entitäten.
 * Diese Klasse erweitert {@link BaseService} und bietet zusätzliche
 * Suchfunktionen nach Vorname, Nachname und Aktivitätsstatus.
 * 
 * Die Fehlerbehandlung erfolgt über {@link TryCatchUtil}, um Ausnahmen einheitlich zu behandeln.
 * 
 * @author Marko
 */
public class TrainerService extends BaseService<Trainer> {

    private final TrainerDAO dao;

    /**
     * Konstruktor, der das {@link TrainerDAO} übergibt.
     *
     * @param dao das DAO-Objekt für Trainer
     */
    public TrainerService(TrainerDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht Trainer anhand ihres Vornamens (case-insensitive, Teilstring erlaubt).
     *
     * @param vorname der gesuchte Vorname oder Teil davon
     * @return Liste der passenden {@link Trainer}-Objekte
     */
    public List<Trainer> getByVorname(String vorname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByVorname(vorname));
    }

    /**
     * Sucht Trainer anhand ihres Nachnamens (case-insensitive, Teilstring erlaubt).
     *
     * @param nachname der gesuchte Nachname oder Teil davon
     * @return Liste der passenden {@link Trainer}-Objekte
     */
    public List<Trainer> getByNachname(String nachname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByNachname(nachname));
    }

    /**
     * Sucht Trainer nach ihrem Aktivitätsstatus.
     *
     * @param aktiv {@code true} für aktive Trainer, {@code false} für inaktive
     * @return Liste der passenden {@link Trainer}-Objekte
     */
    public List<Trainer> getByAktiv(boolean aktiv) {
        return TryCatchUtil.tryCatchList(() -> dao.findByAktiv(aktiv));
    }
}
