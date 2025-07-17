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

    public static Alert alertUngueltigeEingabe() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Eingabefehler");
        alert.setHeaderText("Unvollständige oder ungültige Eingaben");
        String text = "- Alle Pflichtfelder müssen ausgefüllt sein.";
        alert.setContentText(text);
        alert.showAndWait();
        return alert;
    }
}
