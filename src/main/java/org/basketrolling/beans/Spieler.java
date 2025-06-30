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
@Table(name = "spieler")
public class Spieler {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "spieler_id", nullable = false, updatable = false)
    private UUID spielerId;
    
    @Column(name = "vorname", nullable = false)
    private String vorname;
    
    @Column(name = "nachname", nullable = false)
    private String nachname;
    
    @Column(name = "geburtsdatum", nullable = false)
    private LocalDate geburtsdatum;
    
    @Column(name = "alter", nullable = false)
    private int alter;
    
    @Column(name = "e_mail", nullable = true)
    private String eMail;
    
    @Column(name = "groeße", nullable = false)
    private double groesse;
    
    @Column(name = "aktiv", nullable = false)
    private boolean aktiv;
    
    @ManyToOne
    @JoinColumn(name = "mannschaft_intern_id", nullable = true)
    private MannschaftIntern mannschaftIntern;

    public UUID getSpielerId() {
        return spielerId;
    }

    public void setSpielerId(UUID spielerId) {
        this.spielerId = spielerId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public double getGroesse() {
        return groesse;
    }

    public void setGroesse(double groesse) {
        this.groesse = groesse;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public MannschaftIntern getMannschaftIntern() {
        return mannschaftIntern;
    }

    public void setMannschaftIntern(MannschaftIntern mannschaftIntern) {
        this.mannschaftIntern = mannschaftIntern;
    }

    @Override
    public String toString() {
        return "Spieler{" + "spielerId=" + spielerId + 
                ", vorname=" + vorname + 
                ", nachname=" + nachname +
                ", geburtsdatum=" + geburtsdatum + 
                ", alter=" + alter + 
                ", eMail=" + this.eMail + 
                ", groesse=" + groesse +
                ", aktiv=" + aktiv + 
                ", mannschaftIntern=" + mannschaftIntern +
                '}';
    }              
}
