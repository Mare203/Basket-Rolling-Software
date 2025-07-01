/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.interfaces.DAOInterface;
import org.basketrolling.interfaces.ServiceInterface;

/**
 * Abstrakte generische Service-Klasse zur Bereitstellung grundlegender Geschäftslogik
 * für beliebige Entitätstypen. Sie implementiert die Methoden des {@link ServiceInterface}
 * und delegiert die Datenzugriffe an ein {@link DAOInterface}.
 *
 * @param <T> der Entitätstyp
 * 
 * @author Marko
 */
public abstract class BaseService<T> implements ServiceInterface<T> {

    private DAOInterface<T> dao;

    /**
     * Konstruktor, der ein generisches DAO für die jeweilige Entitätsklasse entgegennimmt.
     *
     * @param dao das DAO-Objekt, das für Datenbankzugriffe verwendet wird
     */
    public BaseService(DAOInterface<T> dao) {
        this.dao = dao;
    }

    /**
     * Erstellt eine neue Entität und speichert sie in der Datenbank.
     *
     * @param entity die zu erstellende Entität
     * @return die gespeicherte Entität oder {@code null} bei Fehler
     */
    @Override
    public T create(T entity) {
        try {
            return dao.save(entity);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aktualisiert eine bestehende Entität in der Datenbank.
     *
     * @param entity die zu aktualisierende Entität
     * @return die aktualisierte Entität oder {@code null} bei Fehler
     */
    @Override
    public T update(T entity) {
        try {
            return dao.update(entity);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gibt eine Liste aller Entitäten vom Typ {@code T} zurück.
     *
     * @return Liste aller Entitäten oder {@code null} bei Fehler
     */
    @Override
    public List<T> getAll() {
        try {
            return dao.findAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Löscht eine Entität aus der Datenbank.
     *
     * @param entity die zu löschende Entität
     * @return {@code true}, wenn das Löschen erfolgreich war, sonst {@code false}
     */
    @Override
    public boolean delete(T entity) {
        try {
            dao.delete(entity);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
