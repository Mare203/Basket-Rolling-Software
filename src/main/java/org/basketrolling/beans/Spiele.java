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
import java.util.List;
import java.util.UUID;

/**
 * Die Klasse {@code Spiele} repräsentiert ein einzelnes Basketballspiel
 * zwischen einer internen Mannschaft von {@code Basket Rolling} und einer
 * externen Mannschaft.
 *
 * Zu jedem Spiel gehören:
 * <ul>
 * <li>eine Liga,</li>
 * <li>eine interne und eine externe Mannschaft,</li>
 * <li>eine Halle,</li>
 * <li>ein Spieltermin (Datum),</li>
 * <li>die erzielten Punkte beider Teams,</li>
 * <li>sowie eine Liste von {@link Statistik}-Einträgen für einzelne
 * Spielerleistungen.</li>
 * </ul>
 *
 * Diese Klasse ist eine JPA-Entity und der Tabelle {@code spiele} zugeordnet.
 *
 * @author Marko
 */
@Entity
@Table(name = "spiele")
public class Spiele {

    /**
     * Eindeutige ID des Spiels (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "spiel_id", nullable = false, updatable = false)
    private UUID spielId;

    /**
     * Liga, in der das Spiel stattfindet.
     */
    @ManyToOne
    @JoinColumn(name = "liga_id", nullable = false, updatable = false)
    private Liga liga;

    /**
     * Interne Mannschaft (von Basket Rolling), die am Spiel teilnimmt.
     */
    @ManyToOne
    @JoinColumn(name = "mannschaft_intern_id", nullable = false, updatable = false)
    private MannschaftIntern mannschaftIntern;

    /**
     * Externe Mannschaft (Gegner), die am Spiel teilnimmt.
     */
    @ManyToOne
    @JoinColumn(name = "mannschaft_extern_id", nullable = false, updatable = false)
    private MannschaftExtern mannschaftExtern;

    /**
     * Spielort (Halle), in dem das Spiel ausgetragen wird.
     */
    @ManyToOne
    @JoinColumn(name = "halle_id", nullable = false, updatable = false)
    private Halle halle;

    /**
     * Datum des Spiels.
     */
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    /**
     * Punkte, die die interne Mannschaft erzielt hat.
     */
    @Column(name = "intern_punkte")
    private int internPunkte;

    /**
     * Punkte, die die externe Mannschaft erzielt hat.
     */
    @Column(name = "extern_punkte")
    private int externPunkte;

    /**
     * Liste aller Statistik-Einträge zum Spiel (z. B. Punkte, Fouls je
     * Spieler).
     */
    @OneToMany(mappedBy = "spiel", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Statistik> statistiken;

    // === Getter & Setter ===
    /**
     * Gibt die ID des Spiels zurück.
     *
     * @return UUID des Spiels
     */
    public UUID getSpielId() {
        return spielId;
    }

    /**
     * Setzt die ID des Spiels.
     *
     * @param spielId UUID des Spiels
     */
    public void setSpielId(UUID spielId) {
        this.spielId = spielId;
    }

    /**
     * Gibt die Liga des Spiels zurück.
     *
     * @return Liga
     */
    public Liga getLiga() {
        return liga;
    }

    /**
     * Setzt die Liga des Spiels.
     *
     * @param liga Liga
     */
    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    /**
     * Gibt die interne Mannschaft (Basket Rolling) zurück.
     *
     * @return interne Mannschaft
     */
    public MannschaftIntern getMannschaftIntern() {
        return mannschaftIntern;
    }

    /**
     * Setzt die interne Mannschaft (Basket Rolling).
     *
     * @param mannschaftIntern interne Mannschaft
     */
    public void setMannschaftIntern(MannschaftIntern mannschaftIntern) {
        this.mannschaftIntern = mannschaftIntern;
    }

    /**
     * Gibt die externe Mannschaft zurück.
     *
     * @return externe Mannschaft
     */
    public MannschaftExtern getMannschaftExtern() {
        return mannschaftExtern;
    }

    /**
     * Setzt die externe Mannschaft.
     *
     * @param mannschaftExtern externe Mannschaft
     */
    public void setMannschaftExtern(MannschaftExtern mannschaftExtern) {
        this.mannschaftExtern = mannschaftExtern;
    }

    /**
     * Gibt die Halle zurück, in der das Spiel stattfindet.
     *
     * @return Halle
     */
    public Halle getHalle() {
        return halle;
    }

    /**
     * Setzt die Halle, in der das Spiel stattfindet.
     *
     * @param halle Halle
     */
    public void setHalle(Halle halle) {
        this.halle = halle;
    }

    /**
     * Gibt das Datum des Spiels zurück.
     *
     * @return Spieltermin
     */
    public LocalDate getDatum() {
        return datum;
    }

    /**
     * Setzt das Datum des Spiels.
     *
     * @param datum Spieltermin
     */
    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    /**
     * Gibt die von der internen Mannschaft erzielten Punkte zurück.
     *
     * @return Punkte der internen Mannschaft
     */
    public int getInternPunkte() {
        return internPunkte;
    }

    /**
     * Setzt die Punkte der internen Mannschaft.
     *
     * @param internPunkte Punkte
     */
    public void setInternPunkte(int internPunkte) {
        this.internPunkte = internPunkte;
    }

    /**
     * Gibt die von der externen Mannschaft erzielten Punkte zurück.
     *
     * @return Punkte der externen Mannschaft
     */
    public int getExternPunkte() {
        return externPunkte;
    }

    /**
     * Setzt die Punkte der externen Mannschaft.
     *
     * @param externPunkte Punkte
     */
    public void setExternPunkte(int externPunkte) {
        this.externPunkte = externPunkte;
    }

    /**
     * Gibt eine formatierte String-Repräsentation des Spiels zurück. Beinhaltet
     * Mannschaften, Punktestand, Halle und Datum.
     *
     * @return formatiertes Spielergebnis
     */
    @Override
    public String toString() {
        String teams = String.format("%-50s", getMannschaftIntern().getName() + " - " + getMannschaftExtern().getName());
        String punkte = String.format("%-9s", getInternPunkte() + ":" + getExternPunkte());
        String halle = String.format("%-25s", getHalle().getName());
        String datum = getDatum().toString();

        return teams + punkte + halle + datum;
    }
}
