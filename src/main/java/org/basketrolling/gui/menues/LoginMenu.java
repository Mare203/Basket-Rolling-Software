/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.menues;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Einstiegspunkt der JavaFX-Anwendung <b>Basket Rolling</b>.
 *
 * Diese Klasse lädt das Login-FXML-Layout und zeigt das Loginfenster beim Start
 * der Anwendung an. Sie setzt auch das zentrale CSS-Styling für die gesamte
 * GUI.
 *
 * Der Start erfolgt über die Methode {@link #main(String[])} mit JavaFX
 * {@code launch()}.
 *
 * @author Marko
 */
public class LoginMenu extends Application {

    /**
     * Startet die JavaFX-Anwendung und lädt die Login-Oberfläche.
     *
     * @param stage das primäre Anwendungsfenster
     * @throws Exception wenn das FXML nicht geladen werden kann
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/login/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/org/basketrolling/gui/css/styles.css").toExternalForm());

        stage.setTitle("Basket Rolling - Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main-Methode und Startpunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
