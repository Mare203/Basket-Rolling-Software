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
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.gui.controller.bearbeiten.LigaBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 *
 * @author Marko
 */
public class LigaController implements Initializable, MainBorderSettable {

    LigaDAO ligaDao;
    LigaService ligaService;

    MannschaftInternService mannInService;
    MannschaftInternDAO mannInDao;
    MannschaftExternService mannExService;
    MannschaftExternDAO mannExDao;

    @FXML
    private TableView<Liga> tabelleLiga;

    @FXML
    private TableColumn<Liga, String> ligaSpalte;

    @FXML
    private TableColumn<Liga, Void> aktionenSpalte;

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ligaDao = new LigaDAO();
        mannInDao = new MannschaftInternDAO();
        mannExDao = new MannschaftExternDAO();

        ligaService = new LigaService(ligaDao);
        mannInService = new MannschaftInternService(mannInDao);
        mannExService = new MannschaftExternService(mannExDao);

        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));

        List<Liga> mannschaftExternList = ligaService.getAll();
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
                    Liga liga = getTableView().getItems().get(getIndex());

                    LigaBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/liga/ligabearbeiten.fxml", "Liga Bearbeiten");
                    if (controller != null) {
                        controller.initLiga(liga);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Liga liga = getTableView().getItems().get(getIndex());

                    if (kannLigaGeloeschtWerden(liga)) {
                        boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", liga.getName() + " löschen", "Möchten Sie folgende Liga wirklich löschen? - " + liga.getName());

                        if (bestaetigung) {
                            ligaService.delete(liga);
                            tabelleLiga.getItems().remove(liga);
                        }
                    } else {
                        AlertUtil.alertWarning("Löschen nicht möglich", "Die Liga ist noch mit Mannschaften verknüpft.", "Bitte entfernen Sie zuerst alle Zuordnungen, bevor Sie die Liga löschen.");
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

    public boolean kannLigaGeloeschtWerden(Liga liga) {
        List<MannschaftIntern> internTeams = mannInService.getByLiga(liga);
        List<MannschaftExtern> externTeams = mannExService.getByLiga(liga);

        return internTeams.isEmpty() && externTeams.isEmpty();
    }
}
