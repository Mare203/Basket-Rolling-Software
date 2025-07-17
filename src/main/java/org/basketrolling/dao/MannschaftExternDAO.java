/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftExtern;

/**
 * DAO-Klasse für den Zugriff auf {@link MannschaftExtern}-Entitäten. Diese
 * Klasse erweitert {@link BaseDAO} und bietet eine Methode zur Suche nach
 * Mannschaftsnamen.
 *
 * @author Marko
 */
public class MannschaftExternDAO extends BaseDAO<MannschaftExtern> {

    /**
     * Konstruktor, der die {@link MannschaftExtern}-Klasse an den generischen
     * DAO übergibt.
     */
    public MannschaftExternDAO() {
        super(MannschaftExtern.class);
    }

    /**
     * Sucht alle {@link MannschaftExtern}-Einträge, die der übergebenen
     * {@link Liga} zugeordnet sind.
     *
     * @param liga die {@link Liga}, nach der gefiltert werden soll
     *
     * @return Liste der {@link MannschaftExtern}-Objekte, die zu dieser Liga
     * gehören; niemals {@code null}, aber evtl. leer
     */
    public List<MannschaftExtern> findByLiga(Liga liga) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM MannschaftExtern m WHERE m.liga = :liga";
            return em.createQuery(jpql, MannschaftExtern.class)
                    .setParameter("liga", liga)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
