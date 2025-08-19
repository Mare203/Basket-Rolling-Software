/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
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
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse zum Hinzufügen eines Spielers.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Spielern. Erfasst
 * werden können Vorname, Nachname, Geburtsdatum, Größe, E-Mail, Mannschaft,
 * Aktiv-Status sowie (falls aktiv) der zugewiesene {@link Mitgliedsbeitrag}.
 * </p>
 * <p>
 * Über den {@link SpielerService} wird der Spieler gespeichert und – falls
 * aktiv – zusätzlich eine {@link MitgliedsbeitragZuweisung} über den
 * {@link MitgliedsbeitragZuweisungService} erstellt.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren, die Auswahlboxen mit vorhandenen Daten zu
 * befüllen und den {@link DatePicker} mit einem benutzerdefinierten
 * Datumsformat zu versehen.
 * </p>
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

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt Instanzen der benötigten Services, befüllt die Auswahlboxen für
     * {@link MannschaftIntern} und {@link Mitgliedsbeitrag}, deaktiviert sie
     * bei fehlenden Daten und konfiguriert den {@link DatePicker} mit dem
     * Datumsformat <code>dd.MM.yyyy</code>. Zusätzlich wird ein Listener
     * gesetzt, um die Mitgliedsbeitragsfelder zu leeren, wenn der Aktiv-Status
     * deaktiviert wird.
     * </p>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
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

        cbAktiv.selectedProperty().addListener((obs, alt, neu) -> {
            if (!neu) {
                cbMitgliedsbeitrag.setValue(null);
                cbBezahlt.setSelected(false);
            }
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        dpGeburtsdatum.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String text) {
                if (text == null || text.trim().isEmpty()) {
                    return null;
                }
                try {
                    return LocalDate.parse(text, formatter);
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });
    }

    /**
     * Speichert den eingegebenen Spieler.
     * <p>
     * Validiert die Pflichtfelder, überprüft das Geburtsdatum (Spieler ≥ 5
     * Jahre alt) sowie die Größe (positive Zahl). Anschließend wird ein
     * {@link Spieler}-Objekt erstellt und über den {@link SpielerService}
     * gespeichert.
     * </p>
     * <p>
     * Falls der Spieler aktiv ist, wird zusätzlich eine
     * {@link MitgliedsbeitragZuweisung} erstellt, die den Spieler mit einem
     * {@link Mitgliedsbeitrag} und dem Zahlungsstatus verknüpft.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob ein weiterer
     * Spieler angelegt werden soll. Bei „Nein“ wird das Fenster geschlossen,
     * bei „Ja“ werden die Eingabefelder geleert.
     * </p>
     * <p>
     * Falls Eingaben fehlen oder ungültig sind, wird eine Warnung über
     * {@link AlertUtil} angezeigt.
     * </p>
     */
    public void speichern() {
        LocalDate geburtsdatum = dpGeburtsdatum.getValue();

        String email = tfEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            AlertUtil.alertWarning("Ungültige Eingabe", "Ungültige E-Mail-Adresse", "Bitte geben Sie eine gültige E-Mail-Adresse ein (z. B. name@domain.com).");
            return;
        }

        if (geburtsdatum == null) {
            AlertUtil.alertError("Fehler", "Geben Sie ein gültiges Datum ein!");
            return;
        }

        boolean pflichtfelderAusgefuellt
                = !tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && dpGeburtsdatum.getValue() != null
                && !tfGroesse.getText().isEmpty()
                && cbMannschaft.getValue() != null;

        boolean beitragsPflichtErfuellt = !cbAktiv.isSelected() || cbMitgliedsbeitrag.getValue() != null;

        if (pflichtfelderAusgefuellt && beitragsPflichtErfuellt) {
            if (!cbAktiv.isSelected() && cbMitgliedsbeitrag.getValue() != null) {
                AlertUtil.alertWarning("Mitgliedsbeitrag wird ignoriert", "Spieler ist nicht aktiv", "Ein Mitgliedsbeitrag wird nur aktiven Spielern zugewiesen. Bitte aktiviere den Spieler, wenn du einen Beitrag zuweisen willst.");
                return;
            }

            try {
                Spieler spieler = new Spieler();
                spieler.setVorname(tfVorname.getText());
                spieler.setNachname(tfNachname.getText());

                LocalDate heute = LocalDate.now();
                int alter = Period.between(geburtsdatum, heute).getYears();

                if (alter < 5) {
                    AlertUtil.alertWarning("Ungültiges Geburtsdatum", "Spieler ist zu jung", "Ein Spieler muss mindestens 5 Jahre alt sein.");
                    return;
                }
                spieler.setGeburtsdatum(geburtsdatum);
                String groesseText = tfGroesse.getText().trim();
                try {
                    double groesse = Double.parseDouble(groesseText.replace(",", "."));
                    if (groesse <= 0) {
                        throw new NumberFormatException();
                    }

                    if (groesse > 3) {
                        groesse = groesse / 100.0;
                    }
                    spieler.setGroesse(groesse);
                } catch (NumberFormatException e) {
                    AlertUtil.alertWarning("Ungültige Eingabe", "Ungültiges Zahlenformat im Feld 'Größe'", "Bitte geben Sie eine gültige Zahl ein (z. B. 1.80, 1,80, 180).");
                    return;
                }
                spieler.seteMail(email);
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

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Spieler erfolgreich gespeichert!", "Möchten Sie einen weiteren Spieler anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfVorname);
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

    /**
     * Bricht den Bearbeitungsvorgang ab und schließt das Fenster.
     * <p>
     * Vor dem Schließen wird der Benutzer um eine Bestätigung gebeten.
     * </p>
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfVorname);
    }
}
