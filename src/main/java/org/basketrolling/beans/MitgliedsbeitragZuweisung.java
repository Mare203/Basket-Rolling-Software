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
@Table(name = "mitgliedsbeitrag_zuweisung")
public class MitgliedsbeitragZuweisung {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "spieler_id", nullable = false)
    private Spieler spieler;

    @ManyToOne
    @JoinColumn(name = "mitgliedsbeitrag_id", nullable = false)
    private Mitgliedsbeitrag mitgliedsbeitrag;

    @Column(name = "bezahlt", nullable = false)
    private boolean bezahlt;

    @Column(name = "aktiv", nullable = false)
    private boolean aktiv;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    public Mitgliedsbeitrag getMitgliedsbeitrag() {
        return mitgliedsbeitrag;
    }

    public void setMitgliedsbeitrag(Mitgliedsbeitrag mitgliedsbeitrag) {
        this.mitgliedsbeitrag = mitgliedsbeitrag;
    }

    public boolean isBezahlt() {
        return bezahlt;
    }

    public void setBezahlt(boolean bezahlt) {
        this.bezahlt = bezahlt;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    @Override
    public String toString() {
        return spieler.getVorname() + " " + spieler.getNachname() + " | " + mitgliedsbeitrag.getBetrag() + "€ | Saison - " + mitgliedsbeitrag.getSaison();
    }

}
