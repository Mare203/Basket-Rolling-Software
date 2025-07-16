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
@Table(name = "spiele")
public class Spiele {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "spiel_id", nullable = false, updatable = false)
    private UUID spielId;

    @ManyToOne
    @JoinColumn(name = "liga_id", nullable = false, updatable = false)
    private Liga liga;

    @ManyToOne
    @JoinColumn(name = "mannschaft_intern_id", nullable = false, updatable = false)
    private MannschaftIntern mannschaftIntern;

    @ManyToOne
    @JoinColumn(name = "mannschaft_extern_id", nullable = false, updatable = false)
    private MannschaftExtern mannschaftExtern;

    @ManyToOne
    @JoinColumn(name = "halle_id", nullable = false, updatable = false)
    private Halle halle;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "intern_punkte")
    private int internPunkte;

    @Column(name = "extern_punkte")
    private int externPunkte;

    public UUID getSpielId() {
        return spielId;
    }

    public void setSpielId(UUID spielId) {
        this.spielId = spielId;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public MannschaftIntern getMannschaftIntern() {
        return mannschaftIntern;
    }

    public void setMannschaftIntern(MannschaftIntern mannschaftIntern) {
        this.mannschaftIntern = mannschaftIntern;
    }

    public MannschaftExtern getMannschaftExtern() {
        return mannschaftExtern;
    }

    public void setMannschaftExtern(MannschaftExtern mannschaftExtern) {
        this.mannschaftExtern = mannschaftExtern;
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

    public int getInternPunkte() {
        return internPunkte;
    }

    public void setInternPunkte(int internPunkte) {
        this.internPunkte = internPunkte;
    }

    public int getExternPunkte() {
        return externPunkte;
    }

    public void setExternPunkte(int externPunkte) {
        this.externPunkte = externPunkte;
    }

    @Override
    public String toString() {
        String teams = String.format("%-60s", getMannschaftIntern().getName() + " - " + getMannschaftExtern().getName());
        String punkte = String.format("%-10s", getInternPunkte() + ":" + getExternPunkte());
        String halle = String.format("%-22s", getHalle().getName());
        String datum = getDatum().toString();

        return teams + punkte + halle + datum;
    }
}
