/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.utils;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.basketrolling.interfaces.MainBorderSettable;

/**
 *
 * @author Marko
 */
public class MenuUtil {

    private final BorderPane borderPane;

    public MenuUtil(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public void menuOeffnen(String pfad) {
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

    public static <T> T neuesFensterAnzeigen(String pfad, String titel) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuUtil.class.getResource(pfad));
            Parent root = loader.load();

            T controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titel);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void fensterSchliessenMitWarnung(Node node) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fenster schließen");
        alert.setHeaderText("Sind Sie sicher, dass Sie das Fenster schließen möchten?");
        alert.setContentText("Alle nicht gespeicherten Änderungen gehen verloren.");

        ButtonType jaButton = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
        ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(jaButton, neinButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == jaButton) {
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }
    }

    public static void fensterSchliessenOhneWarnung(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static boolean fensterSchliessenMitEigenerWarnung(String title, String header, String content) {
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
