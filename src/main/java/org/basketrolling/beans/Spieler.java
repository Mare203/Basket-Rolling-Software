/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.beans;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

/**
 * Die Klasse {@code Spieler} repräsentiert ein Vereinsmitglied bzw. einen
 * aktiven Basketballspieler des Vereins <b>Basket Rolling</b>.
 *
 * Ein Spieler ist einer internen Mannschaft zugeordnet und verfügt über
 * persönliche Informationen wie Vorname, Nachname, Geburtsdatum, E-Mail, Größe
 * und Aktivitätsstatus. Zusätzlich können ihm Elternkontakte, Mitgliedsbeiträge
 * und Spielstatistiken zugeordnet sein.
 *
 * Diese Klasse ist als JPA-Entity mit der Tabelle {@code spieler} verknüpft.
 *
 * @author Marko
 */
@Entity
@Table(name = "spieler")
public class Spieler {

    /**
     * Eindeutige ID des Spielers (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "spieler_id", nullable = false, updatable = false)
    private UUID spielerId;

    /**
     * Vorname des Spielers.
     */
    @Column(name = "vorname", nullable = false)
    private String vorname;

    /**
     * Nachname des Spielers.
     */
    @Column(name = "nachname", nullable = false)
    private String nachname;

    /**
     * Geburtsdatum des Spielers.
     */
    @Column(name = "geburtsdatum", nullable = false)
    private LocalDate geburtsdatum;

    /**
     * E-Mail-Adresse des Spielers (optional).
     */
    @Column(name = "e_mail", nullable = true)
    private String eMail;

    /**
     * Körpergröße des Spielers z.b. 1.80.
     */
    @Column(name = "groeße", nullable = false)
    private double groesse;

    /**
     * Gibt an, ob der Spieler derzeit aktiv ist.
     */
    @Column(name = "aktiv", nullable = true)
    private boolean aktiv;

    /**
     * Die interne Mannschaft, der der Spieler zugeordnet ist.
     */
    @ManyToOne
    @JoinColumn(name = "mannschaft_intern_id", nullable = false)
    private MannschaftIntern mannschaftIntern;

    /**
     * Liste aller Mitgliedsbeiträge, die diesem Spieler zugewiesen wurden.
     */
    @OneToMany(mappedBy = "spieler", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MitgliedsbeitragZuweisung> beitragsZuweisungen;

    /**
     * Liste aller Elternkontakte des Spielers.
     */
    @OneToMany(mappedBy = "spieler", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Elternkontakt> elternkontakt;

    /**
     * Liste aller Statistik-Einträge des Spielers.
     */
    @OneToMany(mappedBy = "spieler", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Statistik> statistik;

    // === Getter & Setter ===
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

    /**
     * Berechnet und gibt das Alter des Spielers in Jahren zurück.
     *
     * @return Alter in Jahren (oder 0, wenn kein Geburtsdatum gesetzt ist)
     */
    public int getAlter() {
        if (geburtsdatum == null) {
            return 0;
        }
        return Period.between(geburtsdatum, LocalDate.now()).getYears();
    }

    public List<Elternkontakt> getElternkontakt() {
        return elternkontakt;
    }

    public void setElternkontakt(List<Elternkontakt> elternkontakt) {
        this.elternkontakt = elternkontakt;
    }

    public List<Statistik> getStatistik() {
        return statistik;
    }

    public void setStatistik(List<Statistik> statistik) {
        this.statistik = statistik;
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

    public List<MitgliedsbeitragZuweisung> getBeitragsZuweisungen() {
        return beitragsZuweisungen;
    }

    public void setBeitragsZuweisungen(List<MitgliedsbeitragZuweisung> beitragsZuweisungen) {
        this.beitragsZuweisungen = beitragsZuweisungen;
    }

    /**
     * Gibt eine lesbare Repräsentation des Spielers zurück.
     *
     * @return Vorname, Nachname und zugeordnete Mannschaft
     */
    @Override
    public String toString() {
        return vorname + " " + nachname + " - " + mannschaftIntern.getName();
    }
}
