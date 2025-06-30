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
import jakarta.persistence.Table;
import java.util.UUID;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "halle")
public class Halle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "halle_id", nullable = false, updatable = false)
    private UUID halleId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "strasse", nullable = false)
    private String strasse;
    
    @Column(name = "postleitzahl", nullable = false)
    private int plz;
    
    @Column(name = "ort", nullable = false)
    private String ort;

    public UUID getHalleId() {
        return halleId;
    }

    public void setHalleId(UUID halleId) {
        this.halleId = halleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public String toString() {
        return "Halle{" + "halleId=" + halleId + ", name=" + name + ", adresse=" + strasse + ", " + plz + " " + ort + '}';
    }
    
}
