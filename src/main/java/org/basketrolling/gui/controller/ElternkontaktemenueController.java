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
import org.basketrolling.beans.Elternkontakt;
import org.basketrolling.dao.ElternkontaktDAO;
import org.basketrolling.gui.controller.bearbeiten.ElternkontaktBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.ElternkontaktService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 * Controller-Klasse für das Elternkontakte-Menü.
 * <p>
 * Diese Klasse verwaltet die Anzeige, Bearbeitung und Löschung von
 * {@link Elternkontakt}-Einträgen in einer {@link TableView}. Administratoren
 * haben zusätzlich die Möglichkeit, neue Einträge hinzuzufügen oder bestehende
 * zu bearbeiten/löschen.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable} für die Initialisierung der
 * UI-Komponenten sowie {@link MainBorderSettable}, um das zentrale
 * {@link BorderPane} der Anwendung zu setzen.
 * </p>
 *
 * @author Marko
 */
public class ElternkontaktemenueController implements Initializable, MainBorderSettable {

    private final ElternkontaktDAO dao = new ElternkontaktDAO();
    private final ElternkontaktService service = new ElternkontaktService(dao);

    @FXML
    private TableView<Elternkontakt> tabelleElternkontakte;

    @FXML
    private TableColumn<Elternkontakt, String> vornameSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> nachnameSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> spielerSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> telefonSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> emailSpalte;

    @FXML
    private TableColumn<Elternkontakt, Void> aktionenSpalte;

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
     * Initialisiert den Controller und befüllt die Tabelle mit den
     * gespeicherten Elternkontakten.
     * <ul>
     * <li>Bindet die Spalten an die Eigenschaften von
     * {@link Elternkontakt}</li>
     * <li>Fügt die Bearbeiten- und Löschen-Buttons hinzu</li>
     * <li>Lädt alle vorhandenen Elternkontakte aus der Datenbank</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHinzufuegen.setVisible(Session.istAdmin());

        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        spielerSpalte.setCellValueFactory(new PropertyValueFactory<>("spieler"));
        telefonSpalte.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        emailSpalte.setCellValueFactory(new PropertyValueFactory<>("eMail"));

        buttonsHinzufuegen();

        List<Elternkontakt> elternkontaktList = service.getAll();
        tabelleElternkontakte.setItems(FXCollections.observableArrayList(elternkontaktList));
    }

    /**
     * Fügt der Aktionsspalte Buttons zum Bearbeiten und Löschen hinzu.
     * <p>
     * Die Buttons werden nur angezeigt, wenn der Benutzer Administratorrechte
     * hat.
     * </p>
     * <ul>
     * <li>Bearbeiten-Button: Öffnet ein modales Fenster zur Bearbeitung des
     * ausgewählten Kontakts</li>
     * <li>Löschen-Button: Fragt eine Bestätigung ab und löscht den Eintrag aus
     * der Datenbank</li>
     * </ul>
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
                    Elternkontakt elternkontakt = getTableView().getItems().get(getIndex());
                    ElternkontaktBearbeitenController controller
                            = MenuUtil.neuesFensterModalAnzeigen(
                                    "/org/basketrolling/gui/fxml/elternkontakte/elternkontaktebearbeiten.fxml",
                                    "Elternkontakt Bearbeiten");

                    if (controller != null) {
                        controller.initElternkontakt(elternkontakt);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Elternkontakt elternkontakt = getTableView().getItems().get(getIndex());
                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung",
                            elternkontakt.getVorname() + " " + elternkontakt.getNachname() + " löschen",
                            "Möchten Sie den Elternkontakt " + elternkontakt.getVorname() + " " + elternkontakt.getNachname() + " wirklich löschen?");

                    if (bestaetigung) {
                        service.delete(elternkontakt);
                        tabelleElternkontakte.getItems().remove(elternkontakt);
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
     * Kehrt zum Hauptmenü der Anwendung zurück.
     */
    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    /**
     * Öffnet ein modales Fenster zum Hinzufügen eines neuen Elternkontakts.
     */
    public void elternkontaktHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen(
                "/org/basketrolling/gui/fxml/elternkontakte/elternkontaktehinzufuegen.fxml",
                "Elternkontakt hinzufügen");
    }
}
