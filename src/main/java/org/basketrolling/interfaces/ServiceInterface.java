/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.basketrolling.interfaces;

import java.util.List;

/**
 * Generisches Service-Interface zur Definition der grundlegenden
 * Geschäftslogik-Operationen für eine Entität im Projekt <b>Basket Rolling</b>.
 *
 * Dieses Interface wird von konkreten Service-Klassen wie
 * {@code SpielerService}, {@code TrainerService} usw. implementiert und ruft
 * intern die entsprechenden DAO-Methoden auf.
 *
 * @param <T> die Entitätsklasse, für die dieses Service zuständig ist
 *
 * @author Marko
 */
public interface ServiceInterface<T> {

    /**
     * Erstellt eine neue Entität im System (inkl. Validierung, Logik etc.).
     *
     * @param entity die zu erstellende Entität
     * @return die erstellte Entität (ggf. mit gesetzter ID)
     */
    T create(T entity);

    /**
     * Aktualisiert eine bestehende Entität im System.
     *
     * @param entity die zu aktualisierende Entität
     * @return die aktualisierte Entität
     */
    T update(T entity);

    /**
     * Löscht eine Entität aus dem System (ggf. mit Prüfung).
     *
     * @param entity die zu löschende Entität
     * @return {@code true}, wenn erfolgreich gelöscht, sonst {@code false}
     */
    boolean delete(T entity);

    /**
     * Gibt eine Liste aller vorhandenen Entitäten dieses Typs zurück.
     *
     * @return Liste aller Entitäten
     */
    List<T> getAll();
}
