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
 *
 * @author Marko
 */
public class PasswortNeuController implements Initializable, MainBorderSettable {

    LoginDAO dao;
    LoginService service;

    private Login aktuellerLogin;

    @FXML
    private PasswordField pfPasswort;

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LoginDAO();
        service = new LoginService(dao);
    }

    public void initLogin(Login login) {
        this.aktuellerLogin = login;
    }

    public void setMainBorder(BorderPane mainBorderPane) {
        this.borderPane = mainBorderPane;
    }

    public void speichern() {
        aktuellerLogin.setPasswort(pfPasswort.getText());
        service.update(aktuellerLogin);
        AlertUtil.alertConfirmation("Speichern erfolgreich", "Passwort wurde erfolgreich geändert!");
        MenuUtil.fensterSchliessenOhneWarnung(pfPasswort);
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(pfPasswort);
    }
}
