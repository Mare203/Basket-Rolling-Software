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
 * Die Klasse {@code MannschaftIntern} repräsentiert ein internes Team des
 * Vereins <b>Basket Rolling</b>. Jedes Team gehört verpflichtend einer
 * {@link Liga} an und kann optional einem {@link Trainer} zugewiesen sein.
 *
 * Diese Klasse ist als JPA-Entity mit der Datenbanktabelle
 * {@code mannschaft_intern} verbunden. Die internen Mannschaften stehen im
 * Gegensatz zu {@code MannschaftExtern}, die gegnerische Teams repräsentieren.
 *
 * @author Marko
 */
@Entity
@Table(name = "mannschaft_intern")
public class MannschaftIntern {

    /**
     * Eindeutige ID der internen Mannschaft (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mannschaft_intern_id", nullable = false, updatable = false)
    private UUID mannschaftInternId;

    /**
     * Name der internen Mannschaft (z. B. "Basket Rolling U16").
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Liga, in der die Mannschaft spielt (Pflichtfeld).
     */
    @ManyToOne
    @JoinColumn(name = "liga_id", nullable = false)
    private Liga liga;

    /**
     * Trainer der Mannschaft (optional).
     */
    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = true)
    private Trainer trainer;

    /**
     * Gibt die ID der Mannschaft zurück.
     *
     * @return UUID der Mannschaft
     */
    public UUID getMannschaftInternId() {
        return mannschaftInternId;
    }

    /**
     * Setzt die ID der Mannschaft.
     *
     * @param mannschaftInternId UUID der Mannschaft
     */
    public void setMannschaftInternId(UUID mannschaftInternId) {
        this.mannschaftInternId = mannschaftInternId;
    }

    /**
     * Gibt den Namen der Mannschaft zurück.
     *
     * @return Mannschaftsname
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Mannschaft.
     *
     * @param name Mannschaftsname
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die zugewiesene Liga der Mannschaft zurück.
     *
     * @return Liga-Objekt
     */
    public Liga getLiga() {
        return liga;
    }

    /**
     * Setzt die Liga, in der die Mannschaft spielt.
     *
     * @param liga Liga-Objekt
     */
    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    /**
     * Gibt den Trainer der Mannschaft zurück.
     *
     * @return Trainer-Objekt oder {@code null}, wenn keiner zugewiesen ist
     */
    public Trainer getTrainer() {
        return trainer;
    }

    /**
     * Setzt den Trainer der Mannschaft.
     *
     * @param trainer Trainer-Objekt
     */
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    /**
     * Gibt den Namen der Mannschaft als String-Repräsentation zurück.
     *
     * @return Mannschaftsname
     */
    @Override
    public String toString() {
        return name;
    }
}
