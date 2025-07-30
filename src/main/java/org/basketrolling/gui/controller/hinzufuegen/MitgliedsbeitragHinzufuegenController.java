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
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class MitgliedsbeitragHinzufuegenController implements Initializable {

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

    public void speichern() {
        String saisonText = tfSaison.getText().trim();
        String betragText = tfBetrag.getText().trim();

        if (!saisonText.isEmpty() && !betragText.isEmpty()) {
            try {
                double betrag = Double.parseDouble(betragText.replace(",", "."));

                if (betrag < 0) {
                    throw new NumberFormatException();
                }

                Mitgliedsbeitrag mitgliedsbeitrag = new Mitgliedsbeitrag();
                mitgliedsbeitrag.setSaison(saisonText);
                mitgliedsbeitrag.setBetrag(betrag);

                service.create(mitgliedsbeitrag);

                boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Mitgliedsbeitrag erfolgreich gespeichert!", "Möchten Sie einen weiteren Mitgliedsbeitrag anlegen?");

                if (!weiter) {
                    MenuUtil.fensterSchliessenOhneWarnung(tfSaison);
                } else {
                    tfSaison.clear();
                    tfBetrag.clear();
                }

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültiger Betrag", "Bitte geben Sie eine gültige Zahl ein.", "Beispiel: 50,00 oder 75.50");
            }

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfSaison);
    }
}
