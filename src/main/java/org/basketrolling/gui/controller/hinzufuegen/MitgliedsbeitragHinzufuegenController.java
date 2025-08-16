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
 * Controller-Klasse zum Hinzufügen eines Mitgliedsbeitrags.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Mitgliedsbeiträgen.
 * Über Eingabefelder können Saison und Beitragshöhe erfasst und über den
 * {@link MitgliedsbeitragService} in der Datenbank gespeichert werden.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren.
 * </p>
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

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt eine neue Instanz von {@link MitgliedsbeitragDAO} und
     * {@link MitgliedsbeitragService}.
     * </p>
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
     * Speichert den eingegebenen Mitgliedsbeitrag.
     * <p>
     * Liest Saison und Betrag aus den Eingabefeldern, validiert die Werte
     * (Betrag muss eine gültige Zahl ≥ 0 sein), erstellt ein
     * {@link Mitgliedsbeitrag}-Objekt und übergibt es an den
     * {@link MitgliedsbeitragService}.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob ein weiterer
     * Mitgliedsbeitrag angelegt werden soll. Bei „Nein“ wird das Fenster
     * geschlossen, bei „Ja“ werden die Eingabefelder geleert.
     * </p>
     * <p>
     * Falls die Eingaben ungültig sind oder Pflichtfelder fehlen, wird eine
     * Warnung über {@link AlertUtil} angezeigt.
     * </p>
     */
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
