/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.gui.controller.bearbeiten.HalleBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.HalleService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 *
 * @author Marko
 */
public class HallenmenuController implements Initializable, MainBorderSettable {

    HalleDAO halleDao;
    TrainingDAO trainingDao;
    SpieleDAO spieleDao;

    HalleService halleService;
    TrainingService trainingService;
    SpieleService spieleService;

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

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        halleDao = new HalleDAO();
        trainingDao = new TrainingDAO();
        spieleDao = new SpieleDAO();

        halleService = new HalleService(halleDao);
        trainingService = new TrainingService(trainingDao);
        spieleService = new SpieleService(spieleDao);

        btnHinzufuegen.setVisible(Session.istAdmin());

        halleSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));
        strasseSpalte.setCellValueFactory(new PropertyValueFactory<>("strasse"));
        postleitzahlSpalte.setCellValueFactory(new PropertyValueFactory<>("plz"));
        ortSpalte.setCellValueFactory(new PropertyValueFactory<>("ort"));

        buttonsHinzufuegen();

        List<Halle> halleList = halleService.getAll();
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
                    Halle halle = getTableView().getItems().get(getIndex());
                    HalleBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/halle/hallebearbeiten.fxml", "Halle Bearbeiten");

                    if (controller != null) {
                        controller.initHalle(halle);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Halle halle = getTableView().getItems().get(getIndex());

                    if (kannHalleGeloeschtWerden(halle)) {
                        boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", halle.getName() + " löschen", "Möchten Sie folgende Halle wirklich löschen? - " + halle.getName());

                        if (bestaetigung) {
                            halleService.delete(halle);
                            tabelleHalle.getItems().remove(halle);
                        }
                    } else {
                        AlertUtil.alertWarning("Löschen nicht möglich", "Die Halle ist noch mit Trainingseinheiten und/oder Spielen verknüpft.", "Bitte entfernen Sie zuerst alle Zuordnungen, bevor Sie die Halle löschen.");
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
                    bearbeitenBtn.setVisible(Session.istAdmin());
                    loeschenBtn.setVisible(Session.istAdmin());
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
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    public void halleHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/halle/hallehinzufuegen.fxml", "Halle hinzufügen");
    }

    public boolean kannHalleGeloeschtWerden(Halle halle) {
        List<Training> training = trainingService.getByHalle(halle);
        List<Spiele> spiele = spieleService.getByHalle(halle);

        return training.isEmpty() && spiele.isEmpty();
    }
}
