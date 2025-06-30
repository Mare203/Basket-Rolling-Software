/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.MannschaftIntern;

/**
 * DAO-Klasse für den Zugriff auf {@link MannschaftIntern}-Entitäten.
 * Diese Klasse erweitert {@link BaseDAO} und bietet eine Methode
 * zur Suche interner Mannschaften anhand ihres Namens.
 * 
 * @author Marko
 */
public class MannschaftInternDAO extends BaseDAO<MannschaftIntern> {

    /**
     * Konstruktor, der die {@link MannschaftIntern}-Klasse an den generischen DAO übergibt.
     */
    public MannschaftInternDAO() {
        super(MannschaftIntern.class);
    }

    /**
     * Sucht {@link MannschaftIntern}-Einträge, deren Name (case-insensitive)
     * den übergebenen Suchbegriff enthält.
     *
     * @param name der Name oder Teil des Namens der internen Mannschaft
     * @return Liste der passenden {@link MannschaftIntern}-Objekte
     */
    public List<MannschaftIntern> findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM MannschaftIntern m WHERE LOWER(m.name) LIKE LOWER(:name)";
            return em.createQuery(jpql, MannschaftIntern.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
