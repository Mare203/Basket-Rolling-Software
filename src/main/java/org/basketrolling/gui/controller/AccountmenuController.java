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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Account-Menü.
 * <p>
 * Diese Klasse verwaltet die Anzeige der aktuellen Login-Daten des Benutzers
 * sowie die Navigation zur Passwortänderungsansicht.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable} für die Initialisierung beim Laden
 * des FXML-Layouts und {@link MainBorderSettable}, um das zentrale
 * {@link BorderPane} der Anwendung zu setzen.
 * </p>
 *
 * @author Marko
 */
public class AccountmenuController implements Initializable, MainBorderSettable {

    private LoginDAO dao;
    private LoginService service;

    /**
     * Der aktuell angemeldete Benutzer.
     */
    private Login aktuellerLogin;

    @FXML
    private Label lVorname;

    @FXML
    private Label lNachname;

    @FXML
    private Label lBenutzername;

    @FXML
    private Label lRolle;

    @FXML
    private Button passwortChange;

    @FXML
    private BorderPane borderPane;

    /**
     * Initialisiert den Controller. Erstellt eine neue Instanz von
     * {@link LoginDAO} und {@link LoginService}.
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
     * Übergibt die Login-Daten des aktuell angemeldeten Benutzers und zeigt
     * diese in den entsprechenden Labels an.
     *
     * @param login das {@link Login}-Objekt des angemeldeten Benutzers
     */
    public void initLogin(Login login) {
        this.aktuellerLogin = login;

        lVorname.setText(login.getVorname());
        lNachname.setText(login.getNachname());
        lBenutzername.setText(login.getBenutzername());
        lRolle.setText(login.getRolle().toString());
    }

    /**
     * Setzt das Haupt-{@link BorderPane} für diesen Controller.
     *
     * @param mainBorderPane das zentrale {@link BorderPane} der Anwendung
     */
    @Override
    public void setMainBorder(BorderPane mainBorderPane) {
        this.borderPane = mainBorderPane;
    }

    /**
     * Öffnet den Passwort-Änderungsbildschirm und übergibt den aktuellen Login.
     * <p>
     * Lädt das FXML-Layout für die Passwortänderung und initialisiert den
     * entsprechenden Controller mit den Login-Daten des aktuellen Benutzers.
     * </p>
     */
    public void passwortChange() {
        PasswortChangeController controller = MenuUtil.ladeMenuUndSetzeCenter(
                borderPane,
                "/org/basketrolling/gui/fxml/login/passwortcheck.fxml"
        );

        if (controller != null) {
            controller.initLogin(aktuellerLogin);
        }
    }
}
