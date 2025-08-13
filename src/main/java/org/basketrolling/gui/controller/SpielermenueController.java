/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.gui.controller.ansehen.SpielerAnzeigenController;
import org.basketrolling.gui.controller.bearbeiten.SpielerBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.SpielerService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse zur Verwaltung und Anzeige aller Spieler im System.
 * <p>
 * Bietet eine tabellarische Übersicht aller gespeicherten Spieler mit den
 * wichtigsten Informationen wie Name, Mannschaft, Geburtsdatum, Alter, Größe
 * und Aktivstatus. Administratoren können Spieler hinzufügen, bearbeiten oder
 * löschen.
 * </p>
 *
 * <p>
 * <b>Funktionen:</b></p>
 * <ul>
 * <li>Anzeigen aller gespeicherten Spieler in einer Tabelle</li>
 * <li>Öffnen eines Detailfensters zur Ansicht von Spielerinformationen</li>
 * <li>Bearbeiten bestehender Spielerdaten</li>
 * <li>Löschen von Spielern aus dem System</li>
 * </ul>
 *
 * Implementiert {@link Initializable} für die Initialisierungslogik und
 * {@link MainBorderSettable}, um das Haupt-{@link BorderPane} zu setzen.
 *
 * @author Marko
 */
public class SpielermenueController implements Initializable, MainBorderSettable {

    private SpielerDAO dao = new SpielerDAO();
    private SpielerService service = new SpielerService(dao);

    @FXML
    private TableView<Spieler> tabelleSpieler;

    @FXML
    private TableColumn<Spieler, String> vornameSpalte;

    @FXML
    private TableColumn<Spieler, String> nachnameSpalte;

    @FXML
    private TableColumn<Spieler, String> mannschaftSpalte;

    @FXML
    private TableColumn<Spieler, String> geburtsdatumSpalte;

    @FXML
    private TableColumn<Spieler, String> alterSpalte;

    @FXML
    private TableColumn<Spieler, Double> groesseSpalte;

    @FXML
    private TableColumn<Spieler, String> aktivSpalte;

    @FXML
    private TableColumn<Spieler, Void> aktionenSpalte;

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
     * <li>Aktiviert oder deaktiviert den "Hinzufügen"-Button abhängig von der
     * Benutzerrolle</li>
     * <li>Bindet Tabellen-Spalten an Spieler-Eigenschaften</li>
     * <li>Formatiert die Anzeige der Größe auf zwei Nachkommastellen</li>
     * <li>Fügt Bearbeiten-, Anzeigen- und Löschen-Buttons zu jeder
     * Tabellenzeile hinzu</li>
     * <li>Füllt die Tabelle mit allen gespeicherten Spielern</li>
     * </ul>
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHinzufuegen.setVisible(Session.istAdmin());

        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        mannschaftSpalte.setCellValueFactory(new PropertyValueFactory<>("mannschaftIntern"));
        geburtsdatumSpalte.setCellValueFactory(new PropertyValueFactory<>("geburtsdatum"));
        alterSpalte.setCellValueFactory(new PropertyValueFactory<>("alter"));

        groesseSpalte.setCellValueFactory(new PropertyValueFactory<>("groesse"));
        groesseSpalte.setCellFactory(column -> new TableCell<Spieler, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                setText((empty || value == null) ? null : String.format("%.2f", value));
            }
        });

        aktivSpalte.setCellValueFactory(new PropertyValueFactory<>("aktiv"));

        buttonsHinzufuegen();

        List<Spieler> spielerListe = service.getAll();
        tabelleSpieler.setItems(FXCollections.observableArrayList(spielerListe));
    }

    /**
     * Fügt zu jeder Tabellenzeile Buttons zum Ansehen, Bearbeiten und Löschen
     * eines Spielers hinzu.
     * <p>
     * - <b>Ansehen</b>: Öffnet ein Fenster mit Detailinformationen zum Spieler
     * - <b>Bearbeiten</b>: Öffnet ein Fenster zum Ändern der Spielerdaten -
     * <b>Löschen</b>: Entfernt den Spieler nach Bestätigung dauerhaft aus dem
     * System
     * </p>
     */
    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button ansehenBtn = new Button();
            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();
            private final HBox buttonBox = new HBox(15, ansehenBtn, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                ansehenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/see.png"));
                bearbeitenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));

                ansehenBtn.getStyleClass().add("icon-btn");
                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                ansehenBtn.setOnAction(e -> {
                    Spieler spieler = getTableView().getItems().get(getIndex());
                    SpielerAnzeigenController controller
                            = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml", "Spieler ansehen");
                    if (controller != null) {
                        controller.initSpieler(spieler);
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    Spieler spieler = getTableView().getItems().get(getIndex());
                    SpielerBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spieler/spielerbearbeiten.fxml", "Spieler Bearbeiten");
                    if (controller != null) {
                        controller.initSpieler(spieler);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Spieler spieler = getTableView().getItems().get(getIndex());
                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung",
                            "Spieler löschen",
                            "Möchten Sie den Spieler " + spieler.getVorname() + " " + spieler.getNachname() + " wirklich löschen?"
                    );
                    if (bestaetigung) {
                        service.delete(spieler);
                        tabelleSpieler.getItems().remove(spieler);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean leer) {
                super.updateItem(item, leer);
                if (leer) {
                    setGraphic(null);
                } else {
                    bearbeitenBtn.setVisible(Session.istAdmin());
                    loeschenBtn.setVisible(Session.istAdmin());
                    setGraphic(buttonBox);
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
     * Öffnet das Fenster zum Hinzufügen eines neuen Spielers.
     */
    public void spielerHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spieler/spielerhinzufuegen.fxml", "Spieler hinzufügen");
    }
}
