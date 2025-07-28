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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.TrainingDAO;
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
    private DatePicker dpDatum;

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
    }

    public void initTraining(Training training) {
        this.bearbeitenTraining = training;

        dpDatum.setValue(training.getDatum());
        cbHalle.setValue(training.getHalle());
        cbMannschaft.setValue(training.getMannschaftIntern());
        tfDauer.setText(String.valueOf(training.getDauerInMin()));
    }

    public void aktualisieren() {
        if (dpDatum.getValue() != null
                && cbHalle.getValue() != null
                && cbMannschaft.getValue() != null
                && !tfDauer.getText().isEmpty()) {

            bearbeitenTraining.setDatum(dpDatum.getValue());
            bearbeitenTraining.setDauerInMin(Integer.parseInt(tfDauer.getText()));
            bearbeitenTraining.setHalle(cbHalle.getValue());
            bearbeitenTraining.setMannschaftIntern(cbMannschaft.getValue());
            bearbeitenTraining.setWochentag(dpDatum.getValue().getDayOfWeek().toString());

            trainingService.update(bearbeitenTraining);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Training erfolgreich aktualisiert!");
            MenuUtil.fensterSchliessenOhneWarning(tfDauer);

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarning(tfDauer);
    }
}
