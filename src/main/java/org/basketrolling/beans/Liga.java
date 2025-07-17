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
@Table(name = "liga")
public class Liga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "liga_id", nullable = false, updatable = false)
    private UUID ligaId;
    
    @Column(name = "name", nullable = false)
    private String name;

    public UUID getLigaId() {
        return ligaId;
    }

    public void setLigaId(UUID ligaId) {
        this.ligaId = ligaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
