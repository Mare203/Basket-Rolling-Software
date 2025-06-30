/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Spieler;

/**
 * DAO-Klasse für den Zugriff auf {@link Spieler}-Entitäten. Diese Klasse
 * erweitert {@link BaseDAO} und bietet Methoden zur Suche nach Vorname,
 * Nachname, Größe sowie Aktivitätsstatus.
 *
 * @author Marko
 */
public class SpielerDAO extends BaseDAO<Spieler> {

    /**
     * Konstruktor, der die {@link Spieler}-Klasse an den generischen DAO
     * übergibt.
     */
    public SpielerDAO() {
        super(Spieler.class);
    }

    /**
     * Sucht Spieler anhand des Vornamens (case-insensitive, Teilstring
     * möglich).
     *
     * @param vorname der gesuchte Vorname oder ein Teil davon
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> findByVorname(String vorname) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spieler s WHERE LOWER(s.vorname) LIKE LOWER(:vorname)";
            return em.createQuery(jpql, Spieler.class)
                    .setParameter("vorname", "%" + vorname + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht Spieler anhand des Nachnamens (case-insensitive, Teilstring
     * möglich).
     *
     * @param nachname der gesuchte Nachname oder ein Teil davon
     * @return Liste der passenden {@link Spieler}-Objekte
     */
    public List<Spieler> findByNachname(String nachname) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spieler s WHERE LOWER(s.nachname) LIKE LOWER(:nachname)";
            return em.createQuery(jpql, Spieler.class)
                    .setParameter("nachname", "%" + nachname + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht Spieler mit einer exakten Körpergröße.
     *
     * @param groesse die exakte gesuchte Größe
     * @return Liste der Spieler mit genau dieser Größe
     */
    public List<Spieler> findByGroesse(double groesse) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spieler s WHERE s.groesse = :groesse";
            return em.createQuery(jpql, Spieler.class)
                    .setParameter("groesse", groesse)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht Spieler mit einer Mindestgröße (größer oder gleich).
     *
     * @param minGroesse die untere Grenze für die Größe
     * @return Liste der Spieler mit Größe ab dem angegebenen Wert
     */
    public List<Spieler> findByGroesseAb(double minGroesse) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spieler s WHERE s.groesse >= :minGroesse";
            return em.createQuery(jpql, Spieler.class)
                    .setParameter("minGroesse", minGroesse)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht Spieler mit einer maximalen Größe (kleiner oder gleich).
     *
     * @param maxGroesse die obere Grenze für die Größe
     * @return Liste der Spieler mit Größe bis zum angegebenen Wert
     */
    public List<Spieler> findByGroesseBis(double maxGroesse) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spieler s WHERE s.groesse <= :maxGroesse";
            return em.createQuery(jpql, Spieler.class)
                    .setParameter("maxGroesse", maxGroesse)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht Spieler nach Aktivitätsstatus.
     *
     * @param aktiv {@code true} für aktive Spieler, {@code false} für inaktive
     * @return Liste der passenden Spieler
     */
    public List<Spieler> findByAktiv(boolean aktiv) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Spieler s WHERE s.aktiv = :aktiv";
            return em.createQuery(jpql, Spieler.class)
                    .setParameter("aktiv", aktiv)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
