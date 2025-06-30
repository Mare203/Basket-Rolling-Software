/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.MannschaftExtern;

/**
 * DAO-Klasse für den Zugriff auf {@link MannschaftExtern}-Entitäten.
 * Diese Klasse erweitert {@link BaseDAO} und bietet eine Methode
 * zur Suche nach Mannschaftsnamen.
 * 
 * @author Marko
 */
public class MannschaftExternDAO extends BaseDAO<MannschaftExtern> {
    
    /**
     * Konstruktor, der die {@link MannschaftExtern}-Klasse an den generischen DAO übergibt.
     */
    public MannschaftExternDAO() {
        super(MannschaftExtern.class);
    }

    /**
     * Sucht {@link MannschaftExtern}-Einträge, deren Name (case-insensitive)
     * den übergebenen Suchbegriff enthält.
     *
     * @param name der Name oder Teil des Namens der externen Mannschaft
     * @return Liste der passenden {@link MannschaftExtern}-Objekte
     */
    public List<MannschaftExtern> findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM MannschaftExtern m WHERE LOWER(m.name) LIKE LOWER(:name)";
            return em.createQuery(jpql, MannschaftExtern.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}