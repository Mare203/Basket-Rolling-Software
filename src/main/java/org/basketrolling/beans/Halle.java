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
public class Halle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "halle_id", nullable = false, updatable = false)
    private UUID halleId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "adresse", nullable = false)
    private String adresse;

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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Halle{" + "halleId=" + halleId + ", name=" + name + ", adresse=" + adresse + '}';
    }
    
}
