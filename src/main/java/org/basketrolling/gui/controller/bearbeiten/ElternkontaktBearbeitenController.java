/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Elternkontakt;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.ElternkontaktDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.service.ElternkontaktService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Bearbeiten eines {@link Elternkontakt}-Eintrags.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten eines bestehenden
 * Elternkontakts. Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht
 * Änderungen und speichert diese über den {@link ElternkontaktService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Daten wie die Spieler-Liste zu initialisieren.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Vorname</li>
 * <li>Nachname</li>
 * <li>Telefonnummer</li>
 * <li>Spieler-Auswahl</li>
 * </ul>
 * </p>
 *
 * @author Marko
 */
public class ElternkontaktBearbeitenController implements Initializable {

    private Elternkontakt bearbeitenElternkontakt;

    private ElternkontaktDAO dao;
    private ElternkontaktService service;

    private SpielerDAO spielerDAO;
    private SpielerService spielerService;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private ComboBox<Spieler> cbSpieler;

    @FXML
    private TextField tfTelefon;

    @FXML
    private TextField tfEmail;

    /**
     * Initialisiert den Controller und lädt die Liste aller Spieler.
     * <ul>
     * <li>Erstellt DAO- und Service-Instanzen</li>
     * <li>Lädt die vorhandenen Spieler aus der Datenbank</li>
     * <li>Befüllt die Spieler-Auswahl-{@link ComboBox}</li>
     * <li>Deaktiviert die {@link ComboBox}, falls keine Spieler vorhanden
     * sind</li>
     * </ul>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new ElternkontaktDAO();
        service = new ElternkontaktService(dao);

        spielerDAO = new SpielerDAO();
        spielerService = new SpielerService(spielerDAO);

        List<Spieler> spielerList = spielerService.getAll();

        if (spielerList != null && !spielerList.isEmpty()) {
            cbSpieler.setItems(FXCollections.observableArrayList(spielerList));
        } else {
            cbSpieler.setDisable(true);
        }
    }

    /**
     * Lädt die Daten des zu bearbeitenden {@link Elternkontakt} in die
     * Eingabefelder.
     *
     * @param elternkontakt der zu bearbeitende {@link Elternkontakt}
     */
    public void initElternkontakt(Elternkontakt elternkontakt) {
        this.bearbeitenElternkontakt = elternkontakt;

        tfVorname.setText(elternkontakt.getVorname());
        tfNachname.setText(elternkontakt.getNachname());
        cbSpieler.setValue(elternkontakt.getSpieler());
        tfTelefon.setText(String.valueOf(elternkontakt.getTelefon()));
        tfEmail.setText(elternkontakt.getEMail());
    }

    /**
     * Speichert die Änderungen am Elternkontakt.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Aktualisiert das {@link Elternkontakt}-Objekt mit den neuen
     * Werten</li>
     * <li>Speichert die Änderungen über den {@link ElternkontaktService}</li>
     * <li>Zeigt eine Bestätigungsmeldung an</li>
     * <li>Schließt das Fenster ohne weitere Warnung</li>
     * </ul>
     * Wenn Pflichtfelder fehlen oder ungültig sind, wird ein Warnhinweis
     * angezeigt.
     * </p>
     */
    public void aktualisieren() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()
                && cbSpieler.getValue() != null) {

            bearbeitenElternkontakt.setVorname(tfVorname.getText());
            bearbeitenElternkontakt.setNachname(tfNachname.getText());
            bearbeitenElternkontakt.setSpieler(cbSpieler.getValue());
            bearbeitenElternkontakt.setTelefon(tfTelefon.getText());
            bearbeitenElternkontakt.setEMail(tfEmail.getText());

            service.update(bearbeitenElternkontakt);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Elternkontakt erfolgreich aktualisiert!");
            MenuUtil.fensterSchliessenOhneWarnung(tfVorname);
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
        MenuUtil.fensterSchliessenMitWarnung(tfVorname);
    }
}
