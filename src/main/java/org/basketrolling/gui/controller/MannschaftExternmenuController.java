/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.gui.controller.bearbeiten.MannschaftExternBearbeitenController;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;
import org.basketrolling.utils.Session;

/**
 *
 * @author Marko
 */
public class MannschaftExternmenuController implements Initializable, MainBorderSettable {

    MannschaftExternDAO mannExDao;
    SpieleDAO spieleDao;

    MannschaftExternService mannExService;
    SpieleService spieleService;

    @FXML
    private TableView<MannschaftExtern> tabelleMannschaftExtern;

    @FXML
    private TableColumn<MannschaftExtern, String> nameSpalte;

    @FXML
    private TableColumn<MannschaftExtern, String> ligaSpalte;

    @FXML
    private TableColumn<MannschaftExtern, Void> aktionenSpalte;

    @FXML
    private Button btnHinzufuegen;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mannExDao = new MannschaftExternDAO();
        spieleDao = new SpieleDAO();

        mannExService = new MannschaftExternService(mannExDao);
        spieleService = new SpieleService(spieleDao);

        btnHinzufuegen.setVisible(Session.istAdmin());

        nameSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));
        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("liga"));

        List<MannschaftExtern> mannschaftExternList = mannExService.getAll();
        tabelleMannschaftExtern.setItems(FXCollections.observableArrayList(mannschaftExternList));

        buttonsHinzufuegen();
    }

    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();

            private final HBox buttonBox = new HBox(15, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                bearbeitenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));

                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                bearbeitenBtn.setOnAction(e -> {
                    MannschaftExtern mannschaftExtern = getTableView().getItems().get(getIndex());

                    MannschaftExternBearbeitenController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/mannschaften/mannschaftexternbearbeiten.fxml", "Externe Mannschaft Bearbeiten");
                    if (controller != null) {
                        controller.initMannschaftExtern(mannschaftExtern);
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    MannschaftExtern mannschaftExtern = getTableView().getItems().get(getIndex());

                    if (kannMannschaftGeloeschtWerden(mannschaftExtern)) {
                        boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", mannschaftExtern.getName() + " löschen", "Möchten Sie folgende Mannschaft wirklich löschen? - " + mannschaftExtern.getName());

                        if (bestaetigung) {
                            mannExService.delete(mannschaftExtern);
                            tabelleMannschaftExtern.getItems().remove(mannschaftExtern);
                        }
                    } else {
                        AlertUtil.alertWarning("Löschen nicht möglich", "Die Mannschaft hat noch Spiele zugeordnet", "Bitte entfernen Sie zuerst alle Zuordnungen, bevor Sie die Mannschaft löschen.");
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

    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    public void mannschaftHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/mannschaften/mannschaftexternhinzufuegen.fxml", "Mannschaft hinzufügen");
    }

    public boolean kannMannschaftGeloeschtWerden(MannschaftExtern mannschaft) {
        List<Spiele> spiele = spieleService.getByMannschaftExtern(mannschaft);

        return spiele.isEmpty();
    }

}
