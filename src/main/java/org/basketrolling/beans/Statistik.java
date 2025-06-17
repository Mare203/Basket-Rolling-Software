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
 *
 * @author Marko
 */
@Entity
@Table(name = "statistik")
public class Statistik {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statistik_id", nullable = false, updatable = false)
    private UUID statistikId;

    @ManyToOne
    @JoinColumn(name = "spiel_id", nullable = false, updatable = false)
    private Spiele spiel;

    @ManyToOne
    @JoinColumn(name = "spieler_id", nullable = false, updatable = false)
    private Spieler spieler;

    @Column(name = "punkte")
    private int punkte;

    @Column(name = "fouls")
    private int fouls;

    @Column(name = "starter", nullable = false)
    private boolean starter;

    public UUID getStatistikId() {
        return statistikId;
    }

    public void setStatistikId(UUID statistikId) {
        this.statistikId = statistikId;
    }

    public Spiele getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiele spiel) {
        this.spiel = spiel;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getFouls() {
        return fouls;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }

    public boolean isStarter() {
        return starter;
    }

    public void setStarter(boolean starter) {
        this.starter = starter;
    }

    @Override
    public String toString() {
        return "Statistik{" + "statistikId=" + statistikId +
                ", spiel=" + spiel +
                ", spieler=" + spieler +
                ", punkte=" + punkte + 
                ", fouls=" + fouls + 
                ", starter=" + starter +
                '}';
    }

}
