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
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.gui.controller.bearbeiten.TrainingBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller für das Trainings-Menü.
 * <p>
 * Zeigt eine Liste aller Trainingseinheiten in einer Tabelle an und bietet
 * Funktionen zum Hinzufügen, Bearbeiten und Löschen von Trainings.
 * </p>
 *
 * <p>
 * Sichtbarkeit der Bearbeiten-/Löschen-Buttons sowie des Hinzufügen-Buttons ist
 * abhängig von der Admin-Berechtigung der aktuellen Sitzung.
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
public class TrainingController implements Initializable, MainBorderSettable {

    // DAO und Service für Trainingsverwaltung
    private final TrainingDAO dao = new TrainingDAO();
    private final TrainingService service = new TrainingService(dao);

    // Tabellenansicht & Spalten
    @FXML
    private TableView<Training> tabelleTraining;
    @FXML
    private TableColumn<Training, String> mannschaftSpalte;
    @FXML
    private TableColumn<Training, String> halleSpalte;
    @FXML
    private TableColumn<Training, String> tagSpalte;
    @FXML
    private TableColumn<Training, String> dauerSpalte;
    @FXML
    private TableColumn<Training, Void> aktionenSpalte;

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
     * Initialisiert die Tabelle, lädt die Trainingsliste und fügt
     * Aktions-Buttons pro Zeile hinzu. Zeigt die "Hinzufügen"-Schaltfläche nur
     * für Administratoren.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHinzufuegen.setVisible(Session.istAdmin());

        mannschaftSpalte.setCellValueFactory(new PropertyValueFactory<>("mannschaftIntern"));
        halleSpalte.setCellValueFactory(new PropertyValueFactory<>("halle"));
        tagSpalte.setCellValueFactory(new PropertyValueFactory<>("wochentag"));
        dauerSpalte.setCellValueFactory(new PropertyValueFactory<>("dauerInMin"));

        buttonsHinzufuegen();

        List<Training> trainingList = service.getAll();
        tabelleTraining.setItems(FXCollections.observableArrayList(trainingList));
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

                // Icons setzen
                bearbeitenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));

                // Styles setzen
                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                // Bearbeiten-Action
                bearbeitenBtn.setOnAction(e -> {
                    Training training = getTableView().getItems().get(getIndex());
                    TrainingBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/training/trainingbearbeiten.fxml",
                                    "Training Bearbeiten");
                    if (controller != null) {
                        controller.initTraining(training);
                    }
                });

                // Löschen-Action
                loeschenBtn.setOnAction(e -> {
                    Training training = getTableView().getItems().get(getIndex());
                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung",
                            "Training löschen",
                            "Möchten Sie das Training wirklich löschen?"
                    );

                    if (bestaetigung) {
                        service.delete(training);
                        tabelleTraining.getItems().remove(training);
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
     * Öffnet das Fenster zum Hinzufügen einer neuen Trainingseinheit.
     */
    public void trainingHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/training/traininghinzufuegen.fxml", "Training hinzufügen");
    }
}
