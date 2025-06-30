/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Halle;

/**
 * DAO-Klasse für den Zugriff auf {@link Halle}-Entitäten.
 * Diese Klasse erweitert {@link BaseDAO} und bietet Methoden zur Suche
 * nach Namen, Postleitzahl und Ort.
 * 
 * @author Marko
 */
public class HalleDAO extends BaseDAO<Halle> {

    /**
     * Konstruktor, der die {@link Halle}-Klasse an den generischen DAO übergibt.
     */
    public HalleDAO() {
        super(Halle.class);
    }

    /**
     * Sucht Hallen anhand eines Namens (case-insensitive, Teilstring erlaubt).
     *
     * @param name der Name oder Teil des Namens der Halle
     * @return Liste der passenden {@link Halle}-Objekte
     */
    public List<Halle> findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            String jqpl = "SELECT h from Halle h WHERE LOWER(h.name) LIKE LOWER(:name)";
            return em.createQuery(jqpl, Halle.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht Hallen anhand der Postleitzahl.
     *
     * @param plz die Postleitzahl
     * @return Liste der passenden {@link Halle}-Objekte
     */
    public List<Halle> findByPostleitzahl(int plz) {
        EntityManager em = getEntityManager();
        try {
            String jqpl = "SELECT h FROM Halle h WHERE h.plz = :plz";
            return em.createQuery(jqpl, Halle.class)
                    .setParameter("plz", plz)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht Hallen anhand des Ortsnamens (case-insensitive, Teilstring erlaubt).
     *
     * @param ort der Ort oder Teil des Ortsnamens
     * @return Liste der passenden {@link Halle}-Objekte
     */
    public List<Halle> findByOrt(String ort) {
        EntityManager em = getEntityManager();
        try {
            String jqpl = "SELECT h FROM Halle h WHERE LOWER(h.ort) LIKE LOWER(:ort)";
            return em.createQuery(jqpl, Halle.class)
                    .setParameter("ort", ort)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
