/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Die Klasse {@code AlertUtil} stellt statische Hilfsmethoden zur Verfügung, um
 * standardisierte JavaFX-Dialogfenster (Alerts) anzuzeigen.
 *
 * Unterstützt werden Warnungen, Fehlermeldungen sowie Bestätigungsdialoge –
 * jeweils mit optionalem Textinhalt. Dadurch kann die Benutzerinteraktion
 * konsistent, wartbar und benutzerfreundlich gestaltet werden.
 *
 * Diese Klasse wird typischerweise in Controller-Klassen verwendet, um
 * Rückmeldungen bei Validierung, Fehlern oder kritischen Aktionen zu geben.
 *
 * @author Marko
 */
public class AlertUtil {

    /**
     * Zeigt einen Warnhinweis mit Titel, Header und Text an.
     *
     * @param title Fenstertitel des Dialogs
     * @param header Überschrift des Dialogs
     * @param text Inhalt des Dialogs
     * @return das angezeigte {@link Alert}-Objekt vom Typ
     * {@code AlertType.WARNING}
     */
    public static Alert alertWarning(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
        return alert;
    }

    /**
     * Zeigt einen Warnhinweis mit Titel und Header ohne Textinhalt an.
     *
     * @param title Fenstertitel des Dialogs
     * @param header Überschrift des Dialogs
     * @return das angezeigte {@link Alert}-Objekt vom Typ
     * {@code AlertType.WARNING}
     */
    public static Alert alertWarning(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
        return alert;
    }

    /**
     * Zeigt eine Fehlermeldung mit Titel, Header und Textinhalt an.
     *
     * @param title Fenstertitel des Dialogs
     * @param header Überschrift des Dialogs
     * @param text Inhalt des Dialogs
     * @return das angezeigte {@link Alert}-Objekt vom Typ
     * {@code AlertType.ERROR}
     */
    public static Alert alertError(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
        return alert;
    }

    /**
     * Zeigt eine Fehlermeldung mit Titel und Header ohne Textinhalt an.
     *
     * @param title Fenstertitel des Dialogs
     * @param header Überschrift des Dialogs
     * @return das angezeigte {@link Alert}-Objekt vom Typ
     * {@code AlertType.ERROR}
     */
    public static Alert alertError(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
        return alert;
    }

    /**
     * Zeigt einen Bestätigungsdialog mit Titel, Header und Textinhalt an.
     *
     * @param title Fenstertitel des Dialogs
     * @param header Überschrift des Dialogs
     * @param text Inhalt des Dialogs
     * @return das angezeigte {@link Alert}-Objekt vom Typ
     * {@code AlertType.CONFIRMATION}
     */
    public static Alert alertConfirmation(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
        return alert;
    }

    /**
     * Zeigt einen Bestätigungsdialog mit Titel und Header ohne Textinhalt an.
     *
     * @param title Fenstertitel des Dialogs
     * @param header Überschrift des Dialogs
     * @return das angezeigte {@link Alert}-Objekt vom Typ
     * {@code AlertType.CONFIRMATION}
     */
    public static Alert alertConfirmation(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
        return alert;
    }

    /**
     * Zeigt einen Bestätigungsdialog mit den Optionen „Ja“ und „Nein“ an.
     *
     * @param title Fenstertitel des Dialogs
     * @param header Überschrift des Dialogs
     * @param content Textinhalt des Dialogs
     * @return {@code true}, wenn der Benutzer „Ja“ ausgewählt hat, andernfalls
     * {@code false}
     */
    public static boolean confirmationMitJaNein(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType jaButton = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
        ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(jaButton, neinButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == jaButton;
    }
}
