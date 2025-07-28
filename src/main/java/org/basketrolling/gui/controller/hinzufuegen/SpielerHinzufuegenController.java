/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
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
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class SpielerHinzufuegenController implements Initializable {

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

        if (mannschaften != null && !mannschaften.isEmpty()) {
            cbMannschaft.setItems(FXCollections.observableArrayList(mannschaften));
        } else {
            cbMannschaft.setDisable(true);
        }
        List<Mitgliedsbeitrag> mitgliedsbeitraege = mitgliedsbeitragService.getAll();

        if (mitgliedsbeitraege != null && !mitgliedsbeitraege.isEmpty()) {
            cbMitgliedsbeitrag.setItems(FXCollections.observableArrayList(mitgliedsbeitraege));
        } else {
            cbMitgliedsbeitrag.setDisable(true);
        }
    }

    public void speichern() {
        boolean pflichtfelderAusgefuellt
                = !tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && dpGeburtsdatum.getValue() != null
                && !tfGroesse.getText().isEmpty()
                && cbMannschaft.getValue() != null;

        boolean beitragsPflichtErfuellt = !cbAktiv.isSelected() || cbMitgliedsbeitrag.getValue() != null;

        if (pflichtfelderAusgefuellt && beitragsPflichtErfuellt) {
            try {

                Spieler spieler = new Spieler();
                spieler.setVorname(tfVorname.getText());
                spieler.setNachname(tfNachname.getText());
                LocalDate geburtsdatum = dpGeburtsdatum.getValue();
                LocalDate heute = LocalDate.now();

                int alter = Period.between(geburtsdatum, heute).getYears();

                if (alter < 5) {
                    AlertUtil.alertWarning("Ungültiges Geburtsdatum", "Spieler ist zu jung", "Ein Spieler muss mindestens 5 Jahre alt sein.");
                    return;
                }
                spieler.setGeburtsdatum(geburtsdatum);
                String groesseEingabe = tfGroesse.getText();

                if (!groesseEingabe.matches("^\\d+\\.\\d{2}$")) {
                    AlertUtil.alertWarning("Ungültige Eingabe", "Ungültiges Zahlenformat im Feld 'Größe'", "Bitte geben Sie eine gültige Zahl ein (z. B. 1.80).");
                    return;
                }
                spieler.setGroesse(Double.parseDouble(groesseEingabe));
                spieler.seteMail(tfEmail.getText());
                spieler.setAktiv(cbAktiv.isSelected());
                spieler.setMannschaftIntern(cbMannschaft.getValue());

                Spieler gespeicherterSpieler = spielerService.create(spieler);

                if (cbAktiv.isSelected()) {
                    Mitgliedsbeitrag beitrag = cbMitgliedsbeitrag.getValue();
                    boolean bezahlt = cbBezahlt.isSelected();

                    MitgliedsbeitragZuweisung zuweisung = new MitgliedsbeitragZuweisung();
                    zuweisung.setSpieler(gespeicherterSpieler);
                    zuweisung.setMitgliedsbeitrag(beitrag);
                    zuweisung.setBezahlt(bezahlt);

                    zuweisungService.create(zuweisung);
                }
            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige Eingabe", "Ungültiges Zahlenformat im Feld 'Größe'", "Bitte geben Sie eine gültige Zahl ein (z. B. 1.80).");
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Spieler erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie einen weiteren Spieler anlegen?");

            ButtonType jaButton = new ButtonType("Ja");
            ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(jaButton, neinButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == neinButton) {
                Stage stage = (Stage) tfVorname.getScene().getWindow();
                stage.close();
            } else {
                tfVorname.clear();
                tfNachname.clear();
                tfGroesse.clear();
                tfEmail.clear();
                dpGeburtsdatum.setValue(null);
                cbAktiv.setSelected(false);
                cbBezahlt.setSelected(false);
                cbMannschaft.setValue(null);
                cbMitgliedsbeitrag.setValue(null);
            }

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
