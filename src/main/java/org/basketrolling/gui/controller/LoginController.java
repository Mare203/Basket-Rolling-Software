/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse für den Login-Bildschirm.
 * <p>
 * Diese Klasse ist für die Authentifizierung der Benutzer zuständig. Sie prüft
 * die eingegebenen Zugangsdaten, meldet den Benutzer an und öffnet bei
 * erfolgreicher Anmeldung das Hauptmenü.
 * </p>
 *
 * <p>
 * <b>Funktionen:</b></p>
 * <ul>
 * <li>Initialisierung der DAO- und Service-Instanzen für den Login</li>
 * <li>Validierung der eingegebenen Benutzer- und Passwortdaten</li>
 * <li>Speichern der Benutzersession bei erfolgreicher Anmeldung</li>
 * <li>Abbruch der Anmeldung mit Bestätigungsdialog</li>
 * </ul>
 *
 * @author Marko
 */
public class LoginController implements Initializable {

    private LoginDAO dao;
    private LoginService service;

    @FXML
    private TextField benutzernameFeld;

    @FXML
    private PasswordField passwortFeld;

    @FXML
    private Button anmeldeBtn;

    @FXML
    private Button abbruchBtn;

    /**
     * Initialisiert den Controller und erstellt die notwendigen DAO- und
     * Service-Instanzen für den Loginprozess.
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dao = new LoginDAO();
            this.service = new LoginService(dao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Führt den Anmeldevorgang durch.
     * <p>
     * Prüft, ob die eingegebenen Anmeldedaten gültig sind. Bei Erfolg wird die
     * Benutzersession gesetzt und das Hauptmenü geöffnet, andernfalls erscheint
     * eine Fehlermeldung.
     * </p>
     */
    @FXML
    private void anmelden() {
        String benutzername = benutzernameFeld.getText();
        String passwort = passwortFeld.getText();

        Login user = service.pruefung(benutzername, passwort);

        if (user != null) {
            Session.setBenutzer(user);
            openHauptmenue(user);
            MenuUtil.fensterSchliessenOhneWarnung(anmeldeBtn);
        } else {
            AlertUtil.alertError("Fehler", "Benutzername und/oder Passwort falsch!");
        }
    }

    /**
     * Öffnet das Hauptmenü im Vollbildmodus und übergibt den angemeldeten
     * Benutzer.
     *
     * @param benutzer das {@link Login}-Objekt des angemeldeten Benutzers
     */
    private void openHauptmenue(Login benutzer) {
        HauptmenueController controller = MenuUtil.neuesFensterMaximiertAnzeigen(
                "/org/basketrolling/gui/fxml/hauptmenu/hauptmenue.fxml",
                "Hauptmenü - Basket Rolling",
                "/org/basketrolling/gui/css/styles.css"
        );

        if (controller != null) {
            controller.initUser(benutzer);
        }
    }

    /**
     * Bricht den Anmeldevorgang ab und schließt die Anwendung nach einer
     * Bestätigungsabfrage.
     */
    @FXML
    private void abbruch() {
        boolean beenden = AlertUtil.confirmationMitJaNein(
                "Bestätigung",
                "Programm schließen",
                "Sind Sie sicher, dass Sie das Programm beenden möchten?"
        );

        if (beenden) {
            MenuUtil.fensterSchliessenOhneWarnung(anmeldeBtn);
        }
    }
}
