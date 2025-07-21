/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

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
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class MannschaftExternBearbeitenController implements Initializable {

    MannschaftExtern bearbeitenMannschaft;

    MannschaftExternDAO dao;
    MannschaftExternService service;

    LigaDAO ligaDao;
    LigaService ligaService;

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<Liga> cbLiga;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new MannschaftExternDAO();
        service = new MannschaftExternService(dao);

        ligaDao = new LigaDAO();
        ligaService = new LigaService(ligaDao);

        List<Liga> liga = ligaService.getAll();

        if (!liga.isEmpty() && liga != null) {
            cbLiga.setItems(FXCollections.observableArrayList(liga));
        } else {
            cbLiga.setDisable(true);
        }
    }

    public void initMannschaftExtern(MannschaftExtern mannschaftExtern) {
        this.bearbeitenMannschaft = mannschaftExtern;

        tfName.setText(mannschaftExtern.getName());
        cbLiga.setValue(mannschaftExtern.getLiga());
    }

    public void aktualisieren() {
        if (!tfName.getText().isEmpty() && cbLiga.getValue() != null) {

            bearbeitenMannschaft.setName(tfName.getText());
            bearbeitenMannschaft.setLiga(cbLiga.getValue());

            service.update(bearbeitenMannschaft);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Mannschaft erfolgreich aktualisiert!");

            Stage stage = (Stage) tfName.getScene().getWindow();
            stage.close();

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }
}
