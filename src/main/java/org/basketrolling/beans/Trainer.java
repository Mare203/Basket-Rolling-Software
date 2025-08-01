/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

/**
 * Die Klasse {@code Trainer} repräsentiert einen Coach im Verein <b>Basket
 * Rolling</b>.
 *
 * Ein Trainer verfügt über grundlegende Kontaktdaten (Vorname, Nachname,
 * E-Mail, Telefonnummer) und kann mehreren internen Mannschaften
 * ({@link MannschaftIntern}) zugewiesen sein.
 *
 * Diese Klasse ist als JPA-Entity mit der Tabelle {@code trainer} verknüpft.
 *
 * @author Marko
 */
@Entity
@Table(name = "trainer")
public class Trainer {

    /**
     * Eindeutige ID des Trainers (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "trainer_id", nullable = false, updatable = false)
    private UUID trainerId;

    /**
     * Vorname des Trainers.
     */
    @Column(name = "vorname", nullable = false)
    private String vorname;

    /**
     * Nachname des Trainers.
     */
    @Column(name = "nachname", nullable = false)
    private String nachname;

    /**
     * E-Mail-Adresse des Trainers (optional).
     */
    @Column(name = "e_mail", nullable = true)
    private String eMail;

    /**
     * Telefonnummer des Trainers.
     */
    @Column(name = "telefon", nullable = false)
    private String telefon;

    /**
     * Liste der Mannschaften, die von diesem Trainer betreut werden.
     */
    @OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
    private List<MannschaftIntern> mannschaft;

    /**
     * Gibt die ID des Trainers zurück.
     *
     * @return UUID des Trainers
     */
    public UUID getTrainerId() {
        return trainerId;
    }

    /**
     * Setzt die ID des Trainers.
     *
     * @param trainerId UUID des Trainers
     */
    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Gibt den Vornamen des Trainers zurück.
     *
     * @return Vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen des Trainers.
     *
     * @param vorname Vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Gibt den Nachnamen des Trainers zurück.
     *
     * @return Nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setzt den Nachnamen des Trainers.
     *
     * @param nachname Nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Gibt die E-Mail-Adresse des Trainers zurück.
     *
     * @return E-Mail-Adresse oder leerer String, wenn nicht gesetzt
     */
    public String getEMail() {
        return eMail != null ? eMail : "";
    }

    /**
     * Setzt die E-Mail-Adresse des Trainers.
     *
     * @param eMail E-Mail-Adresse
     */
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * Gibt die Telefonnummer des Trainers zurück.
     *
     * @return Telefonnummer
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Setzt die Telefonnummer des Trainers.
     *
     * @param telefon Telefonnummer
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Gibt die Liste der betreuten Mannschaften zurück.
     *
     * @return Liste der {@link MannschaftIntern}
     */
    public List<MannschaftIntern> getMannschaft() {
        return mannschaft;
    }

    /**
     * Setzt die Liste der betreuten Mannschaften.
     *
     * @param mannschaft Liste von {@link MannschaftIntern}
     */
    public void setMannschaft(List<MannschaftIntern> mannschaft) {
        this.mannschaft = mannschaft;
    }

    /**
     * Gibt den Namen des Trainers als lesbare String-Repräsentation zurück.
     *
     * @return Vorname und Nachname
     */
    @Override
    public String toString() {
        return vorname + " " + nachname;
    }
}
