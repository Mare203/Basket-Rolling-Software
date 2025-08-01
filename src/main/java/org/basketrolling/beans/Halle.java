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
 * Die Klasse {@code Halle} repräsentiert eine Sporthalle, in der Spiele oder
 * Trainings stattfinden. Sie enthält Informationen wie Name, Adresse (Straße,
 * Postleitzahl, Ort).
 *
 * Diese Klasse ist eine JPA-Entity und der Datenbanktabelle {@code halle}
 * zugeordnet.
 *
 * @author Marko
 */
@Entity
@Table(name = "halle")
public class Halle {

    /**
     * Eindeutige ID der Halle (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "halle_id", nullable = false, updatable = false)
    private UUID halleId;

    /**
     * Name der Halle.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Straße, in der sich die Halle befindet.
     */
    @Column(name = "strasse", nullable = false)
    private String strasse;

    /**
     * Postleitzahl der Halle.
     */
    @Column(name = "postleitzahl", nullable = false)
    private int plz;

    /**
     * Ort, in dem sich die Halle befindet.
     */
    @Column(name = "ort", nullable = false)
    private String ort;

    /**
     * Gibt die eindeutige ID der Halle zurück.
     *
     * @return UUID der Halle
     */
    public UUID getHalleId() {
        return halleId;
    }

    /**
     * Setzt die eindeutige ID der Halle.
     *
     * @param halleId UUID der Halle
     */
    public void setHalleId(UUID halleId) {
        this.halleId = halleId;
    }

    /**
     * Gibt den Namen der Halle zurück.
     *
     * @return Name der Halle
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Halle.
     *
     * @param name Name der Halle
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Straße der Halle zurück.
     *
     * @return Straßenname
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     * Setzt die Straße der Halle.
     *
     * @param strasse Straßenname
     */
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    /**
     * Gibt die Postleitzahl der Halle zurück.
     *
     * @return Postleitzahl
     */
    public int getPlz() {
        return plz;
    }

    /**
     * Setzt die Postleitzahl der Halle.
     *
     * @param plz Postleitzahl
     */
    public void setPlz(int plz) {
        this.plz = plz;
    }

    /**
     * Gibt den Ort der Halle zurück.
     *
     * @return Ort
     */
    public String getOrt() {
        return ort;
    }

    /**
     * Setzt den Ort der Halle.
     *
     * @param ort Ort
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    /**
     * Gibt den Namen der Halle als String-Repräsentation zurück.
     *
     * @return Name der Halle
     */
    @Override
    public String toString() {
        return name;
    }
}
