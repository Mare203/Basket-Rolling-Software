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
 * Controller-Klasse zum Hinzufügen einer Halle.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Hallen mit den
 * Attributen Name, Straße, Ort und Postleitzahl. Über ein Formular können die
 * Daten erfasst und über den {@link HalleService} in der Datenbank gespeichert
 * werden.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren.
 * </p>
 *
 * @author Marko
 */
public class HalleHinzufuegenController implements Initializable {

    private HalleDAO dao;
    private HalleService service;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfStrasse;

    @FXML
    private TextField tfOrt;

    @FXML
    private TextField tfPlz;

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt eine neue Instanz von {@link HalleDAO} und {@link HalleService}.
     * </p>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new HalleDAO();
        service = new HalleService(dao);
    }

    /**
     * Speichert die eingegebene Halle.
     * <p>
     * Liest die Daten aus den Eingabefeldern, prüft die Pflichtfelder und
     * validiert die Postleitzahl. Bei korrekten Eingaben wird eine neue
     * {@link Halle} erstellt und über den {@link HalleService} gespeichert.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob eine weitere
     * Halle angelegt werden soll. Bei „Nein“ wird das Fenster geschlossen, bei
     * „Ja“ werden die Eingabefelder geleert.
     * </p>
     * <p>
     * Falls die Postleitzahl keine Zahl ist oder Pflichtfelder fehlen, wird
     * eine Warnung über {@link AlertUtil} angezeigt.
     * </p>
     */
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

    /**
     * Bricht den Bearbeitungsvorgang ab und schließt das Fenster.
     * <p>
     * Vor dem Schließen wird der Benutzer um eine Bestätigung gebeten.
     * </p>
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfName);
    }
}
