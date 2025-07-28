/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.enums.Rolle;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class UserHinzufuegenController implements Initializable {

    LoginDAO dao;
    LoginService service;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private TextField tfBenutzername;

    @FXML
    private TextField pfPasswort;

    @FXML
    private ComboBox<Rolle> cbRolle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LoginDAO();
        service = new LoginService(dao);

        cbRolle.setItems(FXCollections.observableArrayList(Rolle.values()));
        cbRolle.setValue(Rolle.ADMIN);
    }

    public void speichern() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfBenutzername.getText().isEmpty()
                && !pfPasswort.getText().isEmpty()
                && cbRolle.getValue() != null) {

            Login login = new Login();
            login.setVorname(tfVorname.getText());
            login.setNachname(tfNachname.getText());
            login.setBenutzername(tfBenutzername.getText());
            login.setPasswort(pfPasswort.getText());
            login.setRolle(cbRolle.getValue());

            service.create(login);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("User erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie einen weiteren User anlegen?");

            ButtonType jaButton = new ButtonType("Ja");
            ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(jaButton, neinButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == neinButton) {
                MenuUtil.fensterSchliessenOhneWarnung(tfBenutzername);
            } else {
                tfVorname.clear();
                tfNachname.clear();
                tfBenutzername.clear();
                pfPasswort.clear();
                cbRolle.setValue(null);
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfBenutzername);
    }
}
