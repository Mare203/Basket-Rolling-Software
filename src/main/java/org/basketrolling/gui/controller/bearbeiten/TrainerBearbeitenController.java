/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class TrainerBearbeitenController implements Initializable {

    Trainer bearbeitenTrainer;

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

    public void initTrainer(Trainer trainer) {
        this.bearbeitenTrainer = trainer;

        tfVorname.setText(trainer.getVorname());
        tfNachname.setText(trainer.getNachname());
        tfTelefon.setText(String.valueOf(trainer.getTelefon()));
        tfEmail.setText(trainer.getEMail());
    }

    public void aktualisieren() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()) {

            bearbeitenTrainer.setVorname(tfVorname.getText());
            bearbeitenTrainer.setNachname(tfNachname.getText());
            bearbeitenTrainer.setTelefon(tfTelefon.getText());
            bearbeitenTrainer.setEMail(tfEmail.getText());

            service.update(bearbeitenTrainer);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Trainer erfolgreich aktualisiert!");

            Stage stage = (Stage) tfVorname.getScene().getWindow();
            stage.close();

        } else {
            AlertUtil.alertWarning("Eingabefehler","Unvollständige oder ungültige Eingaben","- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }
}
