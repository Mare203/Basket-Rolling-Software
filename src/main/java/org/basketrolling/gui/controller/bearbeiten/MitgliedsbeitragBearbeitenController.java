/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import org.basketrolling.gui.controller.hinzufuegen.*;
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
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class MitgliedsbeitragBearbeitenController implements Initializable {

    Mitgliedsbeitrag bearbeitenMitgliedsbeitrag;

    MitgliedsbeitragDAO dao;
    MitgliedsbeitragService service;

    @FXML
    private TextField tfSaison;

    @FXML
    private TextField tfBetrag;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new MitgliedsbeitragDAO();
        service = new MitgliedsbeitragService(dao);
    }

    public void initMitgliedsbeitrag(Mitgliedsbeitrag mitgliedsbeitrag) {
        this.bearbeitenMitgliedsbeitrag = mitgliedsbeitrag;

        tfSaison.setText(mitgliedsbeitrag.getSaison());
        tfBetrag.setText(String.valueOf(mitgliedsbeitrag.getBetrag()));
    }

    public void aktualisieren() {
        if (!tfSaison.getText().isEmpty() && !tfBetrag.getText().isEmpty()) {

            bearbeitenMitgliedsbeitrag.setSaison(tfSaison.getText());
            bearbeitenMitgliedsbeitrag.setBetrag(Double.parseDouble(tfBetrag.getText()));

            service.update(bearbeitenMitgliedsbeitrag);
            AlertUtil.alertConfirmation("Speichern erfolgreich", "Mitgliedsbeitrag erfolgreich aktualisiert!");

            Stage stage = (Stage) tfSaison.getScene().getWindow();
            stage.close();

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }
}
