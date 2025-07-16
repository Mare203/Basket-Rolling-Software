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
 *
 * @author Marko
 */
@Entity
@Table(name = "trainer")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "trainer_id", nullable = false, updatable = false)
    private UUID trainerId;

    @Column(name = "vorname", nullable = false)
    private String vorname;

    @Column(name = "nachname", nullable = false)
    private String nachname;

    @Column(name = "e_mail", nullable = true)
    private String eMail;

    @Column(name = "telefon", nullable = false)
    private String telefon;

    @Column(name = "aktiv", nullable = false)
    private boolean aktiv;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
    private List<MannschaftIntern> mannschaft;

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
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
        return eMail != null ? eMail : "";
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

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public List<MannschaftIntern> getMannschaft() {
        return mannschaft;
    }

    public void setMannschaft(List<MannschaftIntern> mannschaft) {
        this.mannschaft = mannschaft;
    }

    @Override
    public String toString() {
        return vorname + " " + nachname;
    }
}