/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Elternkontakt;

/**
 * DAO-Klasse für den Zugriff auf {@link Elternkontakt}-Entitäten.
 * Diese Klasse erweitert die generische {@link BaseDAO} und bietet
 * zusätzliche Methoden zur Suche nach Vor- und Nachnamen.
 * 
 * @author Marko
 */
public class ElternkontaktDAO extends BaseDAO<Elternkontakt> {

    /**
     * Konstruktor, der die Elternkontakt-Klasse an den generischen DAO übergibt.
     */
    public ElternkontaktDAO() {
        super(Elternkontakt.class);
    }

    /**
     * Sucht alle {@link Elternkontakt}-Einträge, deren Vorname (case-insensitive) 
     * den übergebenen String enthält.
     *
     * @param vorname der zu suchende Vorname (Teilstring erlaubt)
     * @return Liste aller passenden {@link Elternkontakt}-Objekte
     */
    public List<Elternkontakt> findByVorname(String vorname) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Elternkontakt e WHERE LOWER(e.vorname) LIKE LOWER(:vorname)";
            return em.createQuery(jpql, Elternkontakt.class)
                    .setParameter("vorname", "%" + vorname + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht alle {@link Elternkontakt}-Einträge, deren Nachname (case-insensitive) 
     * den übergebenen String enthält.
     *
     * @param nachname der zu suchende Nachname (Teilstring erlaubt)
     * @return Liste aller passenden {@link Elternkontakt}-Objekte
     */
    public List<Elternkontakt> findByNachname(String nachname) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Elternkontakt e WHERE LOWER(e.nachname) LIKE LOWER(:nachname)";
            return em.createQuery(jpql, Elternkontakt.class)
                    .setParameter("nachname", "%" + nachname + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
