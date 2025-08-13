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
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.enums.Wochentag;
import org.basketrolling.service.HalleService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Bearbeiten eines {@link Training}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten einer bestehenden
 * Trainingseinheit. Sie lädt die aktuellen Daten in die Eingabefelder,
 * ermöglicht Änderungen und speichert diese über den {@link TrainingService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service, Hallen- und Mannschaftslisten,
 * Wochentage) vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Wochentag</li>
 * <li>Halle</li>
 * <li>Interne Mannschaft</li>
 * <li>Dauer (ganze Zahl > 0)</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class TrainingBearbeitenController implements Initializable {

    private Training bearbeitenTraining;

    private TrainingDAO trainingDao;
    private TrainingService trainingService;

    private HalleDAO halleDao;
    private HalleService halleService;

    private MannschaftInternDAO mannschaftInternDAO;
    private MannschaftInternService mannschaftInternService;

    @FXML
    private ComboBox<Wochentag> cbWochentag;

    @FXML
    private ComboBox<Halle> cbHalle;

    @FXML
    private ComboBox<MannschaftIntern> cbMannschaft;

    @FXML
    private TextField tfDauer;

    /**
     * Initialisiert den Controller und lädt alle verfügbaren Hallen,
     * Mannschaften und Wochentage.
     * <ul>
     * <li>Erstellt DAO- und Service-Instanzen</li>
     * <li>Befüllt die ComboBoxen für Halle, Mannschaft und Wochentag</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trainingDao = new TrainingDAO();
        halleDao = new HalleDAO();
        mannschaftInternDAO = new MannschaftInternDAO();

        trainingService = new TrainingService(trainingDao);
        halleService = new HalleService(halleDao);
        mannschaftInternService = new MannschaftInternService(mannschaftInternDAO);

        List<Halle> halle = halleService.getAll();
        List<MannschaftIntern> mannschaft = mannschaftInternService.getAll();

        cbHalle.setItems(FXCollections.observableArrayList(halle));
        cbMannschaft.setItems(FXCollections.observableArrayList(mannschaft));
        cbWochentag.setItems(FXCollections.observableArrayList(Wochentag.values()));
    }

    /**
     * Lädt die Daten des zu bearbeitenden {@link Training} in die
     * Eingabefelder.
     *
     * @param training die zu bearbeitende {@link Training}-Einheit
     */
    public void initTraining(Training training) {
        this.bearbeitenTraining = training;

        cbWochentag.setValue(training.getWochentag());
        cbHalle.setValue(training.getHalle());
        cbMannschaft.setValue(training.getMannschaftIntern());
        tfDauer.setText(String.valueOf(training.getDauerInMin()));
    }

    /**
     * Speichert die Änderungen am Training.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Parst die Dauer als ganze Zahl</li>
     * <li>Stellt sicher, dass die Dauer größer als 0 ist</li>
     * <li>Aktualisiert das {@link Training}-Objekt mit den neuen Werten</li>
     * <li>Speichert die Änderungen über den {@link TrainingService}</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * Wenn die Dauer ungültig ist oder Pflichtfelder fehlen, wird ein
     * Warnhinweis angezeigt.
     * </p>
     */
    public void aktualisieren() {
        String dauerText = tfDauer.getText().trim();

        if (cbWochentag.getValue() != null
                && cbHalle.getValue() != null
                && cbMannschaft.getValue() != null
                && !dauerText.isEmpty()) {

            try {
                int dauer = Integer.parseInt(dauerText);
                if (dauer <= 0) {
                    throw new NumberFormatException();
                }

                bearbeitenTraining.setWochentag(cbWochentag.getValue());
                bearbeitenTraining.setDauerInMin(dauer);
                bearbeitenTraining.setHalle(cbHalle.getValue());
                bearbeitenTraining.setMannschaftIntern(cbMannschaft.getValue());

                trainingService.update(bearbeitenTraining);

                AlertUtil.alertConfirmation("Speichern erfolgreich", "Training erfolgreich aktualisiert!");
                MenuUtil.fensterSchliessenOhneWarnung(tfDauer);

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige Eingabe", "Die Dauer muss eine ganze Zahl größer als 0 sein.", "Beispiel: 60");
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
        MenuUtil.fensterSchliessenMitWarnung(tfDauer);
    }
}
