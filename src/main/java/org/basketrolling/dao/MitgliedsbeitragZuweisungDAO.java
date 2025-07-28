/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spieler;

/**
 * DAO-Klasse für den Zugriff auf {@link MitgliedsbeitragZuweisung}-Entitäten.
 * Diese Klasse erweitert {@link BaseDAO} und kann für benutzerdefinierte
 * Abfragen erweitert werden.
 *
 * @author Marko
 */
public class MitgliedsbeitragZuweisungDAO extends BaseDAO<MitgliedsbeitragZuweisung> {

    /**
     * Konstruktor, der die {@link MitgliedsbeitragZuweisung}-Klasse an den
     * generischen DAO übergibt.
     */
    public MitgliedsbeitragZuweisungDAO() {
        super(MitgliedsbeitragZuweisung.class);
    }

    public List<MitgliedsbeitragZuweisung> findOffeneBeitraege() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT z FROM MitgliedsbeitragZuweisung z JOIN FETCH z.spieler WHERE z.bezahlt = false";
            return em.createQuery(jpql, MitgliedsbeitragZuweisung.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<MitgliedsbeitragZuweisung> findBySpieler(Spieler spieler) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT z FROM MitgliedsbeitragZuweisung z WHERE z.spieler = :spieler";
            return em.createQuery(jpql, MitgliedsbeitragZuweisung.class)
                    .setParameter("spieler", spieler)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<MitgliedsbeitragZuweisung> findAktiveBySpieler(Spieler spieler) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT z FROM MitgliedsbeitragZuweisung z WHERE z.spieler = :spieler";
            return em.createQuery(jpql, MitgliedsbeitragZuweisung.class)
                    .setParameter("spieler", spieler)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
