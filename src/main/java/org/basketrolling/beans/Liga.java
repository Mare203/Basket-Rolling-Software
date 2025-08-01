/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 * Die Klasse {@code Liga} repräsentiert eine Basketballliga (z. B. 1.
 * Herrenliga, Landesliga). Sie enthält lediglich den Namen der Liga und eine
 * eindeutige ID.
 *
 * Diese Klasse ist als JPA-Entity konfiguriert und mit der Datenbanktabelle
 * {@code liga} verbunden.
 *
 * @author Marko
 */
@Entity
@Table(name = "liga")
public class Liga {

    /**
     * Eindeutige ID der Liga (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "liga_id", nullable = false, updatable = false)
    private UUID ligaId;

    /**
     * Name der Liga (z. B. "Landesliga (LL) oder 1. Herrenliga (H1)").
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Gibt die ID der Liga zurück.
     *
     * @return UUID der Liga
     */
    public UUID getLigaId() {
        return ligaId;
    }

    /**
     * Setzt die ID der Liga.
     *
     * @param ligaId UUID der Liga
     */
    public void setLigaId(UUID ligaId) {
        this.ligaId = ligaId;
    }

    /**
     * Gibt den Namen der Liga zurück.
     *
     * @return Name der Liga
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Liga.
     *
     * @param name Name der Liga
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen der Liga als String-Repräsentation zurück.
     *
     * @return Name der Liga
     */
    @Override
    public String toString() {
        return name;
    }
}
