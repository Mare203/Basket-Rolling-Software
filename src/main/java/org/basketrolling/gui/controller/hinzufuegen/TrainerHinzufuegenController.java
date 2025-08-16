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
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.TrainerService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse zum Hinzufügen eines Trainers.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Trainern. Erfasst
 * werden können Vorname, Nachname, Telefonnummer und E-Mail. Über den
 * {@link TrainerService} werden die Daten in der Datenbank gespeichert.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * benötigten Services zu initialisieren.
 * </p>
 *
 * @author Marko
 */
public class TrainerHinzufuegenController implements Initializable {

    TrainerDAO dao;
    TrainerService service;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private TextField tfTelefon;

    @FXML
    private TextField tfEmail;

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt eine neue Instanz von {@link TrainerDAO} und
     * {@link TrainerService}.
     * </p>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new TrainerDAO();
        service = new TrainerService(dao);
    }

    /**
     * Speichert den eingegebenen Trainer.
     * <p>
     * Liest die Pflichtfelder (Vorname, Nachname, Telefonnummer) sowie optional
     * die E-Mail aus, erstellt ein {@link Trainer}-Objekt und übergibt es an
     * den {@link TrainerService}.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob ein weiterer
     * Trainer angelegt werden soll. Bei „Nein“ wird das Fenster geschlossen,
     * bei „Ja“ werden die Eingabefelder geleert.
     * </p>
     * <p>
     * Falls Pflichtfelder fehlen, wird eine Warnung über {@link AlertUtil}
     * angezeigt.
     * </p>
     */
    public void speichern() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()) {

            Trainer trainer = new Trainer();
            trainer.setVorname(tfVorname.getText());
            trainer.setNachname(tfNachname.getText());
            trainer.setTelefon(tfTelefon.getText());
            trainer.setEMail(tfEmail.getText());

            service.create(trainer);

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "Trainer erfolgreich gespeichert!", "Möchten Sie einen weiteren Trainer anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfVorname);
            } else {
                tfVorname.clear();
                tfNachname.clear();
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
