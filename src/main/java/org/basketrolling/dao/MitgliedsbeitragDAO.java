/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.beans.Spieler;

/**
 * DAO-Klasse für den Zugriff auf Mitgliedsbeiträge in der Datenbank.
 * Diese Klasse stellt Methoden zur Verfügung, um Mitgliedsbeiträge
 * anhand verschiedener Kriterien abzufragen.
 *
 * Erbt die Standard-CRUD-Operationen von BaseDAO.
 * 
 * @author Marko
 */
public class MitgliedsbeitragDAO extends BaseDAO<Mitgliedsbeitrag> {

    /**
     * Konstruktor für MitgliedsbeitragDAO.
     * Übermittelt die Klasse {@link Mitgliedsbeitrag} an die Basisklasse.
     */
    public MitgliedsbeitragDAO() {
        super(Mitgliedsbeitrag.class);
    }

    /**
     * Findet alle Mitgliedsbeiträge mit einem bestimmten Betrag.
     *
     * @param betrag Der zu suchende Betrag.
     * @return Liste der Mitgliedsbeiträge mit diesem Betrag, 
     *         oder eine leere Liste, wenn keine vorhanden sind.
     */
    public List<Mitgliedsbeitrag> findByBetrag(double betrag) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM Mitgliedsbeitrag m WHERE m.betrag = :betrag";
            return em.createQuery(jpql, Mitgliedsbeitrag.class)
                     .setParameter("betrag", betrag)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}