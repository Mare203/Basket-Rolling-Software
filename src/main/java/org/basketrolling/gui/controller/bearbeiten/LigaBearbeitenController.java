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
import org.basketrolling.beans.Liga;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.service.LigaService;
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class LigaBearbeitenController implements Initializable {

    Liga bearbeitenLiga;

    LigaDAO dao;
    LigaService service;

    @FXML
    private TextField tfName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LigaDAO();
        service = new LigaService(dao);
    }

    public void initLiga(Liga liga) {
        this.bearbeitenLiga = liga;

        tfName.setText(liga.getName());
    }

    public void aktualisieren() {
        if (!tfName.getText().isEmpty()) {

            bearbeitenLiga.setName(tfName.getText());

            service.update(bearbeitenLiga);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Liga erfolgreich aktualisiert!");

            Stage stage = (Stage) tfName.getScene().getWindow();
            stage.close();

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }
}
