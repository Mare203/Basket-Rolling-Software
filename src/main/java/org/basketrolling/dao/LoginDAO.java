/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.basketrolling.beans.Login;
import org.basketrolling.enums.Rolle;

/**
 * DAO-Klasse für den Zugriff auf {@link Login}-Entitäten.
 * Diese Klasse erweitert {@link BaseDAO} und bietet spezielle
 * Methoden zur Suche nach Benutzername und Rolle.
 * 
 * @author Marko
 */
public class LoginDAO extends BaseDAO<Login> {

    /**
     * Konstruktor, der die {@link Login}-Klasse an den generischen DAO übergibt.
     */
    public LoginDAO() {
        super(Login.class);
    }

    /**
     * Sucht {@link Login}-Einträge, deren Benutzername (case-insensitive) den übergebenen String enthält.
     *
     * @param benutzername der Benutzername oder Teil des Namens, nach dem gesucht werden soll
     * @return Liste der passenden {@link Login}-Objekte
     */
    public List<Login> findByBenutzername(String benutzername) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT l FROM Login l WHERE LOWER(l.benutzername) LIKE LOWER(:benutzername)";
            return em.createQuery(jpql, Login.class)
                    .setParameter("benutzername", "%" + benutzername + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Sucht {@link Login}-Einträge anhand ihrer zugewiesenen {@link Rolle}.
     *
     * @param rolle die Benutzerrolle, nach der gefiltert werden soll
     * @return Liste der passenden {@link Login}-Objekte
     */
    public List<Login> findByRolle(Rolle rolle) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT l FROM Login l WHERE l.rolle = :rolle";
            return em.createQuery(jpql, Login.class)
                    .setParameter("rolle", rolle)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
