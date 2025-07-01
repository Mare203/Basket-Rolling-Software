/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.service;

import java.util.List;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.enums.Rolle;
import org.basketrolling.utils.TryCatchUtil;
import com.password4j.Password;

/**
 * Service-Klasse zur Verwaltung von {@link Login}-Entitäten.
 * Bietet zusätzliche Funktionen zur Authentifizierung und Registrierung von Benutzern.
 * Die Fehlerbehandlung erfolgt zentral über {@link TryCatchUtil}.
 * 
 * Passwörter werden sicher mit BCrypt gehasht und geprüft.
 * 
 * @author Marko
 */
public class LoginService extends BaseService<Login> {

    private final LoginDAO dao;

    /**
     * Konstruktor, der das {@link LoginDAO} übergibt.
     *
     * @param dao das DAO-Objekt für Login-Datenbankzugriffe
     */
    public LoginService(LoginDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Sucht {@link Login}-Einträge anhand des Benutzernamens (case-insensitive, Teilstring erlaubt).
     *
     * @param benutzername der gesuchte Benutzername oder Teil davon
     * @return Liste der passenden {@link Login}-Objekte
     */
    public List<Login> getByBenutzername(String benutzername) {
        return TryCatchUtil.tryCatchList(() -> dao.findByBenutzername(benutzername));
    }

    /**
     * Sucht {@link Login}-Einträge nach Benutzerrolle.
     *
     * @param rolle die Benutzerrolle (z. B. ADMIN, USER)
     * @return Liste aller Benutzer mit der angegebenen Rolle
     */
    public List<Login> getByRolle(Rolle rolle) {
        return TryCatchUtil.tryCatchList(() -> dao.findByRolle(rolle));
    }

    /**
     * Registriert einen neuen Benutzer, indem das Passwort mit BCrypt gehasht und gespeichert wird.
     *
     * @param login das übergebene {@link Login}-Objekt mit Klartextpasswort
     * @return {@code null} (optional kann auch das neue Login-Objekt zurückgegeben werden)
     */
    public Login registrierung(Login login) {
        String gehashtesPasswort = Password.hash(login.getPasswort()).withBcrypt().getResult();

        Login newLogin = new Login();
        newLogin.setBenutzername(login.getBenutzername());
        newLogin.setPasswort(gehashtesPasswort);
        newLogin.setRolle(login.getRolle());
        
        dao.save(newLogin);
        return null;
    }

    /**
     * Prüft die Benutzeranmeldung: Vergleicht eingegebenes Passwort mit gespeichertem Hash.
     *
     * @param benutzername Benutzername
     * @param passwort eingegebenes Klartextpasswort
     * @return der passende {@link Login}-Benutzer bei Erfolg, sonst {@code null}
     */
    public Login pruefung(String benutzername, String passwort) {
        List<Login> users = dao.findByBenutzername(benutzername);
        if (!users.isEmpty()) {
            Login user = users.get(0);
            boolean match = Password.check(passwort, user.getPasswort()).withBcrypt();
            if (match) {
                return user;
            }
        }
        return null;
    }
}
