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
 * Die Klasse {@code Statistik} repräsentiert die individuellen Leistungsdaten
 * eines bestimmten {@link Spieler} in einem konkreten {@link Spiele}.
 *
 * Zu den gespeicherten Informationen gehören erzielte Punkte, begangene Fouls
 * und ob der Spieler überhaupt am Spiel teilgenommen hat. Diese Klasse dient
 * als Verknüpfung zwischen Spieler und Spiel und bildet eine typische
 * Many-to-Many-Beziehung mit zusätzlichen Attributen ab.
 *
 * Diese Entity ist mit der Datenbanktabelle {@code statistik} verknüpft.
 *
 * @author Marko
 */
@Entity
@Table(name = "statistik")
public class Statistik {

    /**
     * Eindeutige ID der Statistik (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statistik_id", nullable = false, updatable = false)
    private UUID statistikId;

    /**
     * Das Spiel, in dem die Statistik aufgezeichnet wurde.
     */
    @ManyToOne
    @JoinColumn(name = "spiel_id", nullable = false, updatable = false)
    private Spiele spiel;

    /**
     * Der Spieler, zu dem diese Statistik gehört.
     */
    @ManyToOne
    @JoinColumn(name = "spieler_id", nullable = false, updatable = false)
    private Spieler spieler;

    /**
     * Anzahl der erzielten Punkte des Spielers in diesem Spiel.
     */
    @Column(name = "punkte")
    private int punkte;

    /**
     * Anzahl der Fouls des Spielers in diesem Spiel.
     */
    @Column(name = "fouls")
    private int fouls;

    /**
     * Gibt an, ob der Spieler am Spiel teilgenommen hat.
     */
    @Column(name = "gespielt")
    private boolean gespielt;

    /**
     * Gibt die ID der Statistik zurück.
     *
     * @return UUID der Statistik
     */
    public UUID getStatistikId() {
        return statistikId;
    }

    /**
     * Setzt die ID der Statistik.
     *
     * @param statistikId UUID der Statistik
     */
    public void setStatistikId(UUID statistikId) {
        this.statistikId = statistikId;
    }

    /**
     * Gibt das Spiel zurück, zu dem diese Statistik gehört.
     *
     * @return Spiel-Objekt
     */
    public Spiele getSpiel() {
        return spiel;
    }

    /**
     * Setzt das Spiel, zu dem diese Statistik gehört.
     *
     * @param spiel Spiel-Objekt
     */
    public void setSpiel(Spiele spiel) {
        this.spiel = spiel;
    }

    /**
     * Gibt den Spieler zurück, zu dem diese Statistik gehört.
     *
     * @return Spieler-Objekt
     */
    public Spieler getSpieler() {
        return spieler;
    }

    /**
     * Setzt den Spieler, zu dem diese Statistik gehört.
     *
     * @param spieler Spieler-Objekt
     */
    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    /**
     * Gibt die erzielten Punkte zurück.
     *
     * @return Anzahl der Punkte
     */
    public int getPunkte() {
        return punkte;
    }

    /**
     * Setzt die erzielten Punkte.
     *
     * @param punkte Anzahl der Punkte
     */
    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    /**
     * Gibt die Anzahl der Fouls zurück.
     *
     * @return Anzahl der Fouls
     */
    public int getFouls() {
        return fouls;
    }

    /**
     * Setzt die Anzahl der Fouls.
     *
     * @param fouls Anzahl der Fouls
     */
    public void setFouls(int fouls) {
        this.fouls = fouls;
    }

    /**
     * Gibt zurück, ob der Spieler gespielt hat.
     *
     * @return {@code true}, wenn gespielt, sonst {@code false}
     */
    public boolean isGespielt() {
        return gespielt;
    }

    /**
     * Setzt, ob der Spieler gespielt hat.
     *
     * @param gespielt {@code true}, wenn gespielt
     */
    public void setGespielt(boolean gespielt) {
        this.gespielt = gespielt;
    }

    /**
     * Gibt eine lesbare Repräsentation der Statistik zurück.
     *
     * @return String mit Spieler, Spiel und Leistungsdaten
     */
    @Override
    public String toString() {
        return "Statistik{"
                + "statistikId=" + statistikId
                + ", spiel=" + spiel
                + ", spieler=" + spieler
                + ", punkte=" + punkte
                + ", fouls=" + fouls
                + ", gespielt=" + gespielt
                + '}';
    }
}
