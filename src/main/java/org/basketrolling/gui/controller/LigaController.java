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
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.gui.controller.bearbeiten.LigaBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse für das Ligen-Menü.
 * <p>
 * Diese Klasse verwaltet die Anzeige, Bearbeitung, Löschung und das Hinzufügen
 * von {@link Liga}-Einträgen in einer {@link TableView}. Administratoren können
 * Ligen hinzufügen oder bestehende bearbeiten bzw. löschen.
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
 * <li>Anzeige aller Ligen aus der Datenbank</li>
 * <li>Bearbeiten- und Löschbuttons pro Tabellenzeile</li>
 * <li>Löschprüfung, ob eine Liga noch mit Mannschaften verknüpft ist</li>
 * <li>Navigation zurück zum Hauptmenü</li>
 * </ul>
 *
 * @author Marko
 */
public class LigaController implements Initializable, MainBorderSettable {

    private LigaDAO ligaDao;
    private LigaService ligaService;

    private MannschaftInternService mannInService;
    private MannschaftInternDAO mannInDao;
    private MannschaftExternService mannExService;
    private MannschaftExternDAO mannExDao;

    @FXML
    private TableView<Liga> tabelleLiga;

    @FXML
    private TableColumn<Liga, String> ligaSpalte;

    @FXML
    private TableColumn<Liga, Void> aktionenSpalte;

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
     * <li>Lädt alle Ligen aus der Datenbank</li>
     * <li>Fügt Bearbeiten- und Löschen-Buttons hinzu</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ligaDao = new LigaDAO();
        mannInDao = new MannschaftInternDAO();
        mannExDao = new MannschaftExternDAO();

        ligaService = new LigaService(ligaDao);
        mannInService = new MannschaftInternService(mannInDao);
        mannExService = new MannschaftExternService(mannExDao);

        btnHinzufuegen.setVisible(Session.istAdmin());

        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));

        List<Liga> ligaList = ligaService.getAll();
        tabelleLiga.setItems(FXCollections.observableArrayList(ligaList));

        buttonsHinzufuegen();
    }

    /**
     * Fügt der Aktionsspalte Buttons zum Bearbeiten und Löschen hinzu.
     * <p>
     * Beim Löschen wird geprüft, ob die Liga noch mit internen oder externen
     * Mannschaften verknüpft ist. Falls ja, wird eine Warnmeldung angezeigt.
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
                    Liga liga = getTableView().getItems().get(getIndex());
                    LigaBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen(
                                    "/org/basketrolling/gui/fxml/liga/ligabearbeiten.fxml",
                                    "Liga Bearbeiten");
                    if (controller != null) {
                        controller.initLiga(liga);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Liga liga = getTableView().getItems().get(getIndex());

                    if (kannLigaGeloeschtWerden(liga)) {
                        boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                                "Bestätigung",
                                liga.getName() + " löschen",
                                "Möchten Sie folgende Liga wirklich löschen? - " + liga.getName());

                        if (bestaetigung) {
                            ligaService.delete(liga);
                            tabelleLiga.getItems().remove(liga);
                        }
                    } else {
                        AlertUtil.alertWarning(
                                "Löschen nicht möglich",
                                "Die Liga ist noch mit Mannschaften verknüpft.",
                                "Bitte entfernen Sie zuerst alle Zuordnungen, bevor Sie die Liga löschen.");
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
     * Öffnet ein modales Fenster zum Hinzufügen einer neuen Liga.
     */
    public void ligaHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen(
                "/org/basketrolling/gui/fxml/liga/ligahinzufuegen.fxml",
                "Liga hinzufügen");
    }

    /**
     * Prüft, ob eine Liga gelöscht werden kann.
     * <p>
     * Eine Liga kann nur gelöscht werden, wenn keine internen oder externen
     * Mannschaften mit ihr verknüpft sind.
     * </p>
     *
     * @param liga die zu prüfende {@link Liga}
     * @return {@code true}, wenn die Liga gelöscht werden kann, {@code false}
     * andernfalls
     */
    public boolean kannLigaGeloeschtWerden(Liga liga) {
        List<MannschaftIntern> internTeams = mannInService.getByLiga(liga);
        List<MannschaftExtern> externTeams = mannExService.getByLiga(liga);

        return internTeams.isEmpty() && externTeams.isEmpty();
    }
}
