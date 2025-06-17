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
@Table(name = "mitgliedsbeitrag")
public class Mitgliedsbeitrag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mitgliedsbeitrag_id", nullable = false, updatable = false)
    private UUID mitgliedsbeitragId;

    @ManyToOne
    @JoinColumn(name = "spieler_id", nullable = false, updatable = false)
    private Spieler spieler;

    @Column(name = "betrag", nullable = false)
    private double betrag;

    @Column(name = "bezahlt_am")
    private LocalDate bezahltAm;

    @Column(name = "jahr")
    private int jahr;

    public UUID getMitgliedsbeitragId() {
        return mitgliedsbeitragId;
    }

    public void setMitgliedsbeitragId(UUID mitgliedsbeitragId) {
        this.mitgliedsbeitragId = mitgliedsbeitragId;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public LocalDate getBezahltAm() {
        return bezahltAm;
    }

    public void setBezahltAm(LocalDate bezahltAm) {
        this.bezahltAm = bezahltAm;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    @Override
    public String toString() {
        return "Mitgliedsbeitrag{" + "mitgliedsbeitragId=" + mitgliedsbeitragId + 
                ", spieler=" + spieler +
                ", betrag=" + betrag + 
                ", bezahltAm=" + bezahltAm + 
                ", jahr=" + jahr + 
                '}';
    }

}
