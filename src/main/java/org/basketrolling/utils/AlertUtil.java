/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author Marko
 */
public class AlertUtil {

    /**
     * Zeigt einen Warnhinweis an, wenn ungültige oder unvollständige Eingaben
     * erkannt wurden.
     * <p>
     * Die Warnung informiert den Benutzer darüber, dass alle Pflichtfelder
     * ausgefüllt sein müssen. Es wird ein modaler Dialog mit dem Typ
     * {@code AlertType.WARNING} angezeigt, der erst nach Bestätigung
     * geschlossen wird.
     *
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
    
    public static Alert alertConfirmation(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
        return alert;
    }
    
      public static Alert alertConfirmation(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
        return alert;
    }
}
