/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Liga;

/**
 * DAO-Klasse für den Zugriff auf {@link Liga}-Entitäten.
 * Diese Klasse erweitert {@link BaseDAO} und bietet eine Methode zur
 * Suche von Ligen anhand des Namens.
 * 
 * @author Marko
 */
public class LigaDAO extends BaseDAO<Liga> {

    /**
     * Konstruktor, der die {@link Liga}-Klasse an den generischen DAO übergibt.
     */
    public LigaDAO() {
        super(Liga.class);
    }

    /**
     * Sucht {@link Liga}-Einträge, deren Name (case-insensitive) den übergebenen String enthält.
     *
     * @param name der gesuchte Name oder Teil des Namens der Liga
     * @return Liste der passenden {@link Liga}-Objekte
     */
    public List<Liga> findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT l FROM Liga l WHERE LOWER(l.name) LIKE LOWER(:name)";
            return em.createQuery(jpql, Liga.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}