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
import javafx.scene.Parent;
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
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.gui.controller.bearbeiten.MannschaftInternBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 *
 * @author Marko
 */
public class MannschaftInternmenuController implements Initializable, MainBorderSettable {

    MannschaftInternDAO mannInDao;
    SpielerDAO spielerDao;
    SpieleDAO spieleDao;
    TrainingDAO trainingDao;

    MannschaftInternService mannInService;
    SpielerService spielerService;
    SpieleService spieleService;
    TrainingService trainingService;

    @FXML
    private TableView<MannschaftIntern> tabelleMannschaftIntern;

    @FXML
    private TableColumn<MannschaftIntern, String> nameSpalte;

    @FXML
    private TableColumn<MannschaftIntern, String> ligaSpalte;

    @FXML
    private TableColumn<MannschaftIntern, String> trainerSpalte;

    @FXML
    private TableColumn<MannschaftIntern, Void> aktionenSpalte;

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mannInDao = new MannschaftInternDAO();
        spielerDao = new SpielerDAO();
        spieleDao = new SpieleDAO();
        trainingDao = new TrainingDAO();

        mannInService = new MannschaftInternService(mannInDao);
        spielerService = new SpielerService(spielerDao);
        spieleService = new SpieleService(spieleDao);
        trainingService = new TrainingService(trainingDao);

        nameSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));
        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("liga"));
        trainerSpalte.setCellValueFactory(new PropertyValueFactory<>("trainer"));

        List<MannschaftIntern> mannschaftInternList = mannInService.getAll();
        tabelleMannschaftIntern.setItems(FXCollections.observableArrayList(mannschaftInternList));

        buttonsHinzufuegen();

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
                        MannschaftIntern mannschaftIntern = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spieleransehen.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("Externe Mannschaft ansehen");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                bearbeitenBtn.setOnAction(e -> {
                    MannschaftIntern mannschaftIntern = getTableView().getItems().get(getIndex());

                    MannschaftInternBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/mannschaften/mannschaftinternbearbeiten.fxml", "Interne Mannschaft Bearbeiten");
                    if (controller != null) {
                        controller.initMannschaftIntern(mannschaftIntern);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    MannschaftIntern mannschaftIntern = getTableView().getItems().get(getIndex());

                    if (kannMannschaftGeloeschtWerden(mannschaftIntern)) {
                        boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", mannschaftIntern.getName() + " löschen", "Möchten Sie folgende Mannschaft wirklich löschen? - " + mannschaftIntern.getName());

                        if (bestaetigung) {
                            mannInService.delete(mannschaftIntern);
                            tabelleMannschaftIntern.getItems().remove(mannschaftIntern);
                        }
                    } else {
                        AlertUtil.alertWarning("Löschen nicht möglich", "Die Mannschaft hat noch Spieler und/oder Spiele und/oder Trainings zugeordnet", "Bitte entfernen Sie zuerst alle Zuordnungen, bevor Sie die Mannschaft löschen.");
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
                    btnHinzufuegen.setVisible(Session.istAdmin());
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

    public void mannschaftHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/mannschaften/mannschaftinternhinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage mannschaftHinzufuegen = new Stage();
            mannschaftHinzufuegen.setTitle("Mannschaft hinzufügen");
            mannschaftHinzufuegen.setScene(scene);
            mannschaftHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            mannschaftHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean kannMannschaftGeloeschtWerden(MannschaftIntern mannschaft) {
        List<Spieler> spieler = spielerService.getByMannschaft(mannschaft);
        List<Spiele> spiele = spieleService.getByMannschaftIntern(mannschaft);
        List<Training> training = trainingService.getByMannschaft(mannschaft);

        return spieler.isEmpty() && spiele.isEmpty() && training.isEmpty();
    }
}
