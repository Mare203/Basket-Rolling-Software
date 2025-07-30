/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

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
public class HalleBearbeitenController implements Initializable {

    Halle bearbeitenhalle;

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

    public void initHalle(Halle halle) {
        this.bearbeitenhalle = halle;

        tfName.setText(halle.getName());
        tfStrasse.setText(halle.getStrasse());
        tfOrt.setText(halle.getOrt());
        tfPlz.setText(String.valueOf(halle.getPlz()));
    }

    public void aktualisieren() {
        String plzText = tfPlz.getText().trim();

        if (!tfName.getText().isEmpty()
                && !tfStrasse.getText().isEmpty()
                && !tfOrt.getText().isEmpty()
                && !plzText.isEmpty()) {

            try {
                int plz = Integer.parseInt(plzText);

                bearbeitenhalle.setName(tfName.getText());
                bearbeitenhalle.setStrasse(tfStrasse.getText());
                bearbeitenhalle.setOrt(tfOrt.getText());
                bearbeitenhalle.setPlz(plz);

                service.create(bearbeitenhalle);

                AlertUtil.alertConfirmation("Speichern erfolgreich", "Halle erfolgreich aktualisiert!");
                MenuUtil.fensterSchliessenOhneWarnung(tfName);

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
