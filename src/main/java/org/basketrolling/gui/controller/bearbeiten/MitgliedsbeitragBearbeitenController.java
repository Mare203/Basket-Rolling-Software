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
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

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
        String saisonText = tfSaison.getText().trim();
        String betragText = tfBetrag.getText().trim();

        if (!saisonText.isEmpty() && !betragText.isEmpty()) {

            try {
                double betrag = Double.parseDouble(betragText.replace(",", "."));

                if (betrag < 0) {
                    throw new NumberFormatException();
                }

                bearbeitenMitgliedsbeitrag.setSaison(saisonText);
                bearbeitenMitgliedsbeitrag.setBetrag(betrag);

                service.update(bearbeitenMitgliedsbeitrag);
                AlertUtil.alertConfirmation("Speichern erfolgreich", "Mitgliedsbeitrag erfolgreich aktualisiert!");
                MenuUtil.fensterSchliessenOhneWarnung(tfSaison);

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültiger Betrag", "Der Betrag muss eine gültige Zahl (z. B. 20.00) sein.");
            }

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfSaison);
    }
}
