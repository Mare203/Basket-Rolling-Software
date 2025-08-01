/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
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
 *
 * @author Marko
 */
public class SpielHinzufuegenController implements Initializable {

    SpieleDAO spielDao;
    SpieleService spielService;

    LigaDAO ligaDao;
    LigaService ligaService;

    HalleDAO halleDao;
    HalleService halleService;

    MannschaftInternDAO mannschaftInternDAO;
    MannschaftInternService mannschaftInternService;

    MannschaftExternDAO mannschaftExternDAO;
    MannschaftExternService mannschaftExternService;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        dpDatum.setConverter(new StringConverter<>() {
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

    public void speichern() {
        String eingabeIntern = tfPunkteIntern.getText().trim();
        String eingabeExtern = tfPunkteExtern.getText().trim();

        LocalDate datum = dpDatum.getValue();

        if (datum == null) {
            AlertUtil.alertError("Fehler", "Geben Sie ein gültiges Datum ein!");
            return;
        }

        if (datum.isAfter(LocalDate.now())) {
            AlertUtil.alertWarning("Ungültiges Datum", "Datum liegt in der Zukunft", "Bitte geben Sie das heutige oder ein vergangenes Datum ein.");
            return;
        }

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

                Spiele spiel = new Spiele();
                spiel.setLiga(cbLiga.getValue());
                spiel.setDatum(dpDatum.getValue());
                spiel.setHalle(cbHalle.getValue());
                spiel.setMannschaftIntern(cbMannschaftIntern.getValue());
                spiel.setMannschaftExtern(cbMannschaftExtern.getValue());
                spiel.setInternPunkte(punkteIntern);
                spiel.setExternPunkte(punkteExtern);

                Spiele erstelltesSpiel = spielService.create(spiel);

                boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Spiel erfolgreich gespeichert!", "Möchten Sie eine Statistik für dieses Spiel anlegen?");

                if (!weiter) {
                    MenuUtil.fensterSchliessenOhneWarnung(cbLiga);
                } else {
                    oeffneStatistik(erstelltesSpiel);
                }

            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige Eingabe", "Nur ganze Zahlen erlaubt", "Bitte geben Sie gültige Punktzahlen ein.");
            }

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    private void oeffneStatistik(Spiele spiel) {
        StatistikHinzufuegenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/statistik/statistikhinzufuegen.fxml", "Spielerstatistik erfassen");

        if (controller != null) {
            controller.setSpiel(spiel);
        }
        MenuUtil.fensterSchliessenOhneWarnung(cbLiga);
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(cbLiga);
    }
}
