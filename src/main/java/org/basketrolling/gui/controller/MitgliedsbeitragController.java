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
import org.basketrolling.beans.Mitgliedsbeitrag;
import org.basketrolling.dao.MitgliedsbeitragDAO;
import org.basketrolling.gui.controller.bearbeiten.MitgliedsbeitragBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MitgliedsbeitragService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 *
 * @author Marko
 */
public class MitgliedsbeitragController implements Initializable, MainBorderSettable {

    MitgliedsbeitragDAO dao = new MitgliedsbeitragDAO();
    MitgliedsbeitragService service = new MitgliedsbeitragService(dao);

    @FXML
    private TableView<Mitgliedsbeitrag> tabelleMitgliedsbeitrag;

    @FXML
    private TableColumn<Mitgliedsbeitrag, String> saisonSpalte;

    @FXML
    private TableColumn<Mitgliedsbeitrag, Double> betragSpalte;

    @FXML
    private TableColumn<Mitgliedsbeitrag, Void> aktionenSpalte;

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saisonSpalte.setCellValueFactory(new PropertyValueFactory<>("saison"));
        betragSpalte.setCellValueFactory(new PropertyValueFactory<>("betrag"));
        betragSpalte.setCellFactory(column -> new TableCell<Mitgliedsbeitrag, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f €", value));
                }
            }
        });

        List<Mitgliedsbeitrag> MitgliedsbeitragList = service.getAll();
        tabelleMitgliedsbeitrag.setItems(FXCollections.observableArrayList(MitgliedsbeitragList));

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
                        Mitgliedsbeitrag mitgliedsbeitrag = getTableView().getItems().get(getIndex());
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
                    Mitgliedsbeitrag mitgliedsbeitrag = getTableView().getItems().get(getIndex());

                    MitgliedsbeitragBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitragbearbeiten.fxml", "Mitgliedsbeitrag Bearbeiten");
                    if (controller != null) {
                        controller.initMitgliedsbeitrag(mitgliedsbeitrag);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Mitgliedsbeitrag mitgliedsbeitrag = getTableView().getItems().get(getIndex());

                    boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", "Mitgliedsbeitrag löschen", "Möchten Sie den Mitgliedsbeitrag für die Saison " + mitgliedsbeitrag.getSaison() + " wirklich löschen?");

                    if (bestaetigung) {
                        service.delete(mitgliedsbeitrag);
                        tabelleMitgliedsbeitrag.getItems().remove(mitgliedsbeitrag);
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

    public void mitgliedsbeitragHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitraghinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage mitgliedsbeitragHinzufuegen = new Stage();
            mitgliedsbeitragHinzufuegen.setTitle("Mitgliedsbeitrag hinzufügen");
            mitgliedsbeitragHinzufuegen.setScene(scene);
            mitgliedsbeitragHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            mitgliedsbeitragHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
