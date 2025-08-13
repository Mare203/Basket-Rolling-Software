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
 * Controller-Klasse zum Setzen eines neuen Passworts nach erfolgreicher
 * Überprüfung des aktuellen Passworts.
 * <p>
 * Diese Klasse ist der zweite Schritt des Passwortänderungsprozesses: Der
 * Benutzer gibt hier das neue Passwort ein, welches dann in der Datenbank
 * gespeichert wird.
 * </p>
 *
 * <p>
 * <b>Funktionen:</b></p>
 * <ul>
 * <li>Speichern des neuen Passworts in der Datenbank</li>
 * <li>Bestätigung der erfolgreichen Änderung</li>
 * <li>Abbrechen des Vorgangs mit optionaler Bestätigung</li>
 * </ul>
 *
 * Implementiert {@link Initializable} für die Initialisierung der
 * Controller-Logik und {@link MainBorderSettable}, um das
 * Haupt-{@link BorderPane} zu setzen.
 *
 * @author Marko
 */
public class PasswortNeuController implements Initializable, MainBorderSettable {

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
     * Speichert das neue Passwort für den aktuellen Benutzer.
     * <ul>
     * <li>Aktualisiert das Passwort-Attribut des {@link Login}-Objekts</li>
     * <li>Schreibt die Änderung in die Datenbank</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     */
    public void speichern() {
        aktuellerLogin.setPasswort(pfPasswort.getText());
        service.update(aktuellerLogin);
        AlertUtil.alertConfirmation("Speichern erfolgreich", "Passwort wurde erfolgreich geändert!");
        MenuUtil.fensterSchliessenOhneWarnung(pfPasswort);
    }

    /**
     * Bricht die Passwortänderung ab und schließt das Fenster mit einer
     * Bestätigungsabfrage.
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(pfPasswort);
    }
}
