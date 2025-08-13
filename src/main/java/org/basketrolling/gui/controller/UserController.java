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
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller für die Benutzerverwaltung.
 * <p>
 * Zeigt alle gespeicherten Benutzer (Login-Objekte) in einer Tabelle an und
 * ermöglicht das Hinzufügen sowie das Löschen von Benutzern.
 * </p>
 *
 * <p>
 * Die Benutzerliste enthält Vorname, Nachname, Benutzername und Rolle. Nur
 * Benutzer mit Administratorrechten können neue Benutzer hinzufügen oder
 * bestehende löschen.
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
public class UserController implements Initializable, MainBorderSettable {

    // DAO & Service für Benutzerverwaltung
    private final LoginDAO dao = new LoginDAO();
    private final LoginService service = new LoginService(dao);

    // Tabellenansicht & Spalten
    @FXML
    private TableView<Login> tabelleLogin;
    @FXML
    private TableColumn<Login, String> vornameSpalte;
    @FXML
    private TableColumn<Login, String> nachnameSpalte;
    @FXML
    private TableColumn<Login, String> benutzernameSpalte;
    @FXML
    private TableColumn<Login, String> rolleSpalte;
    @FXML
    private TableColumn<Login, Void> aktionenSpalte;

    // Buttons
    @FXML
    private Button backBtn;

    // Hauptlayout
    private BorderPane mainBorderPane;

    /**
     * Übergibt das Haupt-Layout an den Controller.
     *
     * @param mainBorderPane Haupt-BorderPane-Layout
     */
    @Override
    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    /**
     * Initialisiert die Tabelle, setzt Spaltenzuweisungen und lädt alle
     * Benutzer aus der Datenbank. Bindet zusätzlich die Buttons pro Zeile ein.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Spalten befüllen
        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        benutzernameSpalte.setCellValueFactory(new PropertyValueFactory<>("benutzername"));
        rolleSpalte.setCellValueFactory(new PropertyValueFactory<>("rolle"));

        // Aktionsbuttons hinzufügen
        buttonsHinzufuegen();

        // Tabelle mit Daten füllen
        List<Login> benutzerListe = service.getAll();
        tabelleLogin.setItems(FXCollections.observableArrayList(benutzerListe));
    }

    /**
     * Fügt der Tabelle eine Löschen-Schaltfläche pro Zeile hinzu. Das Löschen
     * ist nur für Administratoren möglich.
     */
    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button loeschenBtn = new Button();
            private final HBox buttonBox = new HBox(15, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));
                loeschenBtn.getStyleClass().add("icon-btn");

                loeschenBtn.setOnAction(e -> {
                    Login login = getTableView().getItems().get(getIndex());

                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung",
                            "User löschen",
                            "Möchten Sie den User " + login.getBenutzername() + " wirklich löschen?"
                    );

                    if (bestaetigung) {
                        service.delete(login);
                        tabelleLogin.getItems().remove(login);
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
     * Öffnet das Fenster zum Hinzufügen eines neuen Benutzers.
     */
    public void userHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen(
                "/org/basketrolling/gui/fxml/login/userhinzufuegen.fxml",
                "User hinzufügen"
        );
    }
}
