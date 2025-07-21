/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.basketrolling.interfaces.MainBorderSettable;

/**
 *
 * @author Marko
 */
public class MenuOeffnenUtil {

    private final BorderPane borderPane;

    public MenuOeffnenUtil(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public void MenuOeffnen(String pfad) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pfad));
            Parent p = loader.load();

            Object controller = loader.getController();

            if (controller instanceof MainBorderSettable mbSettable) {
                mbSettable.setMainBorder(borderPane);
            }

            borderPane.setCenter(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
