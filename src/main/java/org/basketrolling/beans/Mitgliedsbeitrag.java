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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

/**
 * Die Klasse {@code Mitgliedsbeitrag} repräsentiert einen Mitgliedsbeitrag für
 * eine bestimmte Saison im Verein <b>Basket Rolling</b>.
 *
 * Ein Beitrag ist durch einen Betrag (in Euro) und die zugehörige Saison
 * definiert. Zusätzlich ist eine Liste aller {@link MitgliedsbeitragZuweisung}
 * enthalten, die festhält, welchen Spielern dieser Beitrag zugeordnet wurde.
 *
 * Diese Klasse ist als JPA-Entity mit der Tabelle {@code mitgliedsbeitrag}
 * verknüpft.
 *
 * @author Marko
 */
@Entity
@Table(name = "mitgliedsbeitrag")
public class Mitgliedsbeitrag {

    /**
     * Eindeutige ID des Mitgliedsbeitrags (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mitgliedsbeitrag_id", nullable = false, updatable = false)
    private UUID mitgliedsbeitragId;

    /**
     * Betrag des Mitgliedsbeitrags in Euro.
     */
    @Column(name = "betrag", nullable = false)
    private double betrag;

    /**
     * Saison, für die der Mitgliedsbeitrag gilt (z. B. "2024/25").
     */
    @Column(name = "saison")
    private String saison;

    /**
     * Liste aller Zuweisungen dieses Beitrags an einzelne Spieler.
     */
    @OneToMany(mappedBy = "mitgliedsbeitrag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MitgliedsbeitragZuweisung> beitragsZuweisungen;

    /**
     * Gibt die ID des Mitgliedsbeitrags zurück.
     *
     * @return UUID des Beitrags
     */
    public UUID getMitgliedsbeitragId() {
        return mitgliedsbeitragId;
    }

    /**
     * Setzt die ID des Mitgliedsbeitrags.
     *
     * @param mitgliedsbeitragId UUID des Beitrags
     */
    public void setMitgliedsbeitragId(UUID mitgliedsbeitragId) {
        this.mitgliedsbeitragId = mitgliedsbeitragId;
    }

    /**
     * Gibt den Beitrag in Euro zurück.
     *
     * @return Beitragshöhe
     */
    public double getBetrag() {
        return betrag;
    }

    /**
     * Setzt den Beitrag in Euro.
     *
     * @param betrag Beitragshöhe
     */
    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    /**
     * Gibt die Saison zurück, für die dieser Beitrag gilt.
     *
     * @return Saison (z. B. "2024/25")
     */
    public String getSaison() {
        return saison;
    }

    /**
     * Setzt die Saison für diesen Beitrag.
     *
     * @param saison Saisonangabe
     */
    public void setSaison(String saison) {
        this.saison = saison;
    }

    /**
     * Gibt die Liste der Spieler-Zuweisungen für diesen Beitrag zurück.
     *
     * @return Liste von {@link MitgliedsbeitragZuweisung}
     */
    public List<MitgliedsbeitragZuweisung> getBeitragsZuweisungen() {
        return beitragsZuweisungen;
    }

    /**
     * Setzt die Liste der Spieler-Zuweisungen für diesen Beitrag.
     *
     * @param beitragsZuweisungen Liste von {@link MitgliedsbeitragZuweisung}
     */
    public void setBeitragsZuweisungen(List<MitgliedsbeitragZuweisung> beitragsZuweisungen) {
        this.beitragsZuweisungen = beitragsZuweisungen;
    }

    /**
     * Gibt eine lesbare String-Repräsentation des Beitrags zurück.
     *
     * @return Saisonbezeichnung
     */
    @Override
    public String toString() {
        return "Saison " + saison;
    }
}
