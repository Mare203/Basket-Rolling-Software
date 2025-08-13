/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse zum Überprüfen des aktuellen Passworts vor der Änderung.
 * <p>
 * Diese Klasse dient als Zwischenschritt bei der Passwortänderung: Der Benutzer
 * muss zunächst sein aktuelles Passwort eingeben. Bei erfolgreicher Überprüfung
 * wird der Dialog für das neue Passwort geöffnet.
 * </p>
 *
 * <p>
 * <b>Funktionen:</b></p>
 * <ul>
 * <li>Überprüfung des eingegebenen Passworts gegen die Datenbank</li>
 * <li>Weiterleitung zum Fenster zur Eingabe eines neuen Passworts</li>
 * <li>Abbrechen der Aktion mit optionaler Bestätigung</li>
 * </ul>
 *
 * Implementiert {@link Initializable} für die Initialisierung der
 * Controller-Logik und {@link MainBorderSettable}, um das
 * Haupt-{@link BorderPane} zu setzen.
 *
 * @author Marko
 */
public class PasswortChangeController implements Initializable, MainBorderSettable {

    private LoginDAO dao;
    private LoginService service;
    private Login aktuellerLogin;

    @FXML
    private PasswordField pfPasswort;

    @FXML
    private BorderPane borderPane;

    /**
     * Initialisiert den Controller, legt DAO und Service an.
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LoginDAO();
        service = new LoginService(dao);
    }

    /**
     * Übergibt das aktuell eingeloggte {@link Login}-Objekt an den Controller.
     *
     * @param login das aktuelle Login-Objekt
     */
    public void initLogin(Login login) {
        this.aktuellerLogin = login;
    }

    /**
     * Setzt das Haupt-{@link BorderPane}.
     *
     * @param mainBorderPane das zentrale {@link BorderPane} der Anwendung
     */
    @Override
    public void setMainBorder(BorderPane mainBorderPane) {
        this.borderPane = mainBorderPane;
    }

    /**
     * Prüft das eingegebene Passwort.
     * <ul>
     * <li>Ist es korrekt, wird das Fenster zum Setzen des neuen Passworts
     * geladen.</li>
     * <li>Ist es falsch, erscheint eine Fehlermeldung.</li>
     * </ul>
     */
    public void weiterToNeuesPasswort() {
        Login loginPruefung = service.pruefung(aktuellerLogin.getBenutzername(), pfPasswort.getText());

        if (loginPruefung != null) {
            PasswortNeuController controller
                    = MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/login/neuespasswort.fxml");

            if (controller != null) {
                controller.initLogin(aktuellerLogin);
            }
        } else {
            AlertUtil.alertError("Passwort falsch", "Ungültiges Passwort eingegeben!");
        }
    }

    /**
     * Bricht die Passwortänderung ab und schließt das Fenster.
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(pfPasswort);
    }
}
