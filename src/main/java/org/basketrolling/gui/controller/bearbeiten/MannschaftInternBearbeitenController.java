/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.TrainerService;
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class MannschaftInternBearbeitenController implements Initializable {

    MannschaftIntern bearbeitenMannschaft;

    MannschaftInternDAO dao;
    MannschaftInternService service;

    LigaDAO ligaDao;
    LigaService ligaService;

    TrainerDAO trainerDao;
    TrainerService trainerService;

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<Liga> cbLiga;

    @FXML
    private ComboBox<Trainer> cbTrainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new MannschaftInternDAO();
        service = new MannschaftInternService(dao);

        ligaDao = new LigaDAO();
        ligaService = new LigaService(ligaDao);

        trainerDao = new TrainerDAO();
        trainerService = new TrainerService(trainerDao);

        List<Liga> liga = ligaService.getAll();

        if (!liga.isEmpty() && liga != null) {
            cbLiga.setItems(FXCollections.observableArrayList(liga));
        } else {
            cbLiga.setDisable(true);
        }

        List<Trainer> trainer = trainerService.getAll();

        if (!trainer.isEmpty() && trainer != null) {
            cbTrainer.setItems(FXCollections.observableArrayList(trainer));
        } else {
            cbTrainer.setDisable(true);
        }
    }

    public void initMannschaftIntern(MannschaftIntern mannschaftIntern) {
        this.bearbeitenMannschaft = mannschaftIntern;

        tfName.setText(mannschaftIntern.getName());
        cbLiga.setValue(mannschaftIntern.getLiga());
        cbTrainer.setValue(mannschaftIntern.getTrainer());
    }

    public void aktualisieren() {
        if (!tfName.getText().isEmpty() && cbLiga.getValue() != null) {

            bearbeitenMannschaft.setName(tfName.getText());
            bearbeitenMannschaft.setLiga(cbLiga.getValue());
            bearbeitenMannschaft.setTrainer(cbTrainer.getValue());

            service.update(bearbeitenMannschaft);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Mannschaft erfolgreich aktualisiert!");

            Stage stage = (Stage) tfName.getScene().getWindow();
            stage.close();

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }
}
