/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import org.basketrolling.beans.Login;
import org.basketrolling.enums.Rolle;

/**
 * Die Klasse {@code Session} verwaltet den aktuell eingeloggten Benutzer
 * während einer laufenden Anwendungssitzung.
 *
 * Sie stellt Methoden zum Setzen, Abrufen und Überprüfen der Benutzerrolle
 * bereit. Dadurch kann zentral gesteuert werden, ob ein Benutzer eingeloggt ist
 * und über welche Berechtigungen (z.&nbsp;B. ADMIN oder USER) er verfügt.
 *
 * Diese Klasse ist als statischer Kontext ausgelegt und wird häufig in der
 * GUI-Logik verwendet, um rollenspezifische Ansichten oder Funktionen zu
 * aktivieren bzw. zu sperren.
 *
 * @author Marko
 */
public class Session {

    /**
     * Der aktuell eingeloggte Benutzer.
     */
    private static Login aktuellerBenutzer;

    /**
     * Setzt den aktuellen Benutzer für die Sitzung.
     *
     * @param benutzer das {@link Login}-Objekt des eingeloggten Benutzers
     */
    public static void setBenutzer(Login benutzer) {
        aktuellerBenutzer = benutzer;
    }

    /**
     * Gibt den aktuell eingeloggten Benutzer zurück.
     *
     * @return das aktuelle {@link Login}-Objekt oder {@code null}, wenn kein
     * Benutzer angemeldet ist
     */
    public static Login getBenutzer() {
        return aktuellerBenutzer;
    }

    /**
     * Prüft, ob der aktuell eingeloggte Benutzer die Rolle {@code ADMIN} hat.
     *
     * @return {@code true}, wenn der Benutzer ein Administrator ist, sonst
     * {@code false}
     */
    public static boolean istAdmin() {
        return aktuellerBenutzer != null && aktuellerBenutzer.getRolle() == Rolle.ADMIN;
    }

    /**
     * Prüft, ob der aktuell eingeloggte Benutzer die Rolle {@code USER} hat.
     *
     * @return {@code true}, wenn der Benutzer ein regulärer Benutzer ist, sonst
     * {@code false}
     */
    public static boolean istUser() {
        return aktuellerBenutzer != null && aktuellerBenutzer.getRolle() == Rolle.USER;
    }

    /**
     * Setzt die Benutzersitzung zurück (z.B. beim Logout).
     */
    public static void logout() {
        aktuellerBenutzer = null;
    }
}
