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
public class TrainingHinzufuegenController implements Initializable {

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

    public void speichern() {
        if (dpDatum.getValue() != null
                && cbHalle.getValue() != null
                && cbMannschaft.getValue() != null
                && !tfDauer.getText().isEmpty()) {

            Training training = new Training();
            training.setDatum(dpDatum.getValue());
            training.setDauerInMin(Integer.parseInt(tfDauer.getText()));
            training.setHalle(cbHalle.getValue());
            training.setMannschaftIntern(cbMannschaft.getValue());
            training.setWochentag(dpDatum.getValue().getDayOfWeek().toString());

            trainingService.create(training);

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Training erfolgreich gespeichert!", "Möchten Sie ein weiteres Training anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfDauer);
            } else {
                cbMannschaft.setValue(null);
                cbHalle.setValue(null);
                dpDatum.setValue(null);
                tfDauer.clear();
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfDauer);
    }
}
