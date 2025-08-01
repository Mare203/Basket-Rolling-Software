/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.basketrolling.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Generisches DAO-Interface zur Definition der grundlegenden
 * Datenbankoperationen (Create, Read, Update, Delete – CRUD) für beliebige
 * Entitäten im Projekt <b>Basket Rolling</b>.
 *
 * Dieses Interface wird von konkreten DAO-Klassen implementiert, z. B.
 * {@code SpielerDAO}, {@code TrainerDAO}.
 *
 * @param <T> die Entitätsklasse, für die dieses DAO zuständig ist
 *
 * @author Marko
 */
public interface DAOInterface<T> {

    /**
     * Speichert eine neue Entität in der Datenbank.
     *
     * @param entity die zu speichernde Entität
     * @return die gespeicherte Entität mit evtl. generierter ID
     */
    T save(T entity);

    /**
     * Aktualisiert eine bestehende Entität in der Datenbank.
     *
     * @param entity die zu aktualisierende Entität
     * @return die aktualisierte Entität
     */
    T update(T entity);

    /**
     * Löscht eine Entität aus der Datenbank.
     *
     * @param entity die zu löschende Entität
     */
    void delete(T entity);

    /**
     * Sucht eine Entität anhand ihrer ID.
     *
     * @param id UUID der gesuchten Entität
     * @return gefundene Entität oder {@code null}, wenn nicht vorhanden
     */
    T findById(UUID id);

    /**
     * Gibt eine Liste aller Entitäten dieses Typs zurück.
     *
     * @return Liste aller Einträge
     */
    List<T> findAll();
}
