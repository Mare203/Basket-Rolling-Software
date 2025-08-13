/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Bearbeiten einer {@link MannschaftExtern}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten einer bestehenden externen
 * Mannschaft. Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht
 * Änderungen und speichert diese über den {@link MannschaftExternService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service, Ligenliste) vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Name der Mannschaft</li>
 * <li>Auswahl einer {@link Liga}</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class MannschaftExternBearbeitenController implements Initializable {

    private MannschaftExtern bearbeitenMannschaft;

    private MannschaftExternDAO dao;
    private MannschaftExternService service;

    private LigaDAO ligaDao;
    private LigaService ligaService;

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<Liga> cbLiga;

    /**
     * Initialisiert den Controller und lädt die Liste aller Ligen.
     * <ul>
     * <li>Erstellt DAO- und Service-Instanzen für Mannschaften und Ligen</li>
     * <li>Lädt die vorhandenen Ligen aus der Datenbank</li>
     * <li>Befüllt die Liga-Auswahl-{@link ComboBox}</li>
     * <li>Deaktiviert die {@link ComboBox}, falls keine Ligen vorhanden
     * sind</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new MannschaftExternDAO();
        service = new MannschaftExternService(dao);

        ligaDao = new LigaDAO();
        ligaService = new LigaService(ligaDao);

        List<Liga> liga = ligaService.getAll();

        if (liga != null && !liga.isEmpty()) {
            cbLiga.setItems(FXCollections.observableArrayList(liga));
        } else {
            cbLiga.setDisable(true);
        }
    }

    /**
     * Lädt die Daten der zu bearbeitenden {@link MannschaftExtern} in die
     * Eingabefelder.
     *
     * @param mannschaftExtern die zu bearbeitende {@link MannschaftExtern}
     */
    public void initMannschaftExtern(MannschaftExtern mannschaftExtern) {
        this.bearbeitenMannschaft = mannschaftExtern;

        tfName.setText(mannschaftExtern.getName());
        cbLiga.setValue(mannschaftExtern.getLiga());
    }

    /**
     * Speichert die Änderungen an der externen Mannschaft.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Aktualisiert das {@link MannschaftExtern}-Objekt mit den neuen
     * Werten</li>
     * <li>Speichert die Änderungen über den
     * {@link MannschaftExternService}</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * Wenn Pflichtfelder fehlen oder ungültig sind, wird ein Warnhinweis
     * angezeigt.
     * </p>
     */
    public void aktualisieren() {
        if (!tfName.getText().isEmpty() && cbLiga.getValue() != null) {

            bearbeitenMannschaft.setName(tfName.getText());
            bearbeitenMannschaft.setLiga(cbLiga.getValue());

            service.update(bearbeitenMannschaft);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Mannschaft erfolgreich aktualisiert!");
            MenuUtil.fensterSchliessenOhneWarnung(tfName);
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
