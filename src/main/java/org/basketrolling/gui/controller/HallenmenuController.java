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
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.gui.controller.bearbeiten.HalleBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.HalleService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse für das Hallen-Menü.
 * <p>
 * Diese Klasse verwaltet die Anzeige, Bearbeitung, Löschung und das Hinzufügen
 * von {@link Halle}-Einträgen in einer {@link TableView}. Administratoren
 * können Hallen hinzufügen oder bestehende bearbeiten bzw. löschen.
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
 * <li>Anzeige aller Hallen aus der Datenbank</li>
 * <li>Bearbeiten- und Löschbuttons pro Tabellenzeile</li>
 * <li>Löschprüfung, ob eine Halle noch mit Trainings oder Spielen verknüpft
 * ist</li>
 * <li>Navigation zu weiteren Menüs</li>
 * </ul>
 *
 * @author Marko
 */
public class HallenmenuController implements Initializable, MainBorderSettable {

    private HalleDAO halleDao;
    private TrainingDAO trainingDao;
    private SpieleDAO spieleDao;

    private HalleService halleService;
    private TrainingService trainingService;
    private SpieleService spieleService;

    @FXML
    private TableView<Halle> tabelleHalle;

    @FXML
    private TableColumn<Halle, String> halleSpalte;

    @FXML
    private TableColumn<Halle, String> strasseSpalte;

    @FXML
    private TableColumn<Halle, String> postleitzahlSpalte;

    @FXML
    private TableColumn<Halle, String> ortSpalte;

    @FXML
    private TableColumn<Halle, Void> aktionenSpalte;

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
     * <li>Erzeugt DAO- und Service-Instanzen</li>
     * <li>Setzt die Tabellenbindungen</li>
     * <li>Fügt Bearbeiten- und Löschen-Buttons hinzu</li>
     * <li>Lädt alle Hallen aus der Datenbank</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        halleDao = new HalleDAO();
        trainingDao = new TrainingDAO();
        spieleDao = new SpieleDAO();

        halleService = new HalleService(halleDao);
        trainingService = new TrainingService(trainingDao);
        spieleService = new SpieleService(spieleDao);

        btnHinzufuegen.setVisible(Session.istAdmin());

        halleSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));
        strasseSpalte.setCellValueFactory(new PropertyValueFactory<>("strasse"));
        postleitzahlSpalte.setCellValueFactory(new PropertyValueFactory<>("plz"));
        ortSpalte.setCellValueFactory(new PropertyValueFactory<>("ort"));

        buttonsHinzufuegen();

        List<Halle> halleList = halleService.getAll();
        tabelleHalle.setItems(FXCollections.observableArrayList(halleList));
    }

    /**
     * Fügt der Aktionsspalte Buttons zum Bearbeiten und Löschen hinzu.
     * <p>
     * Beim Löschen wird geprüft, ob die Halle noch mit Trainings oder Spielen
     * verknüpft ist. Falls ja, wird eine Warnmeldung angezeigt.
     * </p>
     */
    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();
            private final HBox buttonBox = new HBox(15, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.BASELINE_LEFT);
                bearbeitenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));

                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                bearbeitenBtn.setOnAction(e -> {
                    Halle halle = getTableView().getItems().get(getIndex());
                    HalleBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen(
                                    "/org/basketrolling/gui/fxml/halle/hallebearbeiten.fxml",
                                    "Halle Bearbeiten");

                    if (controller != null) {
                        controller.initHalle(halle);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Halle halle = getTableView().getItems().get(getIndex());

                    if (kannHalleGeloeschtWerden(halle)) {
                        boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                                "Bestätigung",
                                halle.getName() + " löschen",
                                "Möchten Sie folgende Halle wirklich löschen? - " + halle.getName());

                        if (bestaetigung) {
                            halleService.delete(halle);
                            tabelleHalle.getItems().remove(halle);
                        }
                    } else {
                        AlertUtil.alertWarning(
                                "Löschen nicht möglich",
                                "Die Halle ist noch mit Trainingseinheiten und/oder Spielen verknüpft.",
                                "Bitte entfernen Sie zuerst alle Zuordnungen, bevor Sie die Halle löschen.");
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
     * Öffnet ein modales Fenster zum Hinzufügen einer neuen Halle.
     */
    public void halleHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen(
                "/org/basketrolling/gui/fxml/halle/hallehinzufuegen.fxml",
                "Halle hinzufügen");
    }

    /**
     * Prüft, ob eine Halle gelöscht werden kann.
     * <p>
     * Eine Halle kann nur gelöscht werden, wenn sie nicht mit {@link Training}
     * oder {@link Spiele} verknüpft ist.
     * </p>
     *
     * @param halle die zu prüfende {@link Halle}
     * @return {@code true}, wenn die Halle gelöscht werden kann, {@code false}
     * andernfalls
     */
    public boolean kannHalleGeloeschtWerden(Halle halle) {
        List<Training> training = trainingService.getByHalle(halle);
        List<Spiele> spiele = spieleService.getByHalle(halle);

        return training.isEmpty() && spiele.isEmpty();
    }
}
