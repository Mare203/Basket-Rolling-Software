/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Statistik;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.gui.controller.bearbeiten.SpielBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse zur Verwaltung und Anzeige aller Spiele im System.
 * <p>
 * Stellt eine Tabelle bereit, in der alle gespeicherten Spiele mit ihren
 * relevanten Daten (Mannschaften, Liga, Datum, Halle, Ergebnis) angezeigt
 * werden. Administratoren können Spiele hinzufügen, bearbeiten oder löschen.
 * </p>
 *
 * <p>
 * <b>Funktionen:</b></p>
 * <ul>
 * <li>Anzeigen aller gespeicherten Spiele in einer Tabelle</li>
 * <li>Darstellung des Spielergebnisses als "PunkteIntern : PunkteExtern"</li>
 * <li>Bearbeiten bestehender Spiele</li>
 * <li>Löschen von Spielen, inkl. optionalem Löschen verknüpfter
 * Statistiken</li>
 * </ul>
 *
 * Implementiert {@link Initializable} für die Initialisierungslogik und
 * {@link MainBorderSettable}, um das Haupt-{@link BorderPane} zu setzen.
 *
 * @author Marko
 */
public class SpieleController implements Initializable, MainBorderSettable {

    private SpieleDAO spieleDao;
    private StatistikDAO statistikDao;

    private SpieleService spieleService;
    private StatistikService statistikService;

    @FXML
    private TableView<Spiele> tabelleSpiele;

    @FXML
    private TableColumn<Spiele, String> rollingSpalte;

    @FXML
    private TableColumn<Spiele, String> ergebnisSpalte;

    @FXML
    private TableColumn<Spiele, String> gegnerSpalte;

    @FXML
    private TableColumn<Spiele, String> ligaSpalte;

    @FXML
    private TableColumn<Spiele, String> datumSpalte;

    @FXML
    private TableColumn<Spiele, String> halleSpalte;

    @FXML
    private TableColumn<Spiele, Void> aktionenSpalte;

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    /**
     * Setzt das Haupt-{@link BorderPane}, um Navigation im UI zu ermöglichen.
     *
     * @param mainBorderPane zentrales Layout-Element
     */
    @Override
    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    /**
     * Initialisiert den Controller:
     * <ul>
     * <li>Erstellt DAO- und Service-Instanzen</li>
     * <li>Bindet Tabellen-Spalten an Datenbankfelder</li>
     * <li>Füllt die Tabelle mit allen gespeicherten Spielen</li>
     * <li>Richtet die Bearbeiten- und Löschen-Buttons ein</li>
     * </ul>
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spieleDao = new SpieleDAO();
        statistikDao = new StatistikDAO();

        spieleService = new SpieleService(spieleDao);
        statistikService = new StatistikService(statistikDao);

        btnHinzufuegen.setVisible(Session.istAdmin());

        rollingSpalte.setCellValueFactory(new PropertyValueFactory<>("mannschaftIntern"));
        ergebnisSpalte.setCellValueFactory(daten -> {
            Spiele spiel = daten.getValue();
            Integer intern = spiel.getInternPunkte();
            Integer extern = spiel.getExternPunkte();
            return new ReadOnlyObjectWrapper<>(intern + " : " + extern);
        });
        gegnerSpalte.setCellValueFactory(new PropertyValueFactory<>("mannschaftExtern"));
        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("liga"));
        datumSpalte.setCellValueFactory(new PropertyValueFactory<>("datum"));
        halleSpalte.setCellValueFactory(new PropertyValueFactory<>("halle"));

        buttonsHinzufuegen();

        List<Spiele> spieleListe = spieleService.getAll();
        tabelleSpiele.setItems(FXCollections.observableArrayList(spieleListe));
    }

    /**
     * Fügt in jeder Tabellenzeile Buttons zum Bearbeiten und Löschen eines
     * Spiels hinzu.
     * <p>
     * Beim Löschen wird überprüft, ob Statistiken verknüpft sind. Falls ja,
     * muss der Benutzer bestätigen, ob auch diese entfernt werden sollen.
     * </p>
     */
    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();
            private final HBox buttonBox = new HBox(15, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                bearbeitenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));

                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                bearbeitenBtn.setOnAction(e -> {
                    Spiele spiel = getTableView().getItems().get(getIndex());
                    SpielBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spiele/spielbearbeiten.fxml", "Spiel Bearbeiten");
                    if (controller != null) {
                        controller.initSpiel(spiel);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Spiele spiele = getTableView().getItems().get(getIndex());

                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung", "Spiel löschen", "Möchten Sie das Spiel wirklich löschen?"
                    );
                    if (!bestaetigung) {
                        return;
                    }

                    List<Statistik> statistiken = statistikService.getBySpiel(spiele);
                    if (!statistiken.isEmpty()) {
                        boolean mitStatistikLoeschen = AlertUtil.confirmationMitJaNein(
                                "Achtung – Verknüpfte Daten",
                                "Dieses Spiel hat verknüpfte Statistik-Einträge.",
                                "Wenn Sie fortfahren, werden auch alle zugehörigen Statistiken gelöscht.\nMöchten Sie trotzdem fortfahren?"
                        );
                        if (!mitStatistikLoeschen) {
                            return;
                        }
                    }

                    try {
                        spieleService.delete(spiele);
                        tabelleSpiele.getItems().remove(spiele);
                        AlertUtil.alertConfirmation("Erfolg", "Spiel wurde erfolgreich gelöscht.");
                    } catch (Exception ex) {
                        AlertUtil.alertError("Fehler beim Löschen", "Das Spiel konnte nicht gelöscht werden.", ex.getMessage());
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean leer) {
                super.updateItem(item, leer);
                setGraphic(leer ? null : buttonBox);
                if (!leer) {
                    bearbeitenBtn.setVisible(Session.istAdmin());
                    loeschenBtn.setVisible(Session.istAdmin());
                }
            }
        });
    }

    /**
     * Navigiert zurück ins Hauptmenü.
     */
    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    /**
     * Öffnet das Fenster zum Hinzufügen eines neuen Spiels.
     */
    public void spielHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spiele/spielhinzufuegen.fxml", "Spiel hinzufügen");
    }
}
