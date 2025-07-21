/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.dao.MitgliedsbeitragZuweisungDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.service.MitgliedsbeitragZuweisungService;
import org.basketrolling.service.SpielerService;

/**
 *
 * @author Marko
 */
public class SpielerBearbeitenController implements Initializable {

    Spieler bearbeitenSpieler;

    SpielerDAO spielerDao;
    SpielerService spielerService;

    MitgliedsbeitragZuweisungDAO zuweisungDao;
    MitgliedsbeitragZuweisungService zuweisungService;

    MannschaftInternDAO mannschaftDao;
    MannschaftInternService mannschaftService;

    MitgliedsbeitragDAO mitgliedsbeitragDAO;
    MitgliedsbeitragService mitgliedsbeitragService;

    MitgliedsbeitragZuweisung zuweisung;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private DatePicker dpGeburtsdatum;

    @FXML
    private TextField tfGroesse;

    @FXML
    private TextField tfEmail;

    @FXML
    private CheckBox cbAktiv;

    @FXML
    private ComboBox<MannschaftIntern> cbMannschaft;

    @FXML
    private ComboBox<Mitgliedsbeitrag> cbMitgliedsbeitrag;

    @FXML
    private CheckBox cbBezahlt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spielerDao = new SpielerDAO();
        zuweisungDao = new MitgliedsbeitragZuweisungDAO();
        mannschaftDao = new MannschaftInternDAO();
        mitgliedsbeitragDAO = new MitgliedsbeitragDAO();

        spielerService = new SpielerService(spielerDao);
        zuweisungService = new MitgliedsbeitragZuweisungService(zuweisungDao);
        mannschaftService = new MannschaftInternService(mannschaftDao);
        mitgliedsbeitragService = new MitgliedsbeitragService(mitgliedsbeitragDAO);

        List<MannschaftIntern> mannschaften = mannschaftService.getAll();
        List<Mitgliedsbeitrag> mitgliedsbeitraege = mitgliedsbeitragService.getAll();

        cbMannschaft.setItems(FXCollections.observableArrayList(mannschaften));
        cbMitgliedsbeitrag.setItems(FXCollections.observableArrayList(mitgliedsbeitraege));

    }

    public void initSpieler(Spieler spieler) {
        this.bearbeitenSpieler = spieler;

        tfVorname.setText(spieler.getVorname());
        tfNachname.setText(spieler.getNachname());
        dpGeburtsdatum.setValue(spieler.getGeburtsdatum());
        tfGroesse.setText(String.valueOf(spieler.getGroesse()));
        tfEmail.setText(spieler.geteMail());
        cbAktiv.setSelected(spieler.isAktiv());
        cbMannschaft.setValue(spieler.getMannschaftIntern());

        List<MitgliedsbeitragZuweisung> zuweisungen = zuweisungService.getAktiveBySpieler(spieler);
        if (!zuweisungen.isEmpty()) {
            zuweisung = zuweisungen.get(0);
            cbMitgliedsbeitrag.setValue(zuweisung.getMitgliedsbeitrag());
            cbBezahlt.setSelected(zuweisung.isBezahlt());
        }
    }

    public void aktualisieren() {
        boolean pflichtfelderAusgefuellt
                = !tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && dpGeburtsdatum.getValue() != null
                && !tfGroesse.getText().isEmpty()
                && cbMannschaft.getValue() != null;

        boolean beitragsPflichtErfuellt = !cbAktiv.isSelected() || cbMitgliedsbeitrag.getValue() != null;

        if (pflichtfelderAusgefuellt && beitragsPflichtErfuellt) {
            
            bearbeitenSpieler.setVorname(tfVorname.getText());
            bearbeitenSpieler.setNachname(tfNachname.getText());
            bearbeitenSpieler.setGeburtsdatum(dpGeburtsdatum.getValue());
            bearbeitenSpieler.setGroesse(Double.parseDouble(tfGroesse.getText()));
            bearbeitenSpieler.seteMail(tfEmail.getText());
            bearbeitenSpieler.setAktiv(cbAktiv.isSelected());
            bearbeitenSpieler.setMannschaftIntern(cbMannschaft.getValue());

            Spieler gespeicherterSpieler = spielerService.update(bearbeitenSpieler);

            
            if (cbAktiv.isSelected()) {
                Mitgliedsbeitrag beitrag = cbMitgliedsbeitrag.getValue();
                boolean bezahlt = cbBezahlt.isSelected();

                zuweisungService.aktivereNeueZuweisung(gespeicherterSpieler, beitrag, bezahlt);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Spieler wurde erfolgreich bearbeitet und gespeichert!");

            Stage stage = (Stage) tfVorname.getScene().getWindow();
            stage.close();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eingabefehler");
            alert.setHeaderText("Unvollständige oder ungültige Eingaben");
            String text = "- Alle Pflichtfelder (inkl. Mannschaft) müssen ausgefüllt sein.";
            if (cbAktiv.isSelected() && cbMitgliedsbeitrag.getValue() == null) {
                text += "\n- Ein aktiver Spieler muss einen Mitgliedsbeitrag haben.";
            }
            alert.setContentText(text);
            alert.showAndWait();
        }
    }
}
