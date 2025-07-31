/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Training}-Entitäten. Diese Klasse
 * erweitert {@link BaseService} und bietet Methoden zur Suche von
 * Trainingseinheiten anhand verschiedener Kriterien wie Dauer, Halle oder
 * aktuellem Wochentag.
 *
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}, wodurch
 * Laufzeitfehler abgefangen und mit einer leeren Liste beantwortet werden.
 *
 * @author Marko
 */
public class TrainingService extends BaseService<Training> {

    private final TrainingDAO dao;

    /**
     * Konstruktor, der das {@link TrainingDAO} übergibt.
     *
     * @param dao das DAO-Objekt für Trainingseinheiten
     */
    public TrainingService(TrainingDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht {@link Training}-Einträge mit einer bestimmten Dauer.
     *
     * @param dauer die Trainingsdauer in Minuten
     * @return Liste der passenden {@link Training}-Objekte; im Fehlerfall eine
     * leere Liste
     */
    public List<Training> getByDauer(int dauer) {
        return TryCatchUtil.tryCatchList(() -> dao.findByDauer(dauer));
    }

    /**
     * Ruft alle Trainings für den aktuellen Wochentag ab.
     *
     * @return Liste der heutigen Trainings; im Fehlerfall eine leere Liste
     */
    public List<Training> getHeutigeTrainings() {
        return TryCatchUtil.tryCatchList(() -> dao.findHeutigeTrainings());
    }

    /**
     * Sucht alle {@link Training}-Einträge, die in einer bestimmten
     * {@link Halle} stattfinden.
     *
     * @param halle die Halle, in der das Training stattfindet
     * @return Liste der Trainings in dieser Halle; im Fehlerfall eine leere
     * Liste
     */
    public List<Training> getByHalle(Halle halle) {
        return TryCatchUtil.tryCatchList(() -> dao.findByHalle(halle));
    }

    /**
     * Sucht alle {@link Training}-Einträge, die einer bestimmten
     * {@link MannschaftIntern} zugeordnet sind.
     *
     * @param mannschaft die Mannschaft, für die Trainings gesucht werden
     * @return Liste der Trainings dieser Mannschaft; im Fehlerfall eine leere
     * Liste
     */
    public List<Training> getByMannschaft(MannschaftIntern mannschaft) {
        return TryCatchUtil.tryCatchList(() -> dao.findByMannschaftIntern(mannschaft));
    }
}
