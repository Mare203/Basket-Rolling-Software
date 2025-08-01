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
import org.basketrolling.utils.BildUtil;
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

        btnHinzufuegen.setVisible(Session.istAdmin());

        ligaSpalte.setCellValueFactory(new PropertyValueFactory<>("name"));

        List<Liga> mannschaftExternList = ligaService.getAll();
        tabelleLiga.setItems(FXCollections.observableArrayList(mannschaftExternList));

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
                }
            }
        });
    }

    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    public void ligaHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/liga/ligahinzufuegen.fxml", "Liga hinzufügen");
    }

    public boolean kannLigaGeloeschtWerden(Liga liga) {
        List<MannschaftIntern> internTeams = mannInService.getByLiga(liga);
        List<MannschaftExtern> externTeams = mannExService.getByLiga(liga);

        return internTeams.isEmpty() && externTeams.isEmpty();
    }
}
