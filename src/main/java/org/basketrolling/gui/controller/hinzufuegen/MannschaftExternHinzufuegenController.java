/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

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
 * Controller-Klasse zum Hinzufügen einer externen Mannschaft.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung externer Mannschaften.
 * Über ein Eingabefeld kann ein Mannschaftsname erfasst und eine zugehörige
 * {@link Liga} aus einer Auswahlbox gewählt werden. Die Daten werden
 * anschließend über den {@link MannschaftExternService} gespeichert.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren und die vorhandenen Ligen in die Auswahlbox zu
 * laden.
 * </p>
 *
 * @author Marko
 */
public class MannschaftExternHinzufuegenController implements Initializable {

    MannschaftExternDAO dao;
    MannschaftExternService service;

    LigaDAO ligaDao;
    LigaService ligaService;

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<Liga> cbLiga;

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt Instanzen von {@link MannschaftExternService} und
     * {@link LigaService}. Lädt alle vorhandenen {@link Liga}-Objekte und
     * befüllt damit die Auswahlbox. Ist keine Liga vorhanden, wird die
     * Auswahlbox deaktiviert.
     * </p>
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

        if (!liga.isEmpty() && liga != null) {
            cbLiga.setItems(FXCollections.observableArrayList(liga));
        } else {
            cbLiga.setDisable(true);
        }
    }

    /**
     * Speichert die eingegebene Mannschaft.
     * <p>
     * Liest den Namen und die ausgewählte {@link Liga} aus den Eingabefeldern,
     * erstellt ein {@link MannschaftExtern}-Objekt und übergibt es an den
     * {@link MannschaftExternService}.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob eine weitere
     * Mannschaft angelegt werden soll. Bei „Nein“ wird das Fenster geschlossen,
     * bei „Ja“ werden die Eingabefelder geleert.
     * </p>
     * <p>
     * Falls Pflichtfelder fehlen, wird eine Warnung über {@link AlertUtil}
     * angezeigt.
     * </p>
     */
    public void speichern() {
        if (!tfName.getText().isEmpty() && cbLiga.getValue() != null) {
            MannschaftExtern mannschaft = new MannschaftExtern();
            mannschaft.setName(tfName.getText());
            mannschaft.setLiga(cbLiga.getValue());

            service.create(mannschaft);

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Mannschaft erfolgreich gespeichert!", "Möchten Sie eine weitere Mannschaft anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfName);
            } else {
                tfName.clear();
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
