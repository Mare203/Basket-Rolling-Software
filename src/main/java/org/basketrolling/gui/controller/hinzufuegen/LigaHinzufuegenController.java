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
import org.basketrolling.beans.Liga;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.service.LigaService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class LigaHinzufuegenController implements Initializable {

    LigaDAO dao;
    LigaService service;

    @FXML
    private TextField tfName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LigaDAO();
        service = new LigaService(dao);
    }

    public void speichern() {
        if (!tfName.getText().isEmpty()) {
            Liga liga = new Liga();
            liga.setName(tfName.getText());

            service.create(liga);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Liga erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie eine weitere Liga anlegen?");

            ButtonType jaButton = new ButtonType("Ja");
            ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(jaButton, neinButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == neinButton) {
                MenuUtil.fensterSchliessenOhneWarning(tfName);
            } else {
                tfName.clear();
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarning(tfName);
    }
}
