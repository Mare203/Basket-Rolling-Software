/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;

/**
 * DAO-Klasse für den Zugriff auf {@link Spiele}-Entitäten. Diese Klasse
 * erweitert {@link BaseDAO} und bietet spezifische Methoden zur Suche nach
 * Spielen anhand bestimmter Kriterien.
 *
 * @author Marko
 */
public class SpieleDAO extends BaseDAO<Spiele> {

    /**
     * Konstruktor, der die {@link Spiele}-Klasse an den generischen DAO
     * übergibt.
     */
    public SpieleDAO() {
        super(Spiele.class);
    }

    /**
     * Sucht {@link Spiele}-Einträge, die an einem bestimmten Datum stattfinden.
     *
     * @param datum das Datum, nach dem gesucht werden soll
     * @return Liste der Spiele, die an diesem Datum stattfinden
     */
    public List<Spiele> findByDatum(LocalDate datum) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spiele s WHERE s.datum = :datum";
            return em.createQuery(jpql, Spiele.class)
                    .setParameter("datum", datum)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht alle {@link Spiele}-Einträge, bei denen eine bestimmte interne
     * Mannschaft beteiligt ist.
     *
     * @param mannschaft die {@link MannschaftIntern}, für die Spiele gesucht
     * werden sollen
     * @return Liste der Spiele dieser Mannschaft, sortiert nach Datum
     */
    public List<Spiele> findByMannschaftIntern(MannschaftIntern mannschaft) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spiele s WHERE s.mannschaftIntern = :mannschaft ORDER BY s.datum";
            return em.createQuery(jpql, Spiele.class)
                    .setParameter("mannschaft", mannschaft)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht alle {@link Spiele}-Einträge, bei denen eine bestimmte externe
     * Mannschaft beteiligt ist.
     *
     * @param mannschaft die {@link MannschaftExtern}, für die Spiele gesucht
     * werden sollen
     * @return Liste der Spiele dieser Mannschaft, sortiert nach Datum
     */
    public List<Spiele> findByMannschaftExtern(MannschaftExtern mannschaft) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spiele s WHERE s.mannschaftExtern = :mannschaft ORDER BY s.datum";
            return em.createQuery(jpql, Spiele.class)
                    .setParameter("mannschaft", mannschaft)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Berechnet den durchschnittlich erzielten Punktestand (internPunkte) aller
     * Spiele einer bestimmten internen Mannschaft.
     *
     * @param mannschaft die {@link MannschaftIntern}, für die der Punkteschnitt
     * berechnet werden soll
     * @return der durchschnittliche Punktestand als {@code Double}, oder
     * {@code null}, wenn keine Spiele vorhanden sind
     */
    public Double findPunkteschnittProMannschaft(MannschaftIntern mannschaft) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT AVG(s.internPunkte * 1.0) FROM Spiele s WHERE s.mannschaftIntern = :mannschaft";
            return em.createQuery(jpql, Double.class)
                    .setParameter("mannschaft", mannschaft)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht alle {@link Spiele}-Einträge, die in einer bestimmten {@link Halle}
     * stattfinden.
     *
     * @param halle die {@link Halle}, in der die Spiele stattfinden
     * @return Liste der Spiele in der angegebenen Halle
     */
    public List<Spiele> findByHalle(Halle halle) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spiele s WHERE s.halle = :halle ORDER BY s.datum";
            return em.createQuery(jpql, Spiele.class)
                    .setParameter("halle", halle)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
