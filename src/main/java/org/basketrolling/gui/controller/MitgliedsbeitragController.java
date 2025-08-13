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
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.gui.controller.bearbeiten.MitgliedsbeitragBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse für das Menü der Mitgliedsbeiträge.
 * <p>
 * Diese Klasse verwaltet die Anzeige, Bearbeitung, Löschung und das Hinzufügen
 * von {@link Mitgliedsbeitrag}-Einträgen in einer {@link TableView}.
 * Administratoren können neue Beiträge hinzufügen oder bestehende bearbeiten
 * bzw. löschen.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable} für die Initialisierung der
 * UI-Komponenten und {@link MainBorderSettable}, um das zentrale
 * {@link BorderPane} der Anwendung zu setzen.
 * </p>
 *
 * <p>
 * <b>Funktionen:</b></p>
 * <ul>
 * <li>Anzeige aller Mitgliedsbeiträge aus der Datenbank</li>
 * <li>Formatierte Anzeige der Beträge in Euro</li>
 * <li>Bearbeiten- und Löschbuttons pro Tabellenzeile</li>
 * <li>Navigation zurück zum Hauptmenü</li>
 * </ul>
 *
 * @author Marko
 */
public class MitgliedsbeitragController implements Initializable, MainBorderSettable {

    private final MitgliedsbeitragDAO dao = new MitgliedsbeitragDAO();
    private final MitgliedsbeitragService service = new MitgliedsbeitragService(dao);

    @FXML
    private TableView<Mitgliedsbeitrag> tabelleMitgliedsbeitrag;

    @FXML
    private TableColumn<Mitgliedsbeitrag, String> saisonSpalte;

    @FXML
    private TableColumn<Mitgliedsbeitrag, Double> betragSpalte;

    @FXML
    private TableColumn<Mitgliedsbeitrag, Void> aktionenSpalte;

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    /**
     * Setzt das Haupt-{@link BorderPane} für diesen Controller.
     *
     * @param mainBorderPane das zentrale {@link BorderPane} der Anwendung
     */
    @Override
    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    /**
     * Initialisiert den Controller.
     * <ul>
     * <li>Setzt die Sichtbarkeit des Hinzufügen-Buttons basierend auf der
     * Benutzerrolle</li>
     * <li>Bindet die Spalten an die {@link Mitgliedsbeitrag}-Eigenschaften</li>
     * <li>Formatiert den Betrag in Euro-Format</li>
     * <li>Lädt alle Mitgliedsbeiträge aus der Datenbank</li>
     * <li>Fügt Bearbeiten- und Löschen-Buttons hinzu</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHinzufuegen.setVisible(Session.istAdmin());

        saisonSpalte.setCellValueFactory(new PropertyValueFactory<>("saison"));

        betragSpalte.setCellValueFactory(new PropertyValueFactory<>("betrag"));
        betragSpalte.setCellFactory(column -> new TableCell<Mitgliedsbeitrag, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f €", value));
                }
            }
        });

        List<Mitgliedsbeitrag> beitragListe = service.getAll();
        tabelleMitgliedsbeitrag.setItems(FXCollections.observableArrayList(beitragListe));

        buttonsHinzufuegen();
    }

    /**
     * Fügt der Aktionsspalte Buttons zum Bearbeiten und Löschen hinzu.
     * <p>
     * Beim Löschen wird der Benutzer zur Bestätigung aufgefordert. Erfolgt eine
     * Bestätigung, wird der Eintrag aus der Datenbank und aus der Tabelle
     * entfernt.
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
                    Mitgliedsbeitrag beitrag = getTableView().getItems().get(getIndex());
                    MitgliedsbeitragBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen(
                                    "/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitragbearbeiten.fxml",
                                    "Mitgliedsbeitrag Bearbeiten");
                    if (controller != null) {
                        controller.initMitgliedsbeitrag(beitrag);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Mitgliedsbeitrag beitrag = getTableView().getItems().get(getIndex());
                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung",
                            "Mitgliedsbeitrag löschen",
                            "Möchten Sie den Mitgliedsbeitrag für die Saison "
                            + beitrag.getSaison() + " wirklich löschen?");
                    if (bestaetigung) {
                        service.delete(beitrag);
                        tabelleMitgliedsbeitrag.getItems().remove(beitrag);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean leer) {
                super.updateItem(item, leer);
                if (leer) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                    bearbeitenBtn.setVisible(Session.istAdmin());
                    loeschenBtn.setVisible(Session.istAdmin());
                }
            }
        });
    }

    /**
     * Kehrt zum Hauptmenü zurück.
     */
    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    /**
     * Öffnet ein modales Fenster zum Hinzufügen eines neuen Mitgliedsbeitrags.
     */
    public void mitgliedsbeitragHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen(
                "/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitraghinzufuegen.fxml",
                "Mitgliedsbeitrag hinzufügen");
    }
}
