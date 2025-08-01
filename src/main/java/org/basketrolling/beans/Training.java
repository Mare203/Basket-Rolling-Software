/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import org.basketrolling.enums.Wochentag;

/**
 * Die Klasse {@code Training} repräsentiert eine wiederkehrende
 * Trainingseinheit einer internen Mannschaft des Vereins <b>Basket Rolling</b>.
 *
 * Für jedes Training werden die zugehörige {@link Halle}, die
 * {@link MannschaftIntern}, der {@link Wochentag} sowie die Trainingsdauer (in
 * Minuten) gespeichert.
 *
 * Diese Klasse ist als JPA-Entity mit der Datenbanktabelle {@code training}
 * verknüpft.
 *
 * @author Marko
 */
@Entity
@Table(name = "training")
public class Training {

    /**
     * Eindeutige ID der Trainingseinheit (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "training_id", nullable = false, updatable = false)
    private UUID trainingId;

    /**
     * Halle, in der das Training stattfindet.
     */
    @ManyToOne
    @JoinColumn(name = "halle_id", nullable = false)
    private Halle halle;

    /**
     * Interne Mannschaft, die am Training teilnimmt.
     */
    @ManyToOne
    @JoinColumn(name = "mannschaft_intern_id")
    private MannschaftIntern mannschaftIntern;

    /**
     * Wochentag, an dem das Training regelmäßig stattfindet.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "wochentag")
    private Wochentag wochentag;

    /**
     * Dauer des Trainings in Minuten.
     */
    @Column(name = "dauer_minuten")
    private int dauerInMin;

    /**
     * Gibt die ID der Trainingseinheit zurück.
     *
     * @return UUID des Trainings
     */
    public UUID getTrainingId() {
        return trainingId;
    }

    /**
     * Setzt die ID der Trainingseinheit.
     *
     * @param trainingId UUID des Trainings
     */
    public void setTrainingId(UUID trainingId) {
        this.trainingId = trainingId;
    }

    /**
     * Gibt die Halle zurück, in der das Training stattfindet.
     *
     * @return Halle
     */
    public Halle getHalle() {
        return halle;
    }

    /**
     * Setzt die Halle für das Training.
     *
     * @param halle Halle
     */
    public void setHalle(Halle halle) {
        this.halle = halle;
    }

    /**
     * Gibt die interne Mannschaft zurück, die trainiert.
     *
     * @return MannschaftIntern
     */
    public MannschaftIntern getMannschaftIntern() {
        return mannschaftIntern;
    }

    /**
     * Setzt die interne Mannschaft für das Training.
     *
     * @param mannschaftIntern MannschaftIntern
     */
    public void setMannschaftIntern(MannschaftIntern mannschaftIntern) {
        this.mannschaftIntern = mannschaftIntern;
    }

    /**
     * Gibt den Wochentag des Trainings zurück.
     *
     * @return Wochentag
     */
    public Wochentag getWochentag() {
        return wochentag;
    }

    /**
     * Setzt den Wochentag für das Training.
     *
     * @param wochentag Wochentag
     */
    public void setWochentag(Wochentag wochentag) {
        this.wochentag = wochentag;
    }

    /**
     * Gibt die Dauer des Trainings in Minuten zurück.
     *
     * @return Trainingsdauer
     */
    public int getDauerInMin() {
        return dauerInMin;
    }

    /**
     * Setzt die Dauer des Trainings in Minuten.
     *
     * @param dauerInMin Trainingsdauer
     */
    public void setDauerInMin(int dauerInMin) {
        this.dauerInMin = dauerInMin;
    }

    /**
     * Gibt eine lesbare String-Repräsentation der Trainingseinheit zurück.
     *
     * @return Mannschaft | Halle | Dauer
     */
    @Override
    public String toString() {
        return mannschaftIntern.getName() + " | " + halle.getName() + " | " + dauerInMin + " Minuten";
    }
}
