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

    @Column(name = "betrag", nullable = false)
    private double betrag;

    @Column(name = "saison")
    private String saison;

    @OneToMany(mappedBy = "mitgliedsbeitrag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MitgliedsbeitragZuweisung> beitragsZuweisungen;

    public UUID getMitgliedsbeitragId() {
        return mitgliedsbeitragId;
    }

    public void setMitgliedsbeitragId(UUID mitgliedsbeitragId) {
        this.mitgliedsbeitragId = mitgliedsbeitragId;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public List<MitgliedsbeitragZuweisung> getBeitragsZuweisungen() {
        return beitragsZuweisungen;
    }

    public void setBeitragsZuweisungen(List<MitgliedsbeitragZuweisung> beitragsZuweisungen) {
        this.beitragsZuweisungen = beitragsZuweisungen;
    }

    @Override
    public String toString() {
        return "Saison " + saison;
    }
}
