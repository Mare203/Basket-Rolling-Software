/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

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
 * Controller-Klasse zum Hinzufügen eines Elternkontakts.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Elternkontakten für
 * Spieler. Sie stellt Eingabefelder für Vorname, Nachname, Telefon, E-Mail
 * sowie eine Auswahlbox für den zugehörigen {@link Spieler} bereit.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren und die vorhandenen Spieler in die Auswahlbox zu
 * laden.
 * </p>
 *
 * @author Marko
 */
public class ElternkontaktHinzufuegenController implements Initializable {

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
     * Initialisiert den Controller.
     * <p>
     * Lädt die benötigten Services ({@link ElternkontaktService} und
     * {@link SpielerService}) und befüllt die ComboBox mit den vorhandenen
     * Spielern. Falls keine Spieler vorhanden sind, wird die Auswahlbox
     * deaktiviert.
     * </p>
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
     * Speichert den eingegebenen Elternkontakt.
     * <p>
     * Erstellt ein {@link Elternkontakt}-Objekt aus den eingegebenen Daten,
     * validiert die Pflichtfelder (Vorname, Nachname, Telefon, Spieler), und
     * übergibt es an den {@link ElternkontaktService}.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob ein weiterer
     * Elternkontakt angelegt werden soll. Falls nicht, wird das Fenster
     * geschlossen, andernfalls werden die Eingabefelder geleert.
     * </p>
     */
    public void speichern() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()
                && cbSpieler.getValue() != null) {

            Elternkontakt elternkontakt = new Elternkontakt();
            elternkontakt.setVorname(tfVorname.getText());
            elternkontakt.setNachname(tfNachname.getText());
            elternkontakt.setSpieler(cbSpieler.getValue());
            elternkontakt.setTelefon(tfTelefon.getText());
            elternkontakt.setEMail(tfEmail.getText());

            service.create(elternkontakt);

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Elternkontakt erfolgreich gespeichert!", "Möchten Sie einen weiteren Elternkontakt anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfVorname);
            } else {
                tfVorname.clear();
                tfNachname.clear();
                cbSpieler.setValue(null);
                tfTelefon.clear();
                tfEmail.clear();
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
        MenuUtil.fensterSchliessenMitWarnung(tfVorname);
    }
}
