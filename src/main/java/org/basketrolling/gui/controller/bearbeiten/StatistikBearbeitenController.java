/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import org.basketrolling.gui.controller.hinzufuegen.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Statistik;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class StatistikBearbeitenController implements Initializable {

    StatistikDAO statistikDAO;
    StatistikService statistikService;

    SpielerService spielerService;
    SpielerDAO spielerDao;

    @FXML
    private Label lbMannschaftIntern;
    @FXML
    private Label lbMannschaftExtern;
    @FXML
    private Label lbDatum;
    @FXML
    private Label lbPunkteIntern;
    @FXML
    private Label lbPunkteExtern;

    @FXML
    private TableView<Statistik> tabelleStatistik;
    @FXML
    private TableColumn<Statistik, String> spalteSpieler;
    @FXML
    private TableColumn<Statistik, Integer> spaltePunkte;
    @FXML
    private TableColumn<Statistik, Integer> spalteFouls;
    @FXML
    private TableColumn<Statistik, Boolean> spalteGespielt;

    private final ObservableList<Statistik> statistikListe = FXCollections.observableArrayList();

    Spiele spiel;

    public void setSpiel(Spiele spiel) {
        this.spiel = spiel;
        statistikListe.clear();

        lbMannschaftIntern.setText(spiel.getMannschaftIntern().getName());
        lbMannschaftExtern.setText(spiel.getMannschaftExtern().getName());
        lbPunkteIntern.setText(String.valueOf(spiel.getInternPunkte()));
        lbPunkteExtern.setText(String.valueOf(spiel.getExternPunkte()));
        lbDatum.setText(String.valueOf(spiel.getDatum()));

        List<Statistik> vorhandeneStatistiken = statistikService.getBySpiel(spiel);
        List<Spieler> spielerListe = spielerService.getByMannschaft(spiel.getMannschaftIntern());

        for (Spieler spieler : spielerListe) {
            Statistik vorhandene = vorhandeneStatistiken.stream()
                    .filter(s -> s.getSpieler().getSpielerId().equals(spieler.getSpielerId()))
                    .findFirst()
                    .orElse(null);

            if (vorhandene != null) {
                statistikListe.add(vorhandene);
            } else {
                Statistik neue = new Statistik();
                neue.setSpiel(spiel);
                neue.setSpieler(spieler);
                neue.setPunkte(0);
                neue.setFouls(0);
                neue.setGespielt(false);
                statistikListe.add(neue);
            }
        }
        tabelleStatistik.setItems(statistikListe);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statistikDAO = new StatistikDAO();
        statistikService = new StatistikService(statistikDAO);

        spielerDao = new SpielerDAO();
        spielerService = new SpielerService(spielerDao);

        spalteSpieler.setCellValueFactory(cell
                -> new SimpleStringProperty(
                        cell.getValue().getSpieler().getVorname() + " " + cell.getValue().getSpieler().getNachname()
                )
        );

        spaltePunkte.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getPunkte()).asObject());
        spaltePunkte.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        spaltePunkte.setOnEditCommit(event -> event.getRowValue().setPunkte(event.getNewValue()));

        spalteFouls.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getFouls()).asObject());
        spalteFouls.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        spalteFouls.setOnEditCommit(event -> event.getRowValue().setFouls(event.getNewValue()));

        spalteGespielt.setCellValueFactory(cellData -> {
            Statistik statistik = cellData.getValue();
            SimpleBooleanProperty property = new SimpleBooleanProperty(statistik.isGespielt());
            property.addListener((obs, oldVal, newVal) -> statistik.setGespielt(newVal));
            return property;
        });
        spalteGespielt.setCellFactory(CheckBoxTableCell.forTableColumn(spalteGespielt));

        tabelleStatistik.setEditable(true);
    }

    public void speichern() {
        int summePunkte = statistikListe.stream()
                .mapToInt(Statistik::getPunkte)
                .sum();

        int erwartetePunkte = spiel.getInternPunkte();

        if (summePunkte != erwartetePunkte) {
            AlertUtil.alertWarning("Fehlerhafte Punktzahl",
                    "Gesamtsumme der Spieler-Punkte stimmt nicht!",
                    "Die Summe der Punkte (" + summePunkte + ") stimmt nicht mit den Team-Punkten (" + erwartetePunkte + ") überein.");
        } else {
            for (Statistik statistik : statistikListe) {
                if (statistik.isGespielt()) {
                    if (statistik.getStatistikId() == null) {
                        statistikService.create(statistik);
                    } else {
                        statistikService.update(statistik);
                    }
                }
            }

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Alle Einträge wurden erfolgreich gespeichert.");
            Stage stage = (Stage) tabelleStatistik.getScene().getWindow();
            stage.close();
        }
    }
}
