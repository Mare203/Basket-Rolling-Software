/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.TrainerService;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.gui.controller.bearbeiten.TrainerBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller für das Trainer-Menü.
 * <p>
 * Zeigt eine Liste aller Trainer in einer Tabelle an und bietet Funktionen zum
 * Hinzufügen, Bearbeiten und Löschen von Trainern. Die zugeordneten
 * Mannschaften eines Trainers werden kommasepariert in der Spalte angezeigt.
 * </p>
 *
 * <p>
 * Sichtbarkeit der Bearbeiten-/Löschen-Buttons ist abhängig von der
 * Admin-Berechtigung der aktuellen Sitzung.
 * </p>
 *
 * Implementiert:
 * <ul>
 * <li>{@link Initializable} für Initialisierungslogik</li>
 * <li>{@link MainBorderSettable} zur Übergabe des Haupt-Layouts</li>
 * </ul>
 *
 * @author Marko
 */
public class TrainermenueController implements Initializable, MainBorderSettable {

    // DAO und Service für Trainerverwaltung
    private final TrainerDAO dao = new TrainerDAO();
    private final TrainerService service = new TrainerService(dao);

    // Tabellenansicht & Spalten
    @FXML
    private TableView<Trainer> tabelleTrainer;
    @FXML
    private TableColumn<Trainer, String> vornameSpalte;
    @FXML
    private TableColumn<Trainer, String> nachnameSpalte;
    @FXML
    private TableColumn<Trainer, String> mannschaftSpalte;
    @FXML
    private TableColumn<Trainer, String> telefonSpalte;
    @FXML
    private TableColumn<Trainer, String> emailSpalte;
    @FXML
    private TableColumn<Trainer, Void> aktionenSpalte;

    // Buttons
    @FXML
    private Button btnHinzufuegen;

    // Hauptlayout
    private BorderPane mainBorderPane;

    /**
     * Setzt das Haupt-Layout-Element, damit der Controller Navigationselemente
     * ansprechen kann.
     *
     * @param mainBorderPane zentrales Layout
     */
    @Override
    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    /**
     * Initialisiert die Tabelle, lädt die Trainerliste und fügt Aktions-Buttons
     * pro Zeile hinzu. Zeigt die "Hinzufügen"-Schaltfläche nur für
     * Administratoren.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHinzufuegen.setVisible(Session.istAdmin());

        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));

        // Mannschaften kommasepariert darstellen
        mannschaftSpalte.setCellValueFactory(cellData -> {
            String namen = cellData.getValue().getMannschaft()
                    .stream()
                    .map(MannschaftIntern::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(namen);
        });

        telefonSpalte.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        emailSpalte.setCellValueFactory(new PropertyValueFactory<>("eMail"));

        buttonsHinzufuegen();

        // Trainerliste laden
        List<Trainer> trainerList = service.getAll();
        tabelleTrainer.setItems(FXCollections.observableArrayList(trainerList));
    }

    /**
     * Fügt pro Tabellenzeile Bearbeiten- und Löschen-Buttons hinzu. Aktionen
     * sind nur für Administratoren sichtbar.
     */
    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();
            private final HBox buttonBox = new HBox(15, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                // Icons laden
                bearbeitenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));

                // Styles setzen
                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                // Bearbeiten-Action
                bearbeitenBtn.setOnAction(e -> {
                    Trainer trainer = getTableView().getItems().get(getIndex());
                    TrainerBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/trainer/trainerbearbeiten.fxml",
                                    "Trainer Bearbeiten");
                    if (controller != null) {
                        controller.initTrainer(trainer);
                    }
                });

                // Löschen-Action
                loeschenBtn.setOnAction(e -> {
                    Trainer trainer = getTableView().getItems().get(getIndex());
                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung",
                            trainer.getVorname() + " " + trainer.getNachname() + " löschen",
                            "Möchten Sie den Trainer " + trainer.getVorname() + " " + trainer.getNachname() + " wirklich löschen?"
                    );

                    if (bestaetigung) {
                        service.delete(trainer);
                        tabelleTrainer.getItems().remove(trainer);
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
     * Navigiert zurück ins Hauptmenü.
     */
    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    /**
     * Öffnet das Fenster zum Hinzufügen eines neuen Trainers.
     */
    public void trainerHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/trainer/trainerhinzufuegen.fxml", "Trainer hinzufügen");
    }
}
