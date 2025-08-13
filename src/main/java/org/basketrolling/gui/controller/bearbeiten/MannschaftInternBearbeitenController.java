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
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.TrainerService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Bearbeiten einer {@link MannschaftIntern}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten einer bestehenden internen
 * Mannschaft. Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht
 * Änderungen und speichert diese über den {@link MannschaftInternService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service, Ligen- und Trainerliste)
 * vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Name der Mannschaft</li>
 * <li>Auswahl einer {@link Liga}</li>
 * </ul>
 * Optional:
 * <ul>
 * <li>Auswahl eines {@link Trainer}</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class MannschaftInternBearbeitenController implements Initializable {

    private MannschaftIntern bearbeitenMannschaft;

    private MannschaftInternDAO dao;
    private MannschaftInternService service;

    private LigaDAO ligaDao;
    private LigaService ligaService;

    private TrainerDAO trainerDao;
    private TrainerService trainerService;

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<Liga> cbLiga;

    @FXML
    private ComboBox<Trainer> cbTrainer;

    /**
     * Initialisiert den Controller und lädt die Listen aller Ligen und Trainer.
     * <ul>
     * <li>Erstellt DAO- und Service-Instanzen für Mannschaften, Ligen und
     * Trainer</li>
     * <li>Lädt die vorhandenen Ligen aus der Datenbank</li>
     * <li>Befüllt die Liga-Auswahl-{@link ComboBox}</li>
     * <li>Lädt die vorhandenen Trainer aus der Datenbank</li>
     * <li>Befüllt die Trainer-Auswahl-{@link ComboBox}</li>
     * <li>Deaktiviert die jeweilige {@link ComboBox}, falls keine Daten
     * vorhanden sind</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new MannschaftInternDAO();
        service = new MannschaftInternService(dao);

        ligaDao = new LigaDAO();
        ligaService = new LigaService(ligaDao);

        trainerDao = new TrainerDAO();
        trainerService = new TrainerService(trainerDao);

        List<Liga> liga = ligaService.getAll();
        if (liga != null && !liga.isEmpty()) {
            cbLiga.setItems(FXCollections.observableArrayList(liga));
        } else {
            cbLiga.setDisable(true);
        }

        List<Trainer> trainer = trainerService.getAll();
        if (trainer != null && !trainer.isEmpty()) {
            cbTrainer.setItems(FXCollections.observableArrayList(trainer));
        } else {
            cbTrainer.setDisable(true);
        }
    }

    /**
     * Lädt die Daten der zu bearbeitenden {@link MannschaftIntern} in die
     * Eingabefelder.
     *
     * @param mannschaftIntern die zu bearbeitende {@link MannschaftIntern}
     */
    public void initMannschaftIntern(MannschaftIntern mannschaftIntern) {
        this.bearbeitenMannschaft = mannschaftIntern;

        tfName.setText(mannschaftIntern.getName());
        cbLiga.setValue(mannschaftIntern.getLiga());
        cbTrainer.setValue(mannschaftIntern.getTrainer());
    }

    /**
     * Speichert die Änderungen an der internen Mannschaft.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Aktualisiert das {@link MannschaftIntern}-Objekt mit den neuen
     * Werten</li>
     * <li>Speichert die Änderungen über den
     * {@link MannschaftInternService}</li>
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
            bearbeitenMannschaft.setTrainer(cbTrainer.getValue());

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
