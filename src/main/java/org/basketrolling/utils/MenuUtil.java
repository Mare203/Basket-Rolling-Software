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
import org.basketrolling.gui.controller.HauptmenueController;
import org.basketrolling.interfaces.MainBorderSettable;

/**
 * Die Klasse {@code MenuUtil} bietet statische Hilfsmethoden zur Verwaltung und
 * Anzeige von JavaFX-Fenstern (Stages) sowie zur dynamischen Steuerung des
 * zentralen Layouts innerhalb einer Anwendung mit BorderPane.
 *
 * Sie dient insbesondere dazu, FXML-Dateien zu laden, Fenster modal oder normal
 * zu öffnen, Stylesheets anzuwenden, Fenster mit oder ohne Warnung zu schließen
 * und zentrale Inhalte umzuschalten (z.B. zur Navigation im Hauptmenü).
 *
 * Diese Klasse fördert eine konsistente Fensterverwaltung und reduziert
 * Boilerplate-Code in den Controller-Klassen.
 *
 * @author Marko
 */
public class MenuUtil {

    /**
     * Öffnet ein neues modales Fenster mit dem angegebenen FXML-Pfad und Titel.
     *
     * @param <T> Typ des Controllers
     * @param pfad Pfad zur FXML-Datei (relativ zum Klassenpfad)
     * @param titel Fenstertitel
     * @return der zugehörige Controller oder {@code null} bei Fehler
     */
    public static <T> T neuesFensterModalAnzeigen(String pfad, String titel) {
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

    /**
     * Öffnet ein neues modales Fenster mit optionalem CSS-Stylesheet.
     *
     * @param <T> Typ des Controllers
     * @param fxmlPfad Pfad zur FXML-Datei
     * @param titel Fenstertitel
     * @param cssPfad Pfad zur CSS-Datei (optional)
     * @return der geladene Controller oder {@code null} bei Fehler
     */
    public static <T> T neuesFensterModalAnzeigen(String fxmlPfad, String titel, String cssPfad) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuUtil.class.getResource(fxmlPfad));
            Parent root = loader.load();

            T controller = loader.getController();

            Scene scene = new Scene(root);
            if (cssPfad != null && !cssPfad.isBlank()) {
                scene.getStylesheets().add(MenuUtil.class.getResource(cssPfad).toExternalForm());
            }

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titel);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Öffnet ein neues maximiertes Fenster mit optionalem CSS.
     *
     * @param <T> Typ des Controllers
     * @param fxmlPfad Pfad zur FXML-Datei
     * @param titel Fenstertitel
     * @param cssPfad Pfad zur CSS-Datei (optional)
     * @return der geladene Controller oder {@code null} bei Fehler
     */
    public static <T> T neuesFensterMaximiertAnzeigen(String fxmlPfad, String titel, String cssPfad) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuUtil.class.getResource(fxmlPfad));
            Parent root = loader.load();

            T controller = loader.getController();

            Scene scene = new Scene(root);
            if (cssPfad != null && !cssPfad.isBlank()) {
                scene.getStylesheets().add(MenuUtil.class.getResource(cssPfad).toExternalForm());
            }

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titel);
            stage.setMaximized(true);
            stage.show();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.alertError("Fehler", "Fehler beim Laden");
            return null;
        }
    }

    /**
     * Öffnet ein neues, nicht-modales Fenster mit optionalem CSS.
     *
     * @param <T> Typ des Controllers
     * @param fxmlPfad Pfad zur FXML-Datei
     * @param titel Fenstertitel
     * @param cssPfad Pfad zur CSS-Datei (optional)
     * @return der geladene Controller oder {@code null} bei Fehler
     */
    public static <T> T neuesFensterAnzeigen(String fxmlPfad, String titel, String cssPfad) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuUtil.class.getResource(fxmlPfad));
            Parent root = loader.load();

            T controller = loader.getController();

            Scene scene = new Scene(root);
            if (cssPfad != null && !cssPfad.isBlank()) {
                scene.getStylesheets().add(MenuUtil.class.getResource(cssPfad).toExternalForm());
            }

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titel);
            stage.show();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Zeigt eine Bestätigung beim Schließen eines Fensters an und schließt es,
     * wenn der Benutzer zustimmt.
     *
     * @param node ein beliebiges Steuerelement innerhalb des Fensters
     */
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

    /**
     * Schließt das zugehörige Fenster ohne Rückfrage.
     *
     * @param node ein beliebiges Steuerelement innerhalb des Fensters
     */
    public static void fensterSchliessenOhneWarnung(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    /**
     * Setzt den Hauptinhalt des übergebenen BorderPane auf das Hauptmenü und
     * übergibt den eingeloggten Benutzer.
     *
     * @param borderPane das zentrale Layout-Element der Anwendung
     */
    public static void backToHauptmenu(BorderPane borderPane) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuUtil.class.getResource("/org/basketrolling/gui/fxml/hauptmenu/hauptmenueCenter.fxml"));
            Parent hauptMenue = loader.load();

            HauptmenueController controller = loader.getController();

            borderPane.setCenter(hauptMenue);
            controller.initUser(Session.getBenutzer());

        } catch (IOException ex) {
            ex.printStackTrace();
            AlertUtil.alertError("Fehler beim Zurückkehren", "Das Hauptmenü konnte nicht geladen werden.", ex.getMessage());
        }
    }

    /**
     * Lädt ein Menü über FXML und setzt es als zentralen Bereich in ein
     * BorderPane. Wenn der geladene Controller {@link MainBorderSettable}
     * implementiert, wird ihm das BorderPane zur weiteren Navigation übergeben.
     *
     * @param <T> Typ des Controllers
     * @param borderPane das Hauptlayout-Element (z.B. in Hauptmenü oder
     * Dashboard)
     * @param fxmlPfad Pfad zur FXML-Datei
     * @return der geladene Controller oder {@code null} bei Fehler
     */
    public static <T> T ladeMenuUndSetzeCenter(BorderPane borderPane, String fxmlPfad) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuUtil.class.getResource(fxmlPfad));
            Parent view = loader.load();
            T controller = loader.getController();

            if (controller instanceof MainBorderSettable mbSettable) {
                mbSettable.setMainBorder(borderPane);
            }

            borderPane.setCenter(view);
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
