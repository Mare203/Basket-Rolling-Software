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
 * Controller-Klasse für das Bearbeiten eines {@link Mitgliedsbeitrag}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten eines bestehenden
 * Mitgliedsbeitrags. Sie lädt die aktuellen Daten in die Eingabefelder,
 * ermöglicht Änderungen und speichert diese über den
 * {@link MitgliedsbeitragService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service) vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Saison</li>
 * <li>Betrag (muss eine positive Zahl sein)</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class MitgliedsbeitragBearbeitenController implements Initializable {

    private Mitgliedsbeitrag bearbeitenMitgliedsbeitrag;

    private MitgliedsbeitragDAO dao;
    private MitgliedsbeitragService service;

    @FXML
    private TextField tfSaison;

    @FXML
    private TextField tfBetrag;

    /**
     * Initialisiert den Controller und erstellt DAO- und Service-Instanzen.
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new MitgliedsbeitragDAO();
        service = new MitgliedsbeitragService(dao);
    }

    /**
     * Lädt die Daten des zu bearbeitenden {@link Mitgliedsbeitrag} in die
     * Eingabefelder.
     *
     * @param mitgliedsbeitrag der zu bearbeitende {@link Mitgliedsbeitrag}
     */
    public void initMitgliedsbeitrag(Mitgliedsbeitrag mitgliedsbeitrag) {
        this.bearbeitenMitgliedsbeitrag = mitgliedsbeitrag;

        tfSaison.setText(mitgliedsbeitrag.getSaison());
        tfBetrag.setText(String.valueOf(mitgliedsbeitrag.getBetrag()));
    }

    /**
     * Speichert die Änderungen am Mitgliedsbeitrag.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Parst den Betrag als Zahl (unterstützt Komma oder Punkt als
     * Dezimaltrennzeichen)</li>
     * <li>Stellt sicher, dass der Betrag nicht negativ ist</li>
     * <li>Aktualisiert das {@link Mitgliedsbeitrag}-Objekt mit den neuen
     * Werten</li>
     * <li>Speichert die Änderungen über den
     * {@link MitgliedsbeitragService}</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * Wenn der Betrag ungültig ist oder negativ, wird ein Warnhinweis
     * angezeigt. Wenn Pflichtfelder fehlen, wird ebenfalls ein Warnhinweis
     * angezeigt.
     * </p>
     */
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
                AlertUtil.alertWarning("Ungültiger Betrag", "Der Betrag muss eine gültige Zahl (z. B. 20.00) sein.");
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    /**
     * Bricht den Bearbeitungsvorgang ab und schließt das Fenster.
     * <p>
     * Vor dem Schließen wird der Benutzer um eine Bestätigung gebeten.
     * </p>
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfSaison);
    }
}
