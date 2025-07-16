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
 *
 * @author Marko
 */
@Entity
@Table(name = "mannschaft_extern")
public class MannschaftExtern {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "mannschaft_extern_id", nullable = false, updatable = false)
    private UUID mannschaftExternId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;

    public UUID getMannschaftExternId() {
        return mannschaftExternId;
    }

    public void setMannschaftExternId(UUID mannschaftExternId) {
        this.mannschaftExternId = mannschaftExternId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    @Override
    public String toString() {
        return name;
    }
}
