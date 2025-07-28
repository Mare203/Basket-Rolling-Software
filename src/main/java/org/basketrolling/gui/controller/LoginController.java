/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class LoginController implements Initializable {

    LoginDAO dao;
    LoginService service;

    @FXML
    private TextField benutzernameFeld;
    @FXML
    private PasswordField passwortFeld;
    @FXML
    private Button anmeldeBtn;
    @FXML
    private Button abbruchBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dao = new LoginDAO();
            this.service = new LoginService(dao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void anmelden() {
        String benutzername = benutzernameFeld.getText();
        String passwort = passwortFeld.getText();

        Login user = service.pruefung(benutzername, passwort);

        if (user != null) {
            openHauptmenue(user);
            MenuUtil.fensterSchliessenOhneWarnung(anmeldeBtn);
        } else {
            AlertUtil.alertError("Fehler", "Benutzername und/oder Passwort falsch!");
        }
    }

    private void openHauptmenue(Login benutzer) {
        HauptmenueController controller = MenuUtil.neuesFensterMaximiertAnzeigen("/org/basketrolling/gui/fxml/hauptmenu/hauptmenue.fxml", "Hauptmenü - Basket Rolling", "/org/basketrolling/gui/css/styles.css");

        if (controller != null) {
            controller.initUser(benutzer);
        }
    }

    @FXML
    private void abbruch() {
        boolean beenden = AlertUtil.confirmationMitJaNein("Bestätigung", "Programm schließen", "Sind Sie sicher, dass Sie das Programm beenden möchten?");

        if (beenden) {
            MenuUtil.fensterSchliessenOhneWarnung(anmeldeBtn);
        }
    }
}
