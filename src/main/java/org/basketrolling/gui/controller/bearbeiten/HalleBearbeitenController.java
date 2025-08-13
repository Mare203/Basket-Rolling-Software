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
 * Controller-Klasse für das Bearbeiten einer {@link Halle}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten einer bestehenden Halle.
 * Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht Änderungen und
 * speichert diese über den {@link HalleService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service) vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Name</li>
 * <li>Straße</li>
 * <li>Ort</li>
 * <li>Postleitzahl (muss eine Zahl sein)</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class HalleBearbeitenController implements Initializable {

    private Halle bearbeitenhalle;

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
     * Initialisiert den Controller und erstellt DAO- und Service-Instanzen.
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
     * Lädt die Daten der zu bearbeitenden {@link Halle} in die Eingabefelder.
     *
     * @param halle die zu bearbeitende {@link Halle}
     */
    public void initHalle(Halle halle) {
        this.bearbeitenhalle = halle;

        tfName.setText(halle.getName());
        tfStrasse.setText(halle.getStrasse());
        tfOrt.setText(halle.getOrt());
        tfPlz.setText(String.valueOf(halle.getPlz()));
    }

    /**
     * Speichert die Änderungen an der Halle.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Parst die Postleitzahl als Zahl</li>
     * <li>Aktualisiert das {@link Halle}-Objekt mit den neuen Werten</li>
     * <li>Speichert die Änderungen über den {@link HalleService}</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * <p>
     * Wenn die Postleitzahl kein gültiger Integer ist, wird eine Warnung
     * angezeigt. Wenn Pflichtfelder fehlen oder ungültig sind, wird ebenfalls
     * eine Warnung angezeigt.
     * </p>
     */
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
