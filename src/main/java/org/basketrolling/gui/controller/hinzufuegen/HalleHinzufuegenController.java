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
import org.basketrolling.beans.Halle;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.service.HalleService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class HalleHinzufuegenController implements Initializable {

    HalleDAO dao;
    HalleService service;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfStrasse;

    @FXML
    private TextField tfOrt;

    @FXML
    private TextField tfPlz;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new HalleDAO();
        service = new HalleService(dao);
    }

    public void speichern() {
        String plzText = tfPlz.getText().trim();

        if (!tfName.getText().isEmpty()
                && !tfStrasse.getText().isEmpty()
                && !tfOrt.getText().isEmpty()
                && !plzText.isEmpty()) {

            try {
                int plz = Integer.parseInt(plzText);

                Halle halle = new Halle();
                halle.setName(tfName.getText());
                halle.setStrasse(tfStrasse.getText());
                halle.setOrt(tfOrt.getText());
                halle.setPlz(plz);

                service.create(halle);

                boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Halle erfolgreich gespeichert!", "Möchten Sie eine weitere Halle anlegen?");

                if (!weiter) {
                    MenuUtil.fensterSchliessenOhneWarnung(tfName);
                } else {
                    tfName.clear();
                    tfStrasse.clear();
                    tfOrt.clear();
                    tfPlz.clear();
                }

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige PLZ", "Postleitzahl muss eine Zahl sein.", "Beispiel: 1010, 1220, 8010");
            }

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfName);
    }
}
