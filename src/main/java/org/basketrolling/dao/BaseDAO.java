/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.UUID;
import org.basketrolling.interfaces.DAOInterface;

/**
 * Abstrakte generische DAO-Klasse zur Verwaltung von JPA-Entitäten.
 * Diese Klasse stellt grundlegende CRUD-Operationen (Create, Read, Update, Delete)
 * für jede Entitätsklasse bereit, die von ihr erbt.
 * 
 * @author Marko
 *
 * @param <T> die Typklasse der Entität
 */
public abstract class BaseDAO<T> implements DAOInterface<T> {

    static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("rolling-persistence-unit");

    protected final Class<T> entityClass;

    /**
     * Konstruktor, der die Entitätsklasse übergibt und speichert.
     *
     * @param entityClass die Klasse der Entität
     */
    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Erstellt einen neuen EntityManager aus der EntityManagerFactory.
     *
     * @return ein neuer EntityManager
     */
    protected EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    /**
     * Speichert eine neue Entität in der Datenbank.
     *
     * @param entity die zu speichernde Entität
     * @return die gespeicherte Entität
     * @throws RuntimeException falls ein Fehler beim Speichern auftritt
     */
    @Override
    public T save(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Fehler beim Speichern: " + e.getMessage());
            throw new RuntimeException("Fehler beim Speichern", e);
        } finally {
            em.close();
        }
    }

    /**
     * Aktualisiert eine bestehende Entität in der Datenbank.
     *
     * @param entity die zu aktualisierende Entität
     * @return die aktualisierte Entität
     * @throws RuntimeException falls ein Fehler beim Aktualisieren auftritt
     */
    @Override
    public T update(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return updatedEntity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Fehler beim Aktualisieren: " + e.getMessage());
            throw new RuntimeException("Fehler beim Aktualisieren", e);
        } finally {
            em.close();
        }
    }

    /**
     * Löscht eine Entität aus der Datenbank.
     *
     * @param entity die zu löschende Entität
     * @throws RuntimeException falls ein Fehler beim Löschen auftritt
     */
    @Override
    public void delete(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Fehler beim Löschen: " + e.getMessage());
            throw new RuntimeException("Fehler beim Löschen", e);
        } finally {
            em.close();
        }
    }

    /**
     * Sucht eine Entität anhand ihrer UUID.
     *
     * @param id die UUID der Entität
     * @return die gefundene Entität oder {@code null}, falls nicht vorhanden
     */
    @Override
    public T findById(UUID id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    /**
     * Gibt eine Liste aller Entitäten dieses Typs zurück.
     *
     * @return Liste aller Entitäten
     */
    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, entityClass).getResultList();
        } finally {
            em.close();
        }
    }
}

