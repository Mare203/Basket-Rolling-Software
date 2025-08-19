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
 * Controller-Klasse zum Hinzufügen eines Trainings.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Trainingseinheiten.
 * Erfasst werden können Wochentag, Dauer (in Minuten), Halle und Mannschaft.
 * Die Daten werden über den {@link TrainingService} gespeichert.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren und die Auswahlfelder mit den vorhandenen Werten
 * ({@link Halle}, {@link MannschaftIntern}, {@link Wochentag}) zu befüllen.
 * </p>
 *
 * @author Marko
 */
public class TrainingHinzufuegenController implements Initializable {

    TrainingDAO trainingDao;
    TrainingService trainingService;

    HalleDAO halleDao;
    HalleService halleService;

    MannschaftInternDAO mannschaftInternDAO;
    MannschaftInternService mannschaftInternService;

    @FXML
    private ComboBox<Wochentag> cbWochentag;

    @FXML
    private ComboBox<Halle> cbHalle;

    @FXML
    private ComboBox<MannschaftIntern> cbMannschaft;

    @FXML
    private TextField tfDauer;

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt die benötigten Service- und DAO-Instanzen, lädt alle vorhandenen
     * {@link Halle}- und {@link MannschaftIntern}-Objekte und befüllt die
     * Auswahlboxen. Zusätzlich wird die Enum-Liste der {@link Wochentag}-Werte
     * in die entsprechende Auswahlbox gesetzt.
     * </p>
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
     * Speichert das eingegebene Training.
     * <p>
     * Validiert die Pflichtfelder (Wochentag, Halle, Mannschaft, Dauer) und
     * prüft, ob die Dauer eine ganze Zahl größer als 0 ist. Erstellt
     * anschließend ein {@link Training}-Objekt und übergibt es an den
     * {@link TrainingService}.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob ein weiteres
     * Training angelegt werden soll. Bei „Nein“ wird das Fenster geschlossen,
     * bei „Ja“ werden die Eingabefelder geleert.
     * </p>
     * <p>
     * Falls Eingaben fehlen oder ungültig sind, wird eine Warnung über
     * {@link AlertUtil} angezeigt.
     * </p>
     */
    public void speichern() {
        String dauerText = tfDauer.getText().trim();

        if (cbWochentag.getValue() != null
                && cbHalle.getValue() != null
                && cbMannschaft.getValue() != null
                && !dauerText.isEmpty()) {

            try {
                int dauer = Integer.parseInt(dauerText);

                if (dauer <= 0 || dauer > 150) {
                    throw new NumberFormatException();
                }

                Training training = new Training();
                training.setWochentag(cbWochentag.getValue());
                training.setDauerInMin(dauer);
                training.setHalle(cbHalle.getValue());
                training.setMannschaftIntern(cbMannschaft.getValue());

                trainingService.create(training);

                boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Training erfolgreich gespeichert!", "Möchten Sie ein weiteres Training anlegen?");

                if (!weiter) {
                    MenuUtil.fensterSchliessenOhneWarnung(tfDauer);
                } else {
                    cbMannschaft.setValue(null);
                    cbHalle.setValue(null);
                    cbWochentag.setValue(null);
                    tfDauer.clear();
                }

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige Eingabe", "Die Dauer muss eine ganze Zahl größer als 0 sein.", "Beispiel: 60");
            }

        } else {
            AlertUtil.alertWarning(
                    "Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
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
