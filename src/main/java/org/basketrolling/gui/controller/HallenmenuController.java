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
import org.basketrolling.beans.Halle;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.gui.controller.bearbeiten.HalleBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.HalleService;

/**
 *
 * @author Marko
 */
public class HallenmenuController implements Initializable, MainBorderSettable {

    HalleDAO dao = new HalleDAO();
    HalleService service = new HalleService(dao);

    @FXML
    private TableView<Halle> tabelleHalle;

    @FXML
    private TableColumn<Halle, String> halleSpalte;

    @FXML
    private TableColumn<Halle, String> strasseSpalte;

    @FXML
    private TableColumn<Halle, String> postleitzahlSpalte;

    @FXML
    private TableColumn<Halle, String> ortSpalte;
    
    @FXML
    private TableColumn<Halle, Void> aktionenSpalte;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        halleSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));
        strasseSpalte.setCellValueFactory(new PropertyValueFactory<>("strasse"));
        postleitzahlSpalte.setCellValueFactory(new PropertyValueFactory<>("plz"));
        ortSpalte.setCellValueFactory(new PropertyValueFactory<>("ort"));
        
        buttonsHinzufuegen();
        
        List<Halle> halleList = service.getAll();
        tabelleHalle.setItems(FXCollections.observableArrayList(halleList));
    }
    
        private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button ansehenBtn = new Button();
            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();

            private final HBox buttonBox = new HBox(15, ansehenBtn, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.BASELINE_LEFT);
                ansehenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/see.png"));
                bearbeitenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/delete.png"));

                ansehenBtn.getStyleClass().add("icon-btn");
                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                ansehenBtn.setOnAction(e -> {
                    try {
                        Halle halle = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Halle ansehen");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    try {
                        Halle halle = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/halle/hallebearbeiten.fxml"));
                        Scene scene = new Scene(loader.load());
                        
                        HalleBearbeitenController controller = loader.getController();
                        controller.initHalle(halle);

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Halle Bearbeiten");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Halle halle = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Bestätigung");
                    alert.setHeaderText(halle.getName() + " löschen");
                    alert.setContentText("Möchten Sie folgende Halle wirklich löschen? - " + halle.getName());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        service.delete(halle);
                        tabelleHalle.getItems().remove(halle);
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
    
    public void halleHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/halle/hallehinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage halle = new Stage();
            halle.setTitle("Halle hinzufügen");
            halle.setScene(scene);
            halle.initModality(Modality.APPLICATION_MODAL);
            halle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
