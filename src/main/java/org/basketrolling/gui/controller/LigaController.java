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
import org.basketrolling.beans.Liga;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.gui.controller.bearbeiten.LigaBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LigaService;

/**
 *
 * @author Marko
 */
public class LigaController implements Initializable, MainBorderSettable {

    LigaDAO dao = new LigaDAO();
    LigaService service = new LigaService(dao);

    @FXML
    private TableView<Liga> tabelleLiga;

    @FXML
    private TableColumn<Liga, String> ligaSpalte;

    @FXML
    private TableColumn<Liga, Void> aktionenSpalte;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));

        List<Liga> mannschaftExternList = service.getAll();
        tabelleLiga.setItems(FXCollections.observableArrayList(mannschaftExternList));

        buttonsHinzufuegen();
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
                        Liga liga = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Liga ansehen");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    try {
                        Liga liga = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/liga/ligabearbeiten.fxml"));
                        Scene scene = new Scene(loader.load());

                        LigaBearbeitenController controller = loader.getController();
                        controller.initLiga(liga);
                        
                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Liga Bearbeiten");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Liga liga = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Bestätigung");
                    alert.setHeaderText(liga.getName() + " löschen");
                    alert.setContentText("Möchten Sie folgende Liga wirklich löschen? - " + liga.getName());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        service.delete(liga);
                        tabelleLiga.getItems().remove(liga);
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
    
            public void ligaHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/liga/ligahinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());
            
            Stage ligaHinzufuegen = new Stage();
            ligaHinzufuegen.setTitle("Liga hinzufügen");
            ligaHinzufuegen.setScene(scene);
            ligaHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            ligaHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
