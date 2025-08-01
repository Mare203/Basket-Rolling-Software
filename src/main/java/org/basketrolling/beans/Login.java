/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.beans;

import com.password4j.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import org.basketrolling.enums.Rolle;

/**
 * Die Klasse {@code Login} repräsentiert einen Benutzerzugang zum System. Sie
 * enthält persönliche Informationen wie Vorname, Nachname, Benutzername und
 * eine gehashte Version des Passworts. Außerdem wird dem Benutzer eine
 * {@link Rolle} zugewiesen (z. B. ADMIN, USER).
 *
 * Diese Klasse ist eine JPA-Entity und der Datenbanktabelle {@code login}
 * zugeordnet.
 *
 * <p>
 * <b>Sicherheit:</b> Das Passwort wird beim Setzen automatisch mit BCrypt
 * gehasht.</p>
 *
 * @author Marko
 */
@Entity
@Table(name = "login")
public class Login {

    /**
     * Eindeutige ID des Logins (Primärschlüssel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "login_id", nullable = false, updatable = false)
    private UUID loginId;

    /**
     * Vorname des Benutzers.
     */
    @Column(name = "vorname", nullable = false)
    private String vorname;

    /**
     * Nachname des Benutzers.
     */
    @Column(name = "nachname", nullable = false)
    private String nachname;

    /**
     * Eindeutiger Benutzername für den Login.
     */
    @Column(name = "benutzername", nullable = false, unique = true)
    private String benutzername;

    /**
     * Gehashter Passwort-Hash des Benutzers (wird automatisch mit BCrypt
     * erzeugt).
     */
    @Column(name = "passwort", nullable = false)
    private String passwort;

    /**
     * Rolle des Benutzers im System.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "rolle", nullable = false)
    private Rolle rolle;

    /**
     * Gibt die ID des Logins zurück.
     *
     * @return UUID des Logins
     */
    public UUID getLoginId() {
        return loginId;
    }

    /**
     * Setzt die ID des Logins.
     *
     * @param loginId UUID des Logins
     */
    public void setLoginId(UUID loginId) {
        this.loginId = loginId;
    }

    /**
     * Gibt den Vornamen des Benutzers zurück.
     *
     * @return Vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen des Benutzers.
     *
     * @param vorname Vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Gibt den Nachnamen des Benutzers zurück.
     *
     * @return Nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setzt den Nachnamen des Benutzers.
     *
     * @param nachname Nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Gibt den Benutzernamen zurück.
     *
     * @return Benutzername
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * Setzt den Benutzernamen.
     *
     * @param benutzername Benutzername
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    /**
     * Gibt den gehashten Passwort-Hash zurück.
     *
     * @return gehashter Passwort-String
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * Setzt das Passwort und hasht es automatisch mit BCrypt.
     *
     * @param passwort Klartext-Passwort
     */
    public void setPasswort(String passwort) {
        this.passwort = Password.hash(passwort).withBcrypt().getResult();
    }

    /**
     * Gibt die Rolle des Benutzers zurück.
     *
     * @return Benutzerrolle
     */
    public Rolle getRolle() {
        return rolle;
    }

    /**
     * Setzt die Rolle des Benutzers.
     *
     * @param rolle Benutzerrolle
     */
    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    /**
     * Gibt den Benutzernamen als String-Repräsentation zurück.
     *
     * @return Benutzername
     */
    @Override
    public String toString() {
        return getBenutzername();
    }
}
