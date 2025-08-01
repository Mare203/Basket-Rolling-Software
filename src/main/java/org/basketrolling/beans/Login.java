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
 *
 * @author Marko
 */
@Entity
@Table(name = "login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "login_id", nullable = false, updatable = false)
    private UUID loginId;

    @Column(name = "vorname", nullable = false)
    private String vorname;
    
    @Column(name = "nachname", nullable = false)
    private String nachname;
    
    @Column(name = "benutzername", nullable = false, unique = true)
    private String benutzername;
    
    @Column(name = "passwort", nullable = false)
    private String passwort;

    @Enumerated(EnumType.STRING)
    @Column(name = "rolle", nullable = false)
    private Rolle rolle;

    public UUID getLoginId() {
        return loginId;
    }

    public void setLoginId(UUID loginId) {
        this.loginId = loginId;
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
    
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = Password.hash(passwort).withBcrypt().getResult();
    }
        
    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    @Override
    public String toString() {
        return getBenutzername();
    }
}
