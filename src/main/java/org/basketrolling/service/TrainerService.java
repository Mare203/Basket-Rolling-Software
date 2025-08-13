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
 * <p>
 * Diese Klasse erweitert {@link BaseService} und stellt zusätzliche
 * Suchfunktionen bereit, um Trainer gezielt anhand von Vorname, Nachname oder
 * Aktivitätsstatus zu finden.
 * </p>
 * <p>
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}, sodass
 * Ausnahmen einheitlich behandelt und konsistente Rückgabewerte (z. B. leere
 * Listen) gewährleistet werden.
 * </p>
 *
 * @author Marko
 */
public class TrainerService extends BaseService<Trainer> {

    private final TrainerDAO dao;

    /**
     * Erstellt einen neuen {@code TrainerService}.
     *
     * @param dao das {@link TrainerDAO}-Objekt für Datenbankabfragen zu
     * Trainern
     */
    public TrainerService(TrainerDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht Trainer anhand ihres Vornamens (case-insensitive, Teilstring
     * erlaubt).
     *
     * @param vorname der gesuchte Vorname oder ein Teil davon
     * @return eine {@link List} mit passenden {@link Trainer}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Trainer> getByVorname(String vorname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByVorname(vorname));
    }

    /**
     * Sucht Trainer anhand ihres Nachnamens (case-insensitive, Teilstring
     * erlaubt).
     *
     * @param nachname der gesuchte Nachname oder ein Teil davon
     * @return eine {@link List} mit passenden {@link Trainer}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Trainer> getByNachname(String nachname) {
        return TryCatchUtil.tryCatchList(() -> dao.findByNachname(nachname));
    }

    /**
     * Sucht Trainer anhand ihres Aktivitätsstatus.
     *
     * @param aktiv {@code true} für aktive Trainer, {@code false} für inaktive
     * Trainer
     * @return eine {@link List} mit passenden {@link Trainer}-Objekten, oder
     * eine leere Liste, falls keine Treffer vorhanden sind
     */
    public List<Trainer> getByAktiv(boolean aktiv) {
        return TryCatchUtil.tryCatchList(() -> dao.findByAktiv(aktiv));
    }
}
