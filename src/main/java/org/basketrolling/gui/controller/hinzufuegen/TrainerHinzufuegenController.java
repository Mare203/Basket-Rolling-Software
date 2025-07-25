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
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.TrainerService;
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class TrainerHinzufuegenController implements Initializable {

    TrainerDAO dao;
    TrainerService service;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private TextField tfTelefon;

    @FXML
    private TextField tfEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new TrainerDAO();
        service = new TrainerService(dao);
    }

    public void speichern() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()) {
            
            Trainer trainer = new Trainer();
            trainer.setVorname(tfVorname.getText());
            trainer.setNachname(tfNachname.getText());
            trainer.setTelefon(tfTelefon.getText());
            trainer.setEMail(tfEmail.getText());

            service.create(trainer);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Trainer erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie einen weiteren Trainer anlegen?");

            ButtonType jaButton = new ButtonType("Ja");
            ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(jaButton, neinButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == neinButton) {
                Stage stage = (Stage) tfVorname.getScene().getWindow();
                stage.close();
            } else {
                tfVorname.clear();
                tfNachname.clear();
                tfTelefon.clear();
                tfEmail.clear();
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler","Unvollständige oder ungültige Eingaben","- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }
}
