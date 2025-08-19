/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
 * Controller-Klasse für das Bearbeiten eines {@link Spieler}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten eines bestehenden
 * Spielers. Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht
 * Änderungen und speichert diese über den {@link SpielerService}.
 * </p>
 * <p>
 * Zusätzlich verwaltet sie die Zuweisung eines {@link Mitgliedsbeitrag} an den
 * Spieler (inklusive Zahlungsstatus) über den
 * {@link MitgliedsbeitragZuweisungService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service, Mannschafts- und Beitragslisten)
 * vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Vorname</li>
 * <li>Nachname</li>
 * <li>Geburtsdatum</li>
 * <li>Größe im Format z. B. 1.80</li>
 * <li>Zugehörigkeit zu einer {@link MannschaftIntern}</li>
 * </ul>
 * Zusatzregeln:
 * <ul>
 * <li>Aktive Spieler müssen einen Mitgliedsbeitrag zugewiesen haben</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class SpielerBearbeitenController implements Initializable {

    private Spieler bearbeitenSpieler;

    private SpielerDAO spielerDao;
    private SpielerService spielerService;

    private MitgliedsbeitragZuweisungDAO zuweisungDao;
    private MitgliedsbeitragZuweisungService zuweisungService;

    private MannschaftInternDAO mannschaftDao;
    private MannschaftInternService mannschaftService;

    private MitgliedsbeitragDAO mitgliedsbeitragDAO;
    private MitgliedsbeitragService mitgliedsbeitragService;

    private MitgliedsbeitragZuweisung zuweisung;

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
     * Initialisiert den Controller und lädt die Listen aller Mannschaften und
     * Mitgliedsbeiträge.
     * <ul>
     * <li>Erstellt DAO- und Service-Instanzen</li>
     * <li>Befüllt die {@link ComboBox} für Mannschaften und
     * Mitgliedsbeiträge</li>
     * <li>Deaktiviert die Beitragsauswahl automatisch, wenn der Spieler auf
     * inaktiv gesetzt wird</li>
     * </ul>
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
        List<Mitgliedsbeitrag> mitgliedsbeitraege = mitgliedsbeitragService.getAll();

        cbMannschaft.setItems(FXCollections.observableArrayList(mannschaften));
        cbMitgliedsbeitrag.setItems(FXCollections.observableArrayList(mitgliedsbeitraege));

        cbAktiv.selectedProperty().addListener((obs, alt, neu) -> {
            if (!neu) {
                cbMitgliedsbeitrag.setValue(null);
                cbBezahlt.setSelected(false);
            }
        });
    }

    /**
     * Lädt die Daten des zu bearbeitenden {@link Spieler} in die Eingabefelder.
     * <p>
     * Falls der Spieler einen aktiven Mitgliedsbeitrag zugewiesen hat, werden
     * dieser und der Zahlungsstatus in die Felder übernommen.
     * </p>
     *
     * @param spieler der zu bearbeitende {@link Spieler}
     */
    public void initSpieler(Spieler spieler) {
        this.bearbeitenSpieler = spieler;

        tfVorname.setText(spieler.getVorname());
        tfNachname.setText(spieler.getNachname());
        dpGeburtsdatum.setValue(spieler.getGeburtsdatum());

        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        tfGroesse.setText(df.format(spieler.getGroesse()));

        tfEmail.setText(spieler.geteMail());
        cbAktiv.setSelected(spieler.isAktiv());
        cbMannschaft.setValue(spieler.getMannschaftIntern());

        List<MitgliedsbeitragZuweisung> zuweisungen = zuweisungService.getAktiveBySpieler(spieler);
        if (!zuweisungen.isEmpty()) {
            zuweisung = zuweisungen.get(0);

            Mitgliedsbeitrag beitragVonZuweisung = zuweisung.getMitgliedsbeitrag();
            for (Mitgliedsbeitrag beitrag : cbMitgliedsbeitrag.getItems()) {
                if (beitrag.getMitgliedsbeitragId().equals(beitragVonZuweisung.getMitgliedsbeitragId())) {
                    cbMitgliedsbeitrag.setValue(beitrag);
                    break;
                }
            }
            cbBezahlt.setSelected(zuweisung.isBezahlt());
        }
    }

    /**
     * Speichert die Änderungen am Spieler.
     * <p>
     * Führt eine Validierung der Pflichtfelder und der Mitgliedsbeitragsregeln
     * durch. Wenn alle Bedingungen erfüllt sind:
     * <ul>
     * <li>Aktualisiert das {@link Spieler}-Objekt mit den neuen Werten</li>
     * <li>Speichert den Spieler über den {@link SpielerService}</li>
     * <li>Aktualisiert oder erstellt bei aktiven Spielern die
     * Mitgliedsbeitragszuweisung</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * Wenn Pflichtfelder fehlen, das Größenformat ungültig ist oder die
     * Beitragsregeln verletzt werden, wird ein Warnhinweis angezeigt.
     * </p>
     */
    public void aktualisieren() {
        boolean pflichtfelderAusgefuellt
                = !tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && dpGeburtsdatum.getValue() != null
                && !tfGroesse.getText().isEmpty()
                && cbMannschaft.getValue() != null;

        String email = tfEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            AlertUtil.alertWarning("Ungültige Eingabe", "Ungültige E-Mail-Adresse", "Bitte geben Sie eine gültige E-Mail-Adresse ein (z. B. name@domain.com).");
            return;
        }

        boolean beitragsPflichtErfuellt = !cbAktiv.isSelected() || cbMitgliedsbeitrag.getValue() != null || zuweisung != null;

        if (pflichtfelderAusgefuellt && beitragsPflichtErfuellt) {
            if (!cbAktiv.isSelected() && cbMitgliedsbeitrag.getValue() != null) {
                AlertUtil.alertWarning("Mitgliedsbeitrag wird ignoriert", "Spieler ist nicht aktiv",
                        "Ein Mitgliedsbeitrag wird nur aktiven Spielern zugewiesen. Bitte aktiviere den Spieler, wenn du einen Beitrag zuweisen willst.");
                return;
            }
            try {
                bearbeitenSpieler.setVorname(tfVorname.getText());
                bearbeitenSpieler.setNachname(tfNachname.getText());
                bearbeitenSpieler.setGeburtsdatum(dpGeburtsdatum.getValue());
                String groesseText = tfGroesse.getText().trim();
                try {
                    double groesse = Double.parseDouble(groesseText.replace(",", "."));
                    if (groesse <= 0) {
                        throw new NumberFormatException();
                    }

                    if (groesse > 3) {
                        groesse = groesse / 100.0;
                    }
                    bearbeitenSpieler.setGroesse(groesse);
                } catch (NumberFormatException e) {
                    AlertUtil.alertWarning("Ungültige Eingabe", "Ungültiges Zahlenformat im Feld 'Größe'", "Bitte geben Sie eine gültige Zahl ein (z. B. 1.80).");
                    return;
                }

                bearbeitenSpieler.seteMail(email);
                bearbeitenSpieler.setAktiv(cbAktiv.isSelected());
                bearbeitenSpieler.setMannschaftIntern(cbMannschaft.getValue());

                Spieler gespeicherterSpieler = spielerService.update(bearbeitenSpieler);

                if (cbAktiv.isSelected()) {
                    Mitgliedsbeitrag beitrag = cbMitgliedsbeitrag.getValue();
                    boolean bezahlt = cbBezahlt.isSelected();

                    if (zuweisung != null) {
                        zuweisung.setMitgliedsbeitrag(beitrag);
                        zuweisung.setBezahlt(bezahlt);
                        zuweisungService.update(zuweisung);
                    } else {
                        zuweisungService.aktivereNeueZuweisung(gespeicherterSpieler, beitrag, bezahlt);
                    }
                }

                AlertUtil.alertConfirmation("Speichern erfolgreich", "Spieler wurde erfolgreich bearbeitet und gespeichert!");
                MenuUtil.fensterSchliessenOhneWarnung(tfVorname);

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige Eingabe", "Ungültiges Zahlenformat im Feld 'Größe'", "Bitte geben Sie eine gültige Zahl ein (z. B. 1.80).");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eingabefehler");
            alert.setHeaderText("Unvollständige oder ungültige Eingaben");
            String text = "- Alle Pflichtfelder (inkl. Mannschaft) müssen ausgefüllt sein.";
            if (cbAktiv.isSelected() && cbMitgliedsbeitrag.getValue() == null && zuweisung == null) {
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
