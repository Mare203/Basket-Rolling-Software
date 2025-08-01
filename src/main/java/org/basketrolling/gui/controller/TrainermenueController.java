/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.TrainerService;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.gui.controller.bearbeiten.TrainerBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 *
 * @author Marko
 */
public class TrainermenueController implements Initializable, MainBorderSettable {

    TrainerDAO dao = new TrainerDAO();
    TrainerService service = new TrainerService(dao);

    @FXML
    private TableView<Trainer> tabelleTrainer;

    @FXML
    private TableColumn<Trainer, String> vornameSpalte;

    @FXML
    private TableColumn<Trainer, String> nachnameSpalte;

    @FXML
    private TableColumn<Trainer, String> mannschaftSpalte;

    @FXML
    private TableColumn<Trainer, String> telefonSpalte;

    @FXML
    private TableColumn<Trainer, String> emailSpalte;

    @FXML
    private TableColumn<Trainer, Void> aktionenSpalte;

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHinzufuegen.setVisible(Session.istAdmin());

        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        mannschaftSpalte.setCellValueFactory(cellData -> {
            String namen = cellData.getValue().getMannschaft().stream().map(MannschaftIntern::getName).collect(Collectors.joining(", "));
            return new SimpleStringProperty(namen);
        });
        telefonSpalte.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        emailSpalte.setCellValueFactory(new PropertyValueFactory<>("eMail"));

        buttonsHinzufuegen();

        List<Trainer> trainerList = service.getAll();
        tabelleTrainer.setItems(FXCollections.observableArrayList(trainerList));

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
                        Trainer trainer = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Trainer ansehen");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    Trainer trainer = getTableView().getItems().get(getIndex());

                    TrainerBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/trainer/trainerbearbeiten.fxml", "Trainer Bearbeiten");
                    if (controller != null) {
                        controller.initTrainer(trainer);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Trainer trainer = getTableView().getItems().get(getIndex());

                    boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung",
                            trainer.getVorname() + " " + trainer.getNachname() + " löschen",
                            "Möchten Sie den Trainer " + trainer.getVorname() + " " + trainer.getNachname() + " wirklich löschen?");

                    if (bestaetigung) {
                        service.delete(trainer);
                        tabelleTrainer.getItems().remove(trainer);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/hauptmenu/hauptmenueCenter.fxml"));
            Parent hauptMenue = loader.load();

            mainBorderPane.setCenter(hauptMenue);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void trainerHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/trainer/trainerhinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage trainerHinzufuegen = new Stage();
            trainerHinzufuegen.setTitle("Trainer hinzufügen");
            trainerHinzufuegen.setScene(scene);
            trainerHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            trainerHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
