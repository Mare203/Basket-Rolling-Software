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
 * Die Klasse {@code MitgliedsbeitragZuweisung} stellt die Zuweisung eines
 * {@link Mitgliedsbeitrag} zu einem bestimmten {@link Spieler} dar.
 *
 * Diese Verknüpfung ist notwendig, um festzuhalten, ob ein Spieler für eine
 * bestimmte Saison einen Mitgliedsbeitrag zu bezahlen hat und ob dieser bereits
 * bezahlt wurde.
 *
 * Zusätzlich ermöglicht das Feld {@code aktiv} eine Statusverwaltung, um etwa
 * alte oder inaktive Zuweisungen auszublenden.
 *
 * Die Klasse ist als JPA-Entity mit der Tabelle
 * {@code mitgliedsbeitrag_zuweisung} verknüpft und bildet eine klassische
 * Many-to-Many-Zuordnung mit Zusatzinformation (bezahlt, aktiv) ab.
 *
 * @author Marko
 */
@Entity
@Table(name = "mitgliedsbeitrag_zuweisung")
public class MitgliedsbeitragZuweisung {

    /**
     * Eindeutige ID der Zuweisung (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Spieler, dem der Mitgliedsbeitrag zugewiesen ist.
     */
    @ManyToOne
    @JoinColumn(name = "spieler_id", nullable = false)
    private Spieler spieler;

    /**
     * Zugewiesener Mitgliedsbeitrag (z. B. für Saison 2024/25).
     */
    @ManyToOne
    @JoinColumn(name = "mitgliedsbeitrag_id", nullable = false)
    private Mitgliedsbeitrag mitgliedsbeitrag;

    /**
     * Gibt an, ob der Beitrag bereits bezahlt wurde.
     */
    @Column(name = "bezahlt", nullable = false)
    private boolean bezahlt;

    /**
     * Gibt an, ob die Zuweisung aktiv ist (z. B. für aktuelle Saison).
     */
    @Column(name = "aktiv", nullable = false)
    private boolean aktiv;

    /**
     * Gibt die ID der Zuweisung zurück.
     *
     * @return UUID der Zuweisung
     */
    public UUID getId() {
        return id;
    }

    /**
     * Setzt die ID der Zuweisung.
     *
     * @param id UUID der Zuweisung
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gibt den zugewiesenen Spieler zurück.
     *
     * @return Spieler-Objekt
     */
    public Spieler getSpieler() {
        return spieler;
    }

    /**
     * Setzt den zugewiesenen Spieler.
     *
     * @param spieler Spieler-Objekt
     */
    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    /**
     * Gibt den zugewiesenen Mitgliedsbeitrag zurück.
     *
     * @return Mitgliedsbeitrag-Objekt
     */
    public Mitgliedsbeitrag getMitgliedsbeitrag() {
        return mitgliedsbeitrag;
    }

    /**
     * Setzt den zugewiesenen Mitgliedsbeitrag.
     *
     * @param mitgliedsbeitrag Mitgliedsbeitrag-Objekt
     */
    public void setMitgliedsbeitrag(Mitgliedsbeitrag mitgliedsbeitrag) {
        this.mitgliedsbeitrag = mitgliedsbeitrag;
    }

    /**
     * Gibt zurück, ob der Beitrag bezahlt wurde.
     *
     * @return {@code true}, wenn bezahlt
     */
    public boolean isBezahlt() {
        return bezahlt;
    }

    /**
     * Setzt den Zahlungsstatus des Beitrags.
     *
     * @param bezahlt {@code true}, wenn bezahlt
     */
    public void setBezahlt(boolean bezahlt) {
        this.bezahlt = bezahlt;
    }

    /**
     * Gibt zurück, ob die Zuweisung aktiv ist.
     *
     * @return {@code true}, wenn aktiv
     */
    public boolean isAktiv() {
        return aktiv;
    }

    /**
     * Setzt den Aktivitätsstatus der Zuweisung.
     *
     * @param aktiv {@code true}, wenn aktiv
     */
    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    /**
     * Gibt eine lesbare String-Repräsentation der Zuweisung zurück.
     *
     * @return Vorname und Nachname des Spielers, Betrag und Saison des
     * Mitgliedsbeitrags
     */
    @Override
    public String toString() {
        return spieler.getVorname() + " " + spieler.getNachname()
                + " | " + mitgliedsbeitrag.getBetrag() + "€ | Saison - "
                + mitgliedsbeitrag.getSaison();
    }
}
