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
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.service.HalleService;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Bearbeiten eines {@link Spiele}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten eines bestehenden Spiels.
 * Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht Änderungen und
 * speichert diese über den {@link SpieleService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service, Ligen-, Hallen- und
 * Mannschaftslisten) vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Auswahl einer {@link Liga}</li>
 * <li>Datum</li>
 * <li>Auswahl einer {@link Halle}</li>
 * <li>Auswahl einer internen und einer externen Mannschaft</li>
 * <li>Punkte für interne und externe Mannschaft (nicht negativ, ganze
 * Zahl)</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class SpielBearbeitenController implements Initializable {

    private Spiele bearbeitenSpiel;

    private SpieleDAO spielDao;
    private SpieleService spielService;

    private LigaDAO ligaDao;
    private LigaService ligaService;

    private HalleDAO halleDao;
    private HalleService halleService;

    private MannschaftInternDAO mannschaftInternDAO;
    private MannschaftInternService mannschaftInternService;

    private MannschaftExternDAO mannschaftExternDAO;
    private MannschaftExternService mannschaftExternService;

    @FXML
    private ComboBox<Liga> cbLiga;

    @FXML
    private DatePicker dpDatum;

    @FXML
    private ComboBox<Halle> cbHalle;

    @FXML
    private ComboBox<MannschaftIntern> cbMannschaftIntern;

    @FXML
    private ComboBox<MannschaftExtern> cbMannschaftExtern;

    @FXML
    private TextField tfPunkteIntern;

    @FXML
    private TextField tfPunkteExtern;

    /**
     * Initialisiert den Controller und lädt die Listen aller Ligen und Hallen.
     * <ul>
     * <li>Erstellt DAO- und Service-Instanzen für Spiele, Ligen, Hallen,
     * interne und externe Mannschaften</li>
     * <li>Lädt die Ligen- und Hallenlisten aus der Datenbank</li>
     * <li>Initial deaktivierte Mannschafts-Auswahlboxen, die erst nach
     * Ligaauswahl aktiviert werden</li>
     * <li>Registriert Listener, um Mannschaftslisten anhand der gewählten Liga
     * zu aktualisieren</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spielDao = new SpieleDAO();
        ligaDao = new LigaDAO();
        halleDao = new HalleDAO();
        mannschaftInternDAO = new MannschaftInternDAO();
        mannschaftExternDAO = new MannschaftExternDAO();

        spielService = new SpieleService(spielDao);
        ligaService = new LigaService(ligaDao);
        halleService = new HalleService(halleDao);
        mannschaftInternService = new MannschaftInternService(mannschaftInternDAO);
        mannschaftExternService = new MannschaftExternService(mannschaftExternDAO);

        List<Liga> liga = ligaService.getAll();
        List<Halle> halle = halleService.getAll();

        cbLiga.setItems(FXCollections.observableArrayList(liga));
        cbMannschaftIntern.setDisable(true);
        cbMannschaftExtern.setDisable(true);

        cbLiga.valueProperty().addListener((obs, ligaAlt, ligaNeu) -> {
            boolean ligaVorhanden = ligaNeu != null;
            cbMannschaftIntern.setDisable(!ligaVorhanden);
            cbMannschaftExtern.setDisable(!ligaVorhanden);

            if (ligaNeu != null) {
                List<MannschaftIntern> mannschaftIntern = mannschaftInternService.getByLiga(ligaNeu);
                List<MannschaftExtern> mannschaftExtern = mannschaftExternService.getByLiga(ligaNeu);

                cbMannschaftIntern.setItems(FXCollections.observableArrayList(mannschaftIntern));
                cbMannschaftExtern.setItems(FXCollections.observableArrayList(mannschaftExtern));
            } else {
                cbMannschaftIntern.getItems().clear();
                cbMannschaftExtern.getItems().clear();
            }
        });

        cbHalle.setItems(FXCollections.observableArrayList(halle));
    }

    /**
     * Lädt die Daten des zu bearbeitenden {@link Spiele} in die Eingabefelder.
     *
     * @param spiel das zu bearbeitende {@link Spiele}
     */
    public void initSpiel(Spiele spiel) {
        this.bearbeitenSpiel = spiel;

        cbLiga.setValue(spiel.getLiga());
        dpDatum.setValue(spiel.getDatum());
        cbHalle.setValue(spiel.getHalle());
        cbMannschaftIntern.setValue(spiel.getMannschaftIntern());
        cbMannschaftExtern.setValue(spiel.getMannschaftExtern());
        tfPunkteIntern.setText(String.valueOf(spiel.getInternPunkte()));
        tfPunkteExtern.setText(String.valueOf(spiel.getExternPunkte()));
    }

    /**
     * Speichert die Änderungen am Spiel.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Parst die Punktewerte für interne und externe Mannschaft als ganze
     * Zahlen</li>
     * <li>Stellt sicher, dass keine negativen Punktwerte eingegeben werden</li>
     * <li>Aktualisiert das {@link Spiele}-Objekt mit den neuen Werten</li>
     * <li>Speichert die Änderungen über den {@link SpieleService}</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * Wenn Punktwerte ungültig oder Pflichtfelder leer sind, wird ein
     * Warnhinweis angezeigt.
     * </p>
     */
    public void aktualisieren() {
        String eingabeIntern = tfPunkteIntern.getText().trim();
        String eingabeExtern = tfPunkteExtern.getText().trim();

        if (cbLiga.getValue() != null
                && cbHalle.getValue() != null
                && cbMannschaftIntern.getValue() != null
                && cbMannschaftExtern.getValue() != null
                && !eingabeIntern.isEmpty()
                && !eingabeExtern.isEmpty()) {

            try {
                int punkteIntern = Integer.parseInt(eingabeIntern);
                int punkteExtern = Integer.parseInt(eingabeExtern);

                if (punkteIntern < 0 || punkteExtern < 0) {
                    throw new NumberFormatException();
                }

                bearbeitenSpiel.setLiga(cbLiga.getValue());
                bearbeitenSpiel.setDatum(dpDatum.getValue());
                bearbeitenSpiel.setHalle(cbHalle.getValue());
                bearbeitenSpiel.setMannschaftIntern(cbMannschaftIntern.getValue());
                bearbeitenSpiel.setMannschaftExtern(cbMannschaftExtern.getValue());
                bearbeitenSpiel.setInternPunkte(punkteIntern);
                bearbeitenSpiel.setExternPunkte(punkteExtern);

                spielService.update(bearbeitenSpiel);

                AlertUtil.alertConfirmation("Speichern erfolgreich", "Spiel erfolgreich aktualisiert!");
                MenuUtil.fensterSchliessenOhneWarnung(cbLiga);

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Fehler", "Nur ganze Zahlen erlaubt", "Bitte geben Sie gültige Punktzahlen ein.");
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    /**
     * Öffnet ein Fenster zur Bearbeitung der Statistik für das aktuelle Spiel
     * und übergibt das Spiel an den {@link StatistikBearbeitenController}.
     * <p>
     * Schließt danach das aktuelle Fenster ohne weitere Warnung.
     * </p>
     */
    @FXML
    private void statistikAnpassen() {
        StatistikBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/statistik/statistikbearbeiten.fxml", "Statistik bearbeiten");

        if (controller != null) {
            controller.setSpiel(bearbeitenSpiel);
        }

        MenuUtil.fensterSchliessenOhneWarnung(cbLiga);
    }

    /**
     * Bricht den Bearbeitungsvorgang ab und schließt das Fenster.
     * <p>
     * Vor dem Schließen wird der Benutzer um eine Bestätigung gebeten.
     * </p>
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(cbLiga);
    }
}
