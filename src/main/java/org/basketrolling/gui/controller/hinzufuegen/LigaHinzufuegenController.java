/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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

            boolean weiter = MenuUtil.fensterSchliessenMitEigenerWarnung("Speichern erfolgreich", "Liga erfolgreich gespeichert!", "Möchten Sie eine weitere Liga anlegen?");

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
