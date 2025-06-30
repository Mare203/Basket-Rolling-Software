/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Training;

/**
 * DAO-Klasse für den Zugriff auf {@link Training}-Entitäten.
 * Diese Klasse erweitert {@link BaseDAO} und bietet eine Methode zur
 * Suche von Trainingseinheiten anhand der Trainingsdauer.
 * 
 * @author Marko
 */
public class TrainingDAO extends BaseDAO<Training> {

    /**
     * Konstruktor, der die {@link Training}-Klasse an den generischen DAO übergibt.
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
}