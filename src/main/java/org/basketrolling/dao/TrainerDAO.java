/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Trainer;

/**
 * DAO-Klasse für den Zugriff auf {@link Trainer}-Entitäten. Diese Klasse
 * erweitert {@link BaseDAO} und bietet Methoden zur Suche nach Vorname,
 * Nachname und Aktivitätsstatus von Trainern.
 *
 * @author Marko
 */
public class TrainerDAO extends BaseDAO<Trainer> {

    /**
     * Konstruktor, der die {@link Trainer}-Klasse an den generischen DAO
     * übergibt.
     */
    public TrainerDAO() {
        super(Trainer.class);
    }

    /**
     * Sucht {@link Trainer}-Einträge anhand des Vornamens (case-insensitive,
     * Teilstring möglich).
     *
     * @param vorname der gesuchte Vorname oder Teil des Vornamens
     * @return Liste der passenden {@link Trainer}-Objekte
     */
    public List<Trainer> findByVorname(String vorname) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT t FROM Trainer t WHERE LOWER(t.vorname) LIKE LOWER(:vorname)";
            return em.createQuery(jpql, Trainer.class)
                    .setParameter("vorname", "%" + vorname + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht {@link Trainer}-Einträge anhand des Nachnamens (case-insensitive,
     * Teilstring möglich).
     *
     * @param nachname der gesuchte Nachname oder Teil des Nachnamens
     * @return Liste der passenden {@link Trainer}-Objekte
     */
    public List<Trainer> findByNachname(String nachname) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT t FROM Trainer t WHERE LOWER(t.nachname) LIKE LOWER(:nachname)";
            return em.createQuery(jpql, Trainer.class)
                    .setParameter("nachname", "%" + nachname + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht {@link Trainer}-Einträge anhand ihres Aktivitätsstatus.
     *
     * @param aktiv {@code true} für aktive Trainer, {@code false} für inaktive
     * Trainer
     * @return Liste der passenden {@link Trainer}-Objekte
     */
    public List<Trainer> findByAktiv(boolean aktiv) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT t FROM Trainer t WHERE t.aktiv = :aktiv";
            return em.createQuery(jpql, Trainer.class)
                    .setParameter("aktiv", aktiv)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
