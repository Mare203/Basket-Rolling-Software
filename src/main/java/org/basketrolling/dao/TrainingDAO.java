/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.basketrolling.beans.Training;

/**
 * DAO-Klasse für den Zugriff auf {@link Training}-Entitäten. Diese Klasse
 * erweitert {@link BaseDAO} und bietet eine Methode zur Suche von
 * Trainingseinheiten anhand der Trainingsdauer.
 *
 * @author Marko
 */
public class TrainingDAO extends BaseDAO<Training> {

    /**
     * Konstruktor, der die {@link Training}-Klasse an den generischen DAO
     * übergibt.
     */
    public TrainingDAO() {
        super(Training.class);
    }

    /**
     * Sucht {@link Training}-Einträge mit einer bestimmten Dauer in Minuten.
     *
     * @param dauer die Dauer des Trainings in Minuten
     * @return Liste der {@link Training}-Objekte mit der angegebenen Dauer
     */
    public List<Training> findByDauer(int dauer) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT t FROM Training t WHERE t.dauerInMin = :dauer";
            return em.createQuery(jpql, Training.class)
                    .setParameter("dauer", dauer)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Ruft alle Trainings ab, die am aktuellen Wochentag stattfinden.
     *
     * Die Methode führt eine JPQL-Abfrage aus, die Trainings auswählt, bei
     * denen der Wochentag dem aktuellen Tag entspricht. Zusätzlich werden die
     * zugehörigen {@code mannschaftIntern}-Daten mit einem LEFT JOIN FETCH
     * geladen, um Lazy-Loading zu vermeiden.
     *
     * @return Liste aller Trainings, die am heutigen Wochentag stattfinden
     */
    public List<Training> findHeutigeTrainings() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT t FROM Training t LEFT JOIN FETCH t.mannschaftIntern WHERE t.wochentag = :wochentag";
            return em.createQuery(jpql, Training.class)
                    .setParameter("wochentag", LocalDate.now().getDayOfWeek().toString())
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
