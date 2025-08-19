package org.basketrolling;

import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.enums.Rolle;
import org.basketrolling.service.LoginService;

/**
 * Demo-Klasse zum Anlegen des ersten Benutzers im System.
 * <p>
 * Diese Klasse dient dazu, den initialen Administrator-Account für die
 * Anwendung "Basket Rolling" zu erstellen. Da es kein Registrierungsfenster
 * gibt, muss der erste Benutzer über den Code in der Datenbank angelegt werden.
 * </p>
 * <p>
 * Nach dem einmaligen Ausführen dieser Klasse steht ein Admin-Benutzer mit den
 * Standard-Zugangsdaten zur Verfügung:
 * <ul>
 * <li>Benutzername: admin</li>
 * <li>Passwort: admin</li>
 * </ul>
 * Dieser Benutzer kann sich im System anmelden und anschließend weitere
 * Benutzer (Admins oder normale User) über die GUI anlegen.
 * </p>
 *
 * @author Marko
 */
public class RollingDemo {

    public static void main(String[] args) {
        Login login = new Login();
        LoginDAO loginDAO = new LoginDAO();
        LoginService loginService = new LoginService(loginDAO);

        login.setBenutzername("admin");
        login.setVorname("Admin");
        login.setNachname("Admin");
        login.setPasswort("admin");
        login.setRolle(Rolle.ADMIN);
        loginService.create(login);
    }
}
