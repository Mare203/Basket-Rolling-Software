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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "training_id", nullable = false, updatable = false)
    private UUID trainingId;

    @ManyToOne
    @JoinColumn(name = "halle_id", nullable = false, updatable = false)
    private Halle halle;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "dauer_minuten")
    private int dauerInMin;

    public UUID getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(UUID trainingId) {
        this.trainingId = trainingId;
    }

    public Halle getHalle() {
        return halle;
    }

    public void setHalle(Halle halle) {
        this.halle = halle;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getDauerInMin() {
        return dauerInMin;
    }

    public void setDauerInMin(int dauerInMin) {
        this.dauerInMin = dauerInMin;
    }

    @Override
    public String toString() {
        return "Training{" + "trainingId=" + trainingId
                + ", halle=" + halle
                + ", datum=" + datum
                + ", dauerInMin=" + dauerInMin
                + '}';
    }
}
