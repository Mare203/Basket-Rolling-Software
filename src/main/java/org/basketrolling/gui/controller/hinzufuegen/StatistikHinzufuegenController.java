/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
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
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Statistik;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class StatistikHinzufuegenController implements Initializable {

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
    private TableColumn<Statistik, String> spaltePunkte;
    @FXML
    private TableColumn<Statistik, String> spalteFouls;
    @FXML
    private TableColumn<Statistik, Boolean> spalteGespielt;

    private final ObservableList<Statistik> statistikListe = FXCollections.observableArrayList();

    Spiele spiel;

    public void setSpiel(Spiele spiel) {
        this.spiel = spiel;

        lbMannschaftIntern.setText(spiel.getMannschaftIntern().getName());
        lbMannschaftExtern.setText(spiel.getMannschaftExtern().getName());
        lbPunkteIntern.setText(String.valueOf(spiel.getInternPunkte()));
        lbPunkteExtern.setText(String.valueOf(spiel.getExternPunkte()));
        lbDatum.setText(String.valueOf(spiel.getDatum()));

        if (spiel.getMannschaftIntern() != null) {
            List<Spieler> spielerListe = spielerService.getByMannschaft(spiel.getMannschaftIntern());

            for (Spieler spieler : spielerListe) {
                Statistik statistik = new Statistik();
                statistik.setSpiel(spiel);
                statistik.setSpieler(spieler);
                statistik.setPunkte(0);
                statistik.setFouls(0);
                statistik.setGespielt(false);

                statistikListe.add(statistik);
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

        spaltePunkte.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPunkte())));
        spaltePunkte.setCellFactory(TextFieldTableCell.forTableColumn());
        spaltePunkte.setOnEditCommit(event -> {
            String eingabe = event.getNewValue();

            try {
                int wert = Integer.parseInt(eingabe);
                if (wert < 0) {
                    throw new NumberFormatException();
                }
                event.getRowValue().setPunkte(wert);
            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige Eingabe", "Nur ganze Zahlen erlaubt", "Eingabe \"" + eingabe + "\" ist ungültig.");
                tabelleStatistik.refresh();
            }
        });

        spalteFouls.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getFouls())));
        spalteFouls.setCellFactory(TextFieldTableCell.forTableColumn());
        spalteFouls.setOnEditCommit(event -> {
            String eingabe = event.getNewValue();

            try {
                int wert = Integer.parseInt(eingabe);
                if (wert < 0) {
                    throw new NumberFormatException();
                }
                event.getRowValue().setFouls(wert);
            } catch (NumberFormatException e) {
                AlertUtil.alertWarning("Ungültige Eingabe", "Nur ganze Zahlen erlaubt", "Eingabe \"" + eingabe + "\" ist ungültig.");
                tabelleStatistik.refresh();
            }
        });

        spalteGespielt.setCellValueFactory(cell -> new SimpleBooleanProperty(cell.getValue().isGespielt()));
        spalteGespielt.setCellFactory(CheckBoxTableCell.forTableColumn(spalteGespielt));
        spalteGespielt.setOnEditCommit(event -> event.getRowValue().setGespielt(event.getNewValue()));

        tabelleStatistik.setEditable(true);
    }

    public void speichern() {
        int summePunkte = statistikListe.stream()
                .mapToInt(Statistik::getPunkte)
                .sum();

        int erwartetePunkte = spiel.getInternPunkte();

        if (summePunkte != erwartetePunkte) {
            AlertUtil.alertWarning("Fehlerhafte Punktzahl", "Gesamtsumme der Spieler-Punkte stimmt nicht!", "Die Summe der Punkte (" + summePunkte + ") stimmt nicht mit den Team-Punkten (" + erwartetePunkte + ") überein.");
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
            MenuUtil.fensterSchliessenOhneWarnung(lbDatum);
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(lbDatum);
    }
}
