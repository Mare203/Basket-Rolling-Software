/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.utils.TryCatchUtil;

/**
 * Service-Klasse zur Verwaltung von {@link Training}-Entitäten.
 * Diese Klasse erweitert {@link BaseService} und bietet eine Methode zur
 * Suche von Trainingseinheiten anhand ihrer Dauer.
 * 
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}.
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
     * @return Liste der passenden {@link Training}-Objekte
     */
    public List<Training> getByDauer(int dauer) {
        return TryCatchUtil.tryCatchList(() -> dao.findByDauer(dauer));
    }
}
