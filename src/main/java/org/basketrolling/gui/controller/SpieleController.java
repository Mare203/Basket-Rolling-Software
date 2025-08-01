/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Statistik;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.gui.controller.bearbeiten.SpielBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 *
 * @author Marko
 */
public class SpieleController implements Initializable, MainBorderSettable {

    SpieleDAO spieleDao;
    StatistikDAO statistikDao;

    SpieleService spieleService;
    StatistikService statistikService;

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

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spieleDao = new SpieleDAO();
        statistikDao = new StatistikDAO();

        spieleService = new SpieleService(spieleDao);
        statistikService = new StatistikService(statistikDao);

        btnHinzufuegen.setVisible(Session.istAdmin());

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

        List<Spiele> spieleListe = spieleService.getAll();
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
                    Spiele spiel = getTableView().getItems().get(getIndex());

                    SpielBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spiele/spielbearbeiten.fxml", "Spiel Bearbeiten");
                    if (controller != null) {
                        controller.initSpiel(spiel);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Spiele spiele = getTableView().getItems().get(getIndex());

                    boolean bestaetigung = AlertUtil.confirmationMitJaNein(
                            "Bestätigung", "Spiel löschen", "Möchten Sie das Spiel wirklich löschen?"
                    );

                    if (!bestaetigung) {
                        return;
                    }

                    List<Statistik> statistiken = statistikService.getBySpiel(spiele);

                    if (!statistiken.isEmpty()) {
                        boolean mitStatistikLoeschen = AlertUtil.confirmationMitJaNein("Achtung – Verknüpfte Daten",
                                "Dieses Spiel hat verknüpfte Statistik-Einträge.",
                                "Wenn Sie fortfahren, werden auch alle zugehörigen Statistiken gelöscht.\nMöchten Sie trotzdem fortfahren?");

                        if (!mitStatistikLoeschen) {
                            return;
                        }
                    }

                    try {
                        spieleService.delete(spiele);
                        tabelleSpiele.getItems().remove(spiele);
                        AlertUtil.alertConfirmation("Erfolg", "Spiel wurde erfolgreich gelöscht.");
                    } catch (Exception ex) {
                        AlertUtil.alertError("Fehler beim Löschen", "Das Spiel konnte nicht gelöscht werden.", ex.getMessage());
                        ex.printStackTrace();
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
