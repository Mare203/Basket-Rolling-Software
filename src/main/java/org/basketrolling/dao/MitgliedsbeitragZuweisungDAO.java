/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;

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
    
    public List<MitgliedsbeitragZuweisung> findOffeneBeitraege(){
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT z FROM MitgliedsbeitragZuweisung z JOIN FETCH z.spieler WHERE z.bezahlt = false";
            return em.createQuery(jpql, MitgliedsbeitragZuweisung.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
