/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class MannschaftInternHinzufuegenController implements Initializable {

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

    public void speichern() {
        if (!tfName.getText().isEmpty() && cbLiga.getValue() != null) {
            MannschaftIntern mannschaft = new MannschaftIntern();
            mannschaft.setName(tfName.getText());
            mannschaft.setLiga(cbLiga.getValue());
            mannschaft.setTrainer(cbTrainer.getValue());

            service.create(mannschaft);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Mannschaft erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie eine weitere Mannschaft anlegen?");

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
