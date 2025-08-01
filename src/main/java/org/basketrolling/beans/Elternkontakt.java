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
 * Die Klasse {@code Elternkontakt} stellt einen Ansprechpartner (z. B.
 * Elternteil oder Erziehungsberechtigten) für einen Spieler dar. Sie enthält
 * Informationen wie Vorname, Nachname, E-Mail und Telefonnummer.
 *
 * Jeder Elternkontakt ist optional einem {@link Spieler} zugeordnet. Diese
 * Klasse wird als JPA-Entity verwendet und ist der Tabelle
 * {@code elternkontakt} zugeordnet.
 *
 * @author Marko
 */
@Entity
@Table(name = "elternkontakt")
public class Elternkontakt {

    /**
     * Eindeutige ID des Elternkontakts (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "elternkontakt_id", nullable = false, updatable = false)
    private UUID elternkontaktId;

    /**
     * Der Spieler, zu dem dieser Elternkontakt gehört (optional).
     */
    @ManyToOne
    @JoinColumn(name = "spieler_id", nullable = true)
    private Spieler spieler;

    /**
     * Vorname des Elternkontakts.
     */
    @Column(name = "vorname", nullable = false)
    private String vorname;

    /**
     * Nachname des Elternkontakts.
     */
    @Column(name = "nachname", nullable = false)
    private String nachname;

    /**
     * E-Mail-Adresse des Elternkontakts (optional).
     */
    @Column(name = "e_mail", nullable = true)
    private String eMail;

    /**
     * Telefonnummer des Elternkontakts.
     */
    @Column(name = "telefon", nullable = false)
    private String telefon;

    // Getter und Setter
    /**
     * Gibt die ID des Elternkontakts zurück.
     *
     * @return UUID des Elternkontakts
     */
    public UUID getElternkontaktId() {
        return elternkontaktId;
    }

    /**
     * Setzt die ID des Elternkontakts.
     *
     * @param elternkontaktId UUID des Elternkontakts
     */
    public void setElternkontaktId(UUID elternkontaktId) {
        this.elternkontaktId = elternkontaktId;
    }

    /**
     * Gibt den zugeordneten Spieler zurück.
     *
     * @return Spieler oder {@code null}, wenn nicht zugewiesen
     */
    public Spieler getSpieler() {
        return spieler;
    }

    /**
     * Setzt den zugeordneten Spieler.
     *
     * @param spieler Spieler-Objekt oder {@code null}
     */
    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    /**
     * Gibt den Vornamen des Elternkontakts zurück.
     *
     * @return Vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen des Elternkontakts.
     *
     * @param vorname Vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Gibt den Nachnamen des Elternkontakts zurück.
     *
     * @return Nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setzt den Nachnamen des Elternkontakts.
     *
     * @param nachname Nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Gibt die E-Mail-Adresse des Elternkontakts zurück.
     *
     * @return E-Mail oder {@code null}, wenn nicht angegeben
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Setzt die E-Mail-Adresse des Elternkontakts.
     *
     * @param eMail E-Mail-Adresse
     */
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * Gibt die Telefonnummer des Elternkontakts zurück.
     *
     * @return Telefonnummer
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Setzt die Telefonnummer des Elternkontakts.
     *
     * @param telefon Telefonnummer
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Gibt eine lesbare Repräsentation des Elternkontakts zurück.
     *
     * @return String mit allen relevanten Attributen
     */
    @Override
    public String toString() {
        return "Elternkontakt{" + "elternkontaktId=" + elternkontaktId
                + ", spieler=" + spieler
                + ", name=" + vorname + " " + nachname
                + ", eMail=" + eMail
                + ", telefon=" + telefon
                + '}';
    }
}
