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
 *
 * @author Marko
 */
public class TrainingBearbeitenController implements Initializable {

    Training bearbeitenTraining;

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

    public void initTraining(Training training) {
        this.bearbeitenTraining = training;

        cbWochentag.setValue(training.getWochentag());
        cbHalle.setValue(training.getHalle());
        cbMannschaft.setValue(training.getMannschaftIntern());
        tfDauer.setText(String.valueOf(training.getDauerInMin()));
    }

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

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfDauer);
    }
}
