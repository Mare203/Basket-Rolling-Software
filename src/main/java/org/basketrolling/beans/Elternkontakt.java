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
@Table(name = "elternkontakt")
public class Elternkontakt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "elternkontakt_id", nullable = false, updatable = false)
    private UUID elternkontaktId;

    @ManyToOne
    @JoinColumn(name = "spieler_id", nullable = true)
    private Spieler spieler;

    @Column(name = "vorname", nullable = false)
    private String vorname;

    @Column(name = "nachname", nullable = false)
    private String nachname;

    @Column(name = "e_mail", nullable = true)
    private String eMail;

    @Column(name = "telefon", nullable = false)
    private String telefon;

    public UUID getElternkontaktId() {
        return elternkontaktId;
    }

    public void setElternkontaktId(UUID elternkontaktId) {
        this.elternkontaktId = elternkontaktId;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
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

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Elternkontakt{" + "elternkontaktId=" + elternkontaktId
                + ", spieler=" + spieler
                + ", name=" + vorname + " " +  nachname
                + ", eMail=" + eMail
                + ", telefon=" + telefon
                + '}';
    }
}
