/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.basketrolling.beans.Spiele;

/**
 * DAO-Klasse für den Zugriff auf {@link Spiele}-Entitäten. Diese Klasse
 * erweitert {@link BaseDAO} und bietet eine Methode zur Suche von Spielen
 * anhand eines bestimmten Datums.
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
}
