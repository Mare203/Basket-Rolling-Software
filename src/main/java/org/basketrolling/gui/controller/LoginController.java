/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.service.LoginService;

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
            Stage currentStage = (Stage) anmeldeBtn.getScene().getWindow();
            currentStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Benutzername und/oder Passwort falsch!");
            alert.showAndWait();
        }
    }

    private void openHauptmenue(Login benutzer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/hauptmenu/hauptmenue.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/org/basketrolling/gui/css/styles.css").toExternalForm());

            HauptmenueController hauptmenueController = loader.getController();
            hauptmenueController.initUser(benutzer);

            Stage stage = new Stage();
            stage.setTitle("Hauptmenü - Basket Rolling");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Laden des Hauptmenüs");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void abbruch() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bestätigung");
        alert.setHeaderText("Programm schließen");
        alert.setContentText("Sind Sie sicher, dass Sie das Programm beenden möchten?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) abbruchBtn.getScene().getWindow();
            stage.close();
        }
    }
}
