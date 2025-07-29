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
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class PasswortChangeController implements Initializable, MainBorderSettable {

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

    public void weiterToNeuesPasswort() {
        Login loginPruefung = service.pruefung(aktuellerLogin.getBenutzername(), pfPasswort.getText());

        if (loginPruefung != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/login/neuespasswort.fxml"));
                Parent neuesPasswort = loader.load();

                PasswortNeuController controller = loader.getController();
                controller.initLogin(aktuellerLogin);
                controller.setMainBorder(borderPane);

                borderPane.setCenter(neuesPasswort);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwort falsch");
            alert.setHeaderText("Ungültiges Passwort eingegeben!");
            alert.show();
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(pfPasswort);
    }
}
