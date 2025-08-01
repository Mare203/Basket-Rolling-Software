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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 * Die Klasse {@code MannschaftExtern} repräsentiert ein externes
 * Basketballteam welches kein Teil von Basket Rolling ist. Also
 * alle Gegner von Basket Rolling sind externe Mannschaften.
 *
 * Jede externe Mannschaft gehört optional einer {@link Liga} an und hat einen
 * eindeutigen Namen. Diese Klasse ist als JPA-Entity mit der Datenbanktabelle
 * {@code mannschaft_extern} verknüpft.
 *
 * @author Marko
 */
@Entity
@Table(name = "mannschaft_extern")
public class MannschaftExtern {

    /**
     * Eindeutige ID der externen Mannschaft (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mannschaft_extern_id", nullable = false, updatable = false)
    private UUID mannschaftExternId;

    /**
     * Name der externen Mannschaft.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Liga, in der die Mannschaft spielt.
     */
    @ManyToOne
    @JoinColumn(name = "liga_id", nullable = false)
    private Liga liga;

    /**
     * Gibt die ID der externen Mannschaft zurück.
     *
     * @return UUID der Mannschaft
     */
    public UUID getMannschaftExternId() {
        return mannschaftExternId;
    }

    /**
     * Setzt die ID der externen Mannschaft.
     *
     * @param mannschaftExternId UUID der Mannschaft
     */
    public void setMannschaftExternId(UUID mannschaftExternId) {
        this.mannschaftExternId = mannschaftExternId;
    }

    /**
     * Gibt den Namen der Mannschaft zurück.
     *
     * @return Name der Mannschaft
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Mannschaft.
     *
     * @param name Name der Mannschaft
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die zugehörige Liga der Mannschaft zurück.
     *
     * @return Liga-Objekt oder {@code null}, wenn nicht gesetzt
     */
    public Liga getLiga() {
        return liga;
    }

    /**
     * Setzt die zugehörige Liga der Mannschaft.
     *
     * @param liga Liga-Objekt
     */
    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    /**
     * Gibt den Namen der Mannschaft als String-Repräsentation zurück.
     *
     * @return Name der Mannschaft
     */
    @Override
    public String toString() {
        return name;
    }
}
