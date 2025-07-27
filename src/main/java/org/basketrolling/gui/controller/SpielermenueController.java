/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.gui.controller.bearbeiten.SpielerBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.SpielerService;

/**
 *
 * @author Marko
 */
public class SpielermenueController implements Initializable, MainBorderSettable {

    SpielerDAO dao = new SpielerDAO();
    SpielerService service = new SpielerService(dao);

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Spieler> tabelleSpieler;

    @FXML
    private TableColumn<Spieler, String> vornameSpalte;

    @FXML
    private TableColumn<Spieler, String> nachnameSpalte;

    @FXML
    private TableColumn<Spieler, String> mannschaftSpalte;

    @FXML
    private TableColumn<Spieler, String> geburtsdatumSpalte;

    @FXML
    private TableColumn<Spieler, String> alterSpalte;

    @FXML
    private TableColumn<Spieler, Double> groesseSpalte;

    @FXML
    private TableColumn<Spieler, String> aktivSpalte;

    @FXML
    private TableColumn<Spieler, Void> aktionenSpalte;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        mannschaftSpalte.setCellValueFactory(new PropertyValueFactory<>("mannschaftIntern"));
        geburtsdatumSpalte.setCellValueFactory(new PropertyValueFactory<>("geburtsdatum"));
        alterSpalte.setCellValueFactory(new PropertyValueFactory<>("alter"));
        groesseSpalte.setCellValueFactory(new PropertyValueFactory<>("groesse"));
        groesseSpalte.setCellFactory(column -> new TableCell<Spieler, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", value));
                }
            }
        });
        aktivSpalte.setCellValueFactory(new PropertyValueFactory<>("aktiv"));

        buttonsHinzufuegen();

        List<Spieler> spielerListe = service.getAll();
        tabelleSpieler.setItems(FXCollections.observableArrayList(spielerListe));

    }

    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button ansehenBtn = new Button();
            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();

            private final HBox buttonBox = new HBox(15, ansehenBtn, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                ansehenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/see.png"));
                bearbeitenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/delete.png"));

                ansehenBtn.getStyleClass().add("icon-btn");
                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                ansehenBtn.setOnAction(e -> {
                    try {
                        Spieler spieler = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Spieler ansehen");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    try {
                        Spieler spieler = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spielerbearbeiten.fxml"));
                        Scene scene = new Scene(loader.load());

                        SpielerBearbeitenController controller = loader.getController();
                        controller.initSpieler(spieler);

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Spieler Bearbeiten");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Spieler spieler = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Bestätigung");
                    alert.setHeaderText("Spieler löschen");
                    alert.setContentText("Möchten Sie den Spieler " + spieler.getVorname() + " " + spieler.getNachname() + " wirklich löschen?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        service.delete(spieler);
                        tabelleSpieler.getItems().remove(spieler);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean leer) {
                super.updateItem(item, leer);
                if (leer) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });
    }

    private ImageView ladeBild(String pfad) {
        Image icon = new Image(getClass().getResourceAsStream(pfad));
        ImageView bild = new ImageView(icon);
        bild.setFitHeight(20);
        bild.setFitWidth(20);
        return bild;
    }

    public void backToHauptmenue() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/hauptmenu/hauptmenueCenter.fxml"));
            Parent hauptMenue = loader.load();

            mainBorderPane.setCenter(hauptMenue);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void spielerHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spielerhinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage spielerHinzufuegen = new Stage();
            spielerHinzufuegen.setTitle("Spieler hinzufügen");
            spielerHinzufuegen.setScene(scene);
            spielerHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            spielerHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
