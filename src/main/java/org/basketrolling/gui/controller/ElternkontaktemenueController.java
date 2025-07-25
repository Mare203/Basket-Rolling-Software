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
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.basketrolling.beans.Elternkontakt;
import org.basketrolling.dao.ElternkontaktDAO;
import org.basketrolling.gui.controller.bearbeiten.ElternkontaktBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.ElternkontaktService;

/**
 *
 * @author Marko
 */
public class ElternkontaktemenueController implements Initializable, MainBorderSettable {

    ElternkontaktDAO dao = new ElternkontaktDAO();
    ElternkontaktService service = new ElternkontaktService(dao);

    @FXML
    private TableView<Elternkontakt> tabelleElternkontakte;

    @FXML
    private TableColumn<Elternkontakt, String> vornameSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> nachnameSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> spielerSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> telefonSpalte;

    @FXML
    private TableColumn<Elternkontakt, String> emailSpalte;

    @FXML
    private TableColumn<Elternkontakt, Void> aktionenSpalte;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        spielerSpalte.setCellValueFactory(new PropertyValueFactory<>("spieler"));
        telefonSpalte.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        emailSpalte.setCellValueFactory(new PropertyValueFactory<>("eMail"));

        buttonsHinzufuegen();

        List<Elternkontakt> elternkontaktList = service.getAll();
        tabelleElternkontakte.setItems(FXCollections.observableArrayList(elternkontaktList));

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
                        Elternkontakt elternkontakt = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Elternkontakt ansehen");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    try {
                        Elternkontakt elternkontakt = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/elternkontakte/elternkontaktebearbeiten.fxml"));
                        Scene scene = new Scene(loader.load());

                        ElternkontaktBearbeitenController controller = loader.getController();
                        controller.initElternkontakt(elternkontakt);

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Elternkontakt Bearbeiten");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Elternkontakt elternkontakt = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Bestätigung");
                    alert.setHeaderText(elternkontakt.getVorname() + " " + elternkontakt.getNachname() + " löschen");
                    alert.setContentText("Möchten Sie den Elternkontakt " + elternkontakt.getVorname() + " " + elternkontakt.getNachname() + " wirklich löschen?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        service.delete(elternkontakt);
                        tabelleElternkontakte.getItems().remove(elternkontakt);
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

    public void elternkontaktHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/elternkontakte/elternkontaktehinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage elternkontaktHinzufuegen = new Stage();
            elternkontaktHinzufuegen.setTitle("Elternkontakt hinzufügen");
            elternkontaktHinzufuegen.setScene(scene);
            elternkontaktHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            elternkontaktHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
