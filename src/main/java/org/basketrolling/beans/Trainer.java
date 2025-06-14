/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.beans;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

/**
 *
 * @author Marko
 */
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "trainer_id", nullable = false, updatable = false)
    private UUID trainerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "e_mail", nullable = true)
    private String eMail;

    @Column(name = "telefon", nullable = false)
    private String telefon;

    @Column(name = "aktiv", nullable = false)
    private boolean aktiv;

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
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

    @Override
    public String toString() {
        return "Trainer{" + "trainerId=" + trainerId
                + ", name=" + name
                + ", eMail=" + this.eMail
                + ", telefon=" + telefon
                + ", aktiv=" + aktiv + '}';
    }
}
