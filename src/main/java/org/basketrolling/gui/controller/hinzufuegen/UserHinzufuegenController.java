/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.enums.Rolle;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse zum Hinzufügen eines Benutzers.
 * <p>
 * Diese Klasse verwaltet die Eingabe und Speicherung von Benutzern (Logins).
 * Erfasst werden können Vorname, Nachname, Benutzername, Passwort und Rolle.
 * Über den {@link LoginService} werden die Daten in der Datenbank gespeichert.
 * </p>
 * <p>
 * Sie implementiert {@link Initializable}, um beim Laden des FXML-Layouts die
 * Services zu initialisieren und die Rollen-Auswahlbox mit allen
 * {@link Rolle}-Werten zu befüllen.
 * </p>
 *
 * @author Marko
 */
public class UserHinzufuegenController implements Initializable {

    LoginDAO dao;
    LoginService service;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private TextField tfBenutzername;

    @FXML
    private TextField pfPasswort;

    @FXML
    private ComboBox<Rolle> cbRolle;

    /**
     * Initialisiert den Controller.
     * <p>
     * Erstellt eine neue Instanz von {@link LoginDAO} und {@link LoginService},
     * befüllt die ComboBox mit allen {@link Rolle}-Werten und setzt
     * standardmäßig die Rolle {@code ADMIN}.
     * </p>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new LoginDAO();
        service = new LoginService(dao);

        cbRolle.setItems(FXCollections.observableArrayList(Rolle.values()));
        cbRolle.setValue(Rolle.ADMIN);
    }

    /**
     * Speichert den eingegebenen Benutzer.
     * <p>
     * Validiert die Pflichtfelder (Vorname, Nachname, Benutzername, Passwort,
     * Rolle) und erstellt ein {@link Login}-Objekt, das über den
     * {@link LoginService} gespeichert wird.
     * </p>
     * <p>
     * Nach erfolgreichem Speichern wird der Benutzer gefragt, ob ein weiterer
     * Benutzer angelegt werden soll. Bei „Nein“ wird das Fenster geschlossen,
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
                && !tfBenutzername.getText().isEmpty()
                && !pfPasswort.getText().isEmpty()
                && cbRolle.getValue() != null) {

            Login login = new Login();
            login.setVorname(tfVorname.getText());
            login.setNachname(tfNachname.getText());
            login.setBenutzername(tfBenutzername.getText());
            login.setPasswort(pfPasswort.getText());
            login.setRolle(cbRolle.getValue());

            service.create(login);

            boolean weiter = AlertUtil.confirmationMitJaNein("Speichern erfolgreich", "User erfolgreich gespeichert!", "Möchten Sie einen weiteren User anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfBenutzername);
            } else {
                tfVorname.clear();
                tfNachname.clear();
                tfBenutzername.clear();
                pfPasswort.clear();
                cbRolle.setValue(null);
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
        MenuUtil.fensterSchliessenMitWarnung(tfBenutzername);
    }
}
