/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private Button infoBtn;

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

        Tooltip tooltip = infoBtn.getTooltip();
        Tooltip.uninstall(infoBtn, tooltip);
        tooltip.setText("Mannschaft müssen wie folgt gekennzeichnet werden: /1, /2, /3.\nBeispiel: Basket Rolling/1 LL, Basket Rolling/2 H2");

        infoBtn.setOnAction(e -> {
            if (tooltip.isShowing()) {
                tooltip.hide();
            } else {
                tooltip.show(infoBtn,
                        infoBtn.localToScreen(infoBtn.getBoundsInLocal()).getMinX(),
                        infoBtn.localToScreen(infoBtn.getBoundsInLocal()).getMaxY()
                );
            }
        });

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

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Mannschaft erfolgreich gespeichert!", "Möchten Sie eine weitere Mannschaft anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfName);
            } else {
                tfName.clear();
            }

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfName);
    }
}
