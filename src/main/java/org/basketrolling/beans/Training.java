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
    @JoinColumn(name = "halle_id", nullable = false)
    private Halle halle;

    @ManyToOne
    @JoinColumn(name = "mannschaft_intern_id")
    private MannschaftIntern mannschaftIntern;

    @Column(name = "wochentag")
    private String wochentag;

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

    public MannschaftIntern getMannschaftIntern() {
        return mannschaftIntern;
    }

    public void setMannschaftIntern(MannschaftIntern mannschaftIntern) {
        this.mannschaftIntern = mannschaftIntern;
    }    
    
    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getWochentag() {
        return wochentag;
    }

    public void setWochentag(String wochentag) {
        this.wochentag = wochentag;
    }

    public int getDauerInMin() {
        return dauerInMin;
    }

    public void setDauerInMin(int dauerInMin) {
        this.dauerInMin = dauerInMin;
    }

    @Override
    public String toString() {
        return mannschaftIntern.getName() + " | " + halle.getName() + " | " + dauerInMin + " Minuten";
    }
}
