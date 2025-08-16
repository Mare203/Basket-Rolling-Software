/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Liga;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.service.LigaService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse zum Hinzufügen einer Liga.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Ligen. Über ein
 * Eingabefeld kann ein Ligana­me erfasst und über den {@link LigaService} in
 * der Datenbank gespeichert werden.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren.
 * </p>
 *
 * @author Marko
 */
public class LigaHinzufuegenController implements Initializable {

    LigaDAO dao;
    LigaService service;

    @FXML
    private TextField tfName;

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt eine neue Instanz von {@link LigaDAO} und {@link LigaService}.
     * </p>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LigaDAO();
        service = new LigaService(dao);
    }

    /**
     * Speichert die eingegebene Liga.
     * <p>
     * Liest den Namen aus dem Eingabefeld und erstellt ein {@link Liga}-Objekt,
     * das über den {@link LigaService} gespeichert wird.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob eine weitere
     * Liga angelegt werden soll. Bei „Nein“ wird das Fenster geschlossen, bei
     * „Ja“ wird das Eingabefeld geleert.
     * </p>
     * <p>
     * Falls das Eingabefeld leer ist, wird eine Warnung über {@link AlertUtil}
     * angezeigt.
     * </p>
     */
    public void speichern() {
        if (!tfName.getText().isEmpty()) {
            Liga liga = new Liga();
            liga.setName(tfName.getText());

            service.create(liga);

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Liga erfolgreich gespeichert!", "Möchten Sie eine weitere Liga anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfName);
            } else {
                tfName.clear();
            }

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    /**
     * Bricht den Bearbeitungsvorgang ab und schließt das Fenster.
     * <p>
     * Vor dem Schließen wird der Benutzer um eine Bestätigung gebeten.
     * </p>
     */
    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfName);
    }
}
