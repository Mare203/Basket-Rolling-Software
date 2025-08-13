/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

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
 * Controller-Klasse für das Bearbeiten einer {@link Liga}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten einer bestehenden Liga.
 * Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht Änderungen und
 * speichert diese über den {@link LigaService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service) vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Name der Liga</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class LigaBearbeitenController implements Initializable {

    private Liga bearbeitenLiga;

    private LigaDAO dao;
    private LigaService service;

    @FXML
    private TextField tfName;

    /**
     * Initialisiert den Controller und erstellt DAO- und Service-Instanzen.
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
     * Lädt die Daten der zu bearbeitenden {@link Liga} in die Eingabefelder.
     *
     * @param liga die zu bearbeitende {@link Liga}
     */
    public void initLiga(Liga liga) {
        this.bearbeitenLiga = liga;

        tfName.setText(liga.getName());
    }

    /**
     * Speichert die Änderungen an der Liga.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Aktualisiert das {@link Liga}-Objekt mit den neuen Werten</li>
     * <li>Speichert die Änderungen über den {@link LigaService}</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * Wenn Pflichtfelder fehlen oder ungültig sind, wird ein Warnhinweis
     * angezeigt.
     * </p>
     */
    public void aktualisieren() {
        if (!tfName.getText().isEmpty()) {

            bearbeitenLiga.setName(tfName.getText());

            service.update(bearbeitenLiga);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Liga erfolgreich aktualisiert!");
            MenuUtil.fensterSchliessenOhneWarnung(tfName);
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
