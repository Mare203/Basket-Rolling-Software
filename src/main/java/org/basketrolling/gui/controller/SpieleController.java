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
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import org.basketrolling.beans.Spiele;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.service.SpieleService;

/**
 *
 * @author Marko
 */
public class SpieleController implements Initializable {

    SpieleDAO dao = new SpieleDAO();
    SpieleService service = new SpieleService(dao);

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Spiele> tabelleSpiele;

    @FXML
    private TableColumn<Spiele, String> rollingSpalte;

    @FXML
    private TableColumn<Spiele, String> ergebnisSpalte;

    @FXML
    private TableColumn<Spiele, String> gegnerSpalte;

    @FXML
    private TableColumn<Spiele, String> ligaSpalte;

    @FXML
    private TableColumn<Spiele, String> datumSpalte;

    @FXML
    private TableColumn<Spiele, String> halleSpalte;

    @FXML
    private TableColumn<Spiele, Void> aktionenSpalte;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rollingSpalte.setCellValueFactory(new PropertyValueFactory<>("mannschaftIntern"));
        ergebnisSpalte.setCellValueFactory(daten -> {
            Spiele spiel = daten.getValue();
            Integer intern = spiel.getInternPunkte();
            Integer extern = spiel.getExternPunkte();
            String ergebnis = intern + " : " + extern;
            return new ReadOnlyObjectWrapper<>(ergebnis);
        });
        gegnerSpalte.setCellValueFactory(new PropertyValueFactory<>("mannschaftExtern"));
        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("liga"));
        datumSpalte.setCellValueFactory(new PropertyValueFactory<>("datum"));
        halleSpalte.setCellValueFactory(new PropertyValueFactory<>("halle"));

        buttonsHinzufuegen();

        List<Spiele> spieleListe = service.getAll();
        tabelleSpiele.setItems(FXCollections.observableArrayList(spieleListe));

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
                        Spiele spiele = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Spiel ansehen");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    try {
                        Spiele spiele = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spielerbearbeiten.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Spiel Bearbeiten");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Spiele spiele = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Bestätigung");
                    alert.setHeaderText("Spiel löschen");
                    alert.setContentText("Möchten Sie das Spiel wirklich löschen?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        service.delete(spiele);
                        tabelleSpiele.getItems().remove(spiele);
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

    public void spielHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spiele/spielhinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage spielHinzufuegen = new Stage();
            spielHinzufuegen.setTitle("Spiel hinzufügen");
            spielHinzufuegen.setScene(scene);
            spielHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            spielHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
