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
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.gui.controller.bearbeiten.MannschaftInternBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse für das Menü der internen Mannschaften.
 * <p>
 * Diese Klasse verwaltet die Anzeige, Bearbeitung, Löschung und das Hinzufügen
 * von {@link MannschaftIntern}-Einträgen in einer {@link TableView}.
 * Administratoren können interne Mannschaften hinzufügen oder bestehende
 * bearbeiten bzw. löschen.
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
 * <li>Anzeige aller internen Mannschaften aus der Datenbank</li>
 * <li>Bearbeiten- und Löschbuttons pro Tabellenzeile</li>
 * <li>Löschprüfung, ob noch Spieler, Spiele oder Trainings zugeordnet sind</li>
 * <li>Navigation zurück zum Hauptmenü</li>
 * </ul>
 *
 * @author Marko
 */
public class MannschaftInternmenuController implements Initializable, MainBorderSettable {

    private MannschaftInternDAO mannInDao;
    private SpielerDAO spielerDao;
    private SpieleDAO spieleDao;
    private TrainingDAO trainingDao;

    private MannschaftInternService mannInService;
    private SpielerService spielerService;
    private SpieleService spieleService;
    private TrainingService trainingService;

    @FXML
    private TableView<MannschaftIntern> tabelleMannschaftIntern;

    @FXML
    private TableColumn<MannschaftIntern, String> nameSpalte;

    @FXML
    private TableColumn<MannschaftIntern, String> ligaSpalte;

    @FXML
    private TableColumn<MannschaftIntern, String> trainerSpalte;

    @FXML
    private TableColumn<MannschaftIntern, Void> aktionenSpalte;

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
     * <li>Lädt alle internen Mannschaften aus der Datenbank</li>
     * <li>Fügt Bearbeiten- und Löschen-Buttons hinzu</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mannInDao = new MannschaftInternDAO();
        spielerDao = new SpielerDAO();
        spieleDao = new SpieleDAO();
        trainingDao = new TrainingDAO();

        mannInService = new MannschaftInternService(mannInDao);
        spielerService = new SpielerService(spielerDao);
        spieleService = new SpieleService(spieleDao);
        trainingService = new TrainingService(trainingDao);

        btnHinzufuegen.setVisible(Session.istAdmin());

        nameSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));
        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("liga"));
        trainerSpalte.setCellValueFactory(new PropertyValueFactory<>("trainer"));

        List<MannschaftIntern> mannschaftInternList = mannInService.getAll();
        tabelleMannschaftIntern.setItems(FXCollections.observableArrayList(mannschaftInternList));

        buttonsHinzufuegen();
    }

    /**
     * Fügt der Aktionsspalte Buttons zum Bearbeiten und Löschen hinzu.
     * <p>
     * Beim Löschen wird geprüft, ob der Mannschaft noch Spieler, Spiele oder
     * Trainings zugeordnet sind. Falls ja, wird eine Warnmeldung angezeigt.
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
                    MannschaftIntern mannschaftIntern = getTableView().getItems().get(getIndex());
                    MannschaftInternBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen(
                                    "/org/basketrolling/gui/fxml/mannschaften/mannschaftinternbearbeiten.fxml",
                                    "Interne Mannschaft Bearbeiten");
                    if (controller != null) {
                        controller.initMannschaftIntern(mannschaftIntern);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    MannschaftIntern mannschaftIntern = getTableView().getItems().get(getIndex());

                    if (kannMannschaftGeloeschtWerden(mannschaftIntern)) {
                        boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                                "Bestätigung",
                                mannschaftIntern.getName() + " löschen",
                                "Möchten Sie folgende Mannschaft wirklich löschen? - " + mannschaftIntern.getName());

                        if (bestaetigung) {
                            mannInService.delete(mannschaftIntern);
                            tabelleMannschaftIntern.getItems().remove(mannschaftIntern);
                        }
                    } else {
                        AlertUtil.alertWarning(
                                "Löschen nicht möglich",
                                "Die Mannschaft hat noch Spieler und/oder Spiele und/oder Trainings zugeordnet",
                                "Bitte entfernen Sie zuerst alle Zuordnungen, bevor Sie die Mannschaft löschen.");
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
     * Öffnet ein modales Fenster zum Hinzufügen einer neuen internen
     * Mannschaft.
     */
    public void mannschaftHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen(
                "/org/basketrolling/gui/fxml/mannschaften/mannschaftinternhinzufuegen.fxml",
                "Mannschaft hinzufügen");
    }

    /**
     * Prüft, ob eine interne Mannschaft gelöscht werden kann.
     * <p>
     * Eine Mannschaft kann nur gelöscht werden, wenn ihr keine Spieler, Spiele
     * oder Trainings zugeordnet sind.
     * </p>
     *
     * @param mannschaft die zu prüfende {@link MannschaftIntern}
     * @return {@code true}, wenn die Mannschaft gelöscht werden kann,
     * {@code false} andernfalls
     */
    public boolean kannMannschaftGeloeschtWerden(MannschaftIntern mannschaft) {
        List<Spieler> spieler = spielerService.getByMannschaft(mannschaft);
        List<Spiele> spiele = spieleService.getByMannschaftIntern(mannschaft);
        List<Training> training = trainingService.getByMannschaft(mannschaft);

        return spieler.isEmpty() && spiele.isEmpty() && training.isEmpty();
    }
}
