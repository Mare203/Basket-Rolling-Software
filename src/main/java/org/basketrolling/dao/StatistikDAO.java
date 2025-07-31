/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Statistik;

/**
 * DAO-Klasse für den Zugriff auf Spielerstatistiken in der Datenbank. Diese
 * Klasse ermöglicht den Abruf von Statistiken, die einem bestimmten Spiel
 * zugeordnet sind.
 *
 * Erbt die Standard-CRUD-Methoden von {@link BaseDAO}.
 *
 * @author Marko
 */
public class StatistikDAO extends BaseDAO<Statistik> {

    /**
     * Konstruktor für StatistikDAO. Übergibt die {@link Statistik}-Klasse an
     * die Basisklasse.
     */
    public StatistikDAO() {
        super(Statistik.class);
    }

    /**
     * Findet alle Statistiken, die zu einem bestimmten Spiel gehören.
     *
     * @param spiel Das {@link Spiele}-Objekt, für das die Statistiken abgerufen
     * werden sollen.
     * @return Eine Liste der zugehörigen {@link Statistik}-Objekte, oder eine
     * leere Liste, wenn keine Statistiken gefunden werden.
     */
    public List<Statistik> findBySpiel(Spiele spiel) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Statistik s WHERE s.spiel = :spiel";
            return em.createQuery(jpql, Statistik.class)
                    .setParameter("spiel", spiel)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> findTop5Scorer() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s.spieler, SUM(s.punkte) * 1.0 / COUNT (DISTINCT s.spiel), COUNT(DISTINCT s.spiel)"
                    + " FROM Statistik s GROUP BY s.spieler"
                    + " ORDER BY SUM(s.punkte) * 1.0 / COUNT(DISTINCT s.spiel) DESC";
            return em.createQuery(jpql, Object[].class).setMaxResults(5).getResultList();
        } finally {
            em.close();
        }
    }

    public Object[] findTopScorerByMannschaft(MannschaftIntern mannschaft) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s.spieler, SUM(s.punkte) * 1.0 / COUNT(DISTINCT s.spiel), COUNT(DISTINCT s.spiel) "
                    + "FROM Statistik s "
                    + "WHERE s.spiel.mannschaftIntern = :mannschaft "
                    + "GROUP BY s.spieler "
                    + "ORDER BY SUM(s.punkte) * 1.0 / COUNT(DISTINCT s.spiel) DESC";

            List<Object[]> result = em.createQuery(jpql, Object[].class)
                    .setParameter("mannschaft", mannschaft)
                    .setMaxResults(1)
                    .getResultList();

            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
}
