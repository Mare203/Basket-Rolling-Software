/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.basketrolling.beans.Halle;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.service.HalleService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class HalleHinzufuegenController implements Initializable {

    HalleDAO dao;
    HalleService service;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfStrasse;

    @FXML
    private TextField tfOrt;

    @FXML
    private TextField tfPlz;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new HalleDAO();
        service = new HalleService(dao);
    }

    public void speichern() {
        if (!tfName.getText().isEmpty()
                && !tfStrasse.getText().isEmpty()
                && !tfOrt.getText().isEmpty()
                && !tfPlz.getText().isEmpty()) {
            Halle halle = new Halle();
            halle.setName(tfName.getText());
            halle.setStrasse(tfStrasse.getText());
            halle.setOrt(tfOrt.getText());
            halle.setPlz(Integer.parseInt(tfPlz.getText()));

            service.create(halle);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Halle erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie eine weitere Halle anlegen?");

            ButtonType jaButton = new ButtonType("Ja");
            ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(jaButton, neinButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == neinButton) {
                MenuUtil.fensterSchliessenOhneWarning(tfName);
            } else {
                tfName.clear();
                tfStrasse.clear();
                tfOrt.clear();
                tfPlz.clear();
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarning(tfName);
    }
}
