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
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.TrainerService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Bearbeiten eines {@link Trainer}.
 * <p>
 * Diese Klasse steuert das UI-Fenster zum Bearbeiten eines bestehenden
 * Trainers. Sie lädt die aktuellen Daten in die Eingabefelder, ermöglicht
 * Änderungen und speichert diese über den {@link TrainerService}.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des Fensters
 * notwendige Initialisierungen (DAO/Service) vorzunehmen.
 * </p>
 * <p>
 * Pflichtfelder:
 * <ul>
 * <li>Vorname</li>
 * <li>Nachname</li>
 * <li>Telefonnummer</li>
 * </ul>
 * E-Mail ist optional.
 * </p>
 *
 * @author Marko
 */
public class TrainerBearbeitenController implements Initializable {

    private Trainer bearbeitenTrainer;

    private TrainerDAO dao;
    private TrainerService service;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private TextField tfTelefon;

    @FXML
    private TextField tfEmail;

    /**
     * Initialisiert den Controller und erstellt DAO- und Service-Instanzen.
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
     * Lädt die Daten des zu bearbeitenden {@link Trainer} in die Eingabefelder.
     *
     * @param trainer der zu bearbeitende {@link Trainer}
     */
    public void initTrainer(Trainer trainer) {
        this.bearbeitenTrainer = trainer;

        tfVorname.setText(trainer.getVorname());
        tfNachname.setText(trainer.getNachname());
        tfTelefon.setText(String.valueOf(trainer.getTelefon()));
        tfEmail.setText(trainer.getEMail());
    }

    /**
     * Speichert die Änderungen am Trainer.
     * <p>
     * Führt eine Validierung der Pflichtfelder durch. Wenn alle Felder gültig
     * sind:
     * <ul>
     * <li>Aktualisiert das {@link Trainer}-Objekt mit den neuen Werten</li>
     * <li>Speichert die Änderungen über den {@link TrainerService}</li>
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
                && !tfTelefon.getText().isEmpty()) {

            bearbeitenTrainer.setVorname(tfVorname.getText());
            bearbeitenTrainer.setNachname(tfNachname.getText());
            bearbeitenTrainer.setTelefon(tfTelefon.getText());
            bearbeitenTrainer.setEMail(tfEmail.getText());

            service.update(bearbeitenTrainer);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Trainer erfolgreich aktualisiert!");
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
