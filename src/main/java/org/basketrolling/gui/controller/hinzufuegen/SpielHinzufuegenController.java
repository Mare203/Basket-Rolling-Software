/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.basketrolling.beans.Halle;
import org.basketrolling.beans.Liga;
import org.basketrolling.beans.MannschaftExtern;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.dao.HalleDAO;
import org.basketrolling.dao.LigaDAO;
import org.basketrolling.dao.MannschaftExternDAO;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.service.HalleService;
import org.basketrolling.service.LigaService;
import org.basketrolling.service.MannschaftExternService;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.utils.AlertUtil;

/**
 *
 * @author Marko
 */
public class SpielHinzufuegenController implements Initializable {

    SpieleDAO spielDao;
    SpieleService spielService;

    LigaDAO ligaDao;
    LigaService ligaService;

    HalleDAO halleDao;
    HalleService halleService;

    MannschaftInternDAO mannschaftInternDAO;
    MannschaftInternService mannschaftInternService;

    MannschaftExternDAO mannschaftExternDAO;
    MannschaftExternService mannschaftExternService;

    @FXML
    private ComboBox<Liga> cbLiga;

    @FXML
    private DatePicker dpDatum;

    @FXML
    private ComboBox<Halle> cbHalle;

    @FXML
    private ComboBox<MannschaftIntern> cbMannschaftIntern;

    @FXML
    private ComboBox<MannschaftExtern> cbMannschaftExtern;

    @FXML
    private TextField tfPunkteIntern;

    @FXML
    private TextField tfPunkteExtern;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spielDao = new SpieleDAO();
        ligaDao = new LigaDAO();
        halleDao = new HalleDAO();
        mannschaftInternDAO = new MannschaftInternDAO();
        mannschaftExternDAO = new MannschaftExternDAO();

        spielService = new SpieleService(spielDao);
        ligaService = new LigaService(ligaDao);
        halleService = new HalleService(halleDao);
        mannschaftInternService = new MannschaftInternService(mannschaftInternDAO);
        mannschaftExternService = new MannschaftExternService(mannschaftExternDAO);

        List<Liga> liga = ligaService.getAll();
        List<Halle> halle = halleService.getAll();

        cbLiga.setItems(FXCollections.observableArrayList(liga));
        cbMannschaftIntern.setDisable(true);
        cbMannschaftExtern.setDisable(true);

        cbLiga.valueProperty().addListener((obs, ligaAlt, ligaNeu) -> {
            boolean ligaVorhanden = ligaNeu != null;
            cbMannschaftIntern.setDisable(!ligaVorhanden);
            cbMannschaftExtern.setDisable(!ligaVorhanden);

            if (ligaNeu != null) {
                List<MannschaftIntern> mannschaftIntern = mannschaftInternService.getByLiga(ligaNeu);
                List<MannschaftExtern> mannschaftExtern = mannschaftExternService.getByLiga(ligaNeu);

                cbMannschaftIntern.setItems(FXCollections.observableArrayList(mannschaftIntern));
                cbMannschaftExtern.setItems(FXCollections.observableArrayList(mannschaftExtern));
            } else {
                cbMannschaftIntern.getItems().clear();
                cbMannschaftExtern.getItems().clear();
            }
        });
        cbHalle.setItems(FXCollections.observableArrayList(halle));
    }

    public void speichern() {
        if (cbLiga.getValue() != null
                && cbHalle.getValue() != null
                && cbMannschaftIntern.getValue() != null
                && cbMannschaftExtern.getValue() != null
                && !tfPunkteIntern.getText().isEmpty()
                && !tfPunkteExtern.getText().isEmpty()) {
            Spiele spiel = new Spiele();
            spiel.setLiga(cbLiga.getValue());
            spiel.setDatum(dpDatum.getValue());
            spiel.setHalle(cbHalle.getValue());
            spiel.setMannschaftIntern(cbMannschaftIntern.getValue());
            spiel.setMannschaftExtern(cbMannschaftExtern.getValue());
            spiel.setInternPunkte(Integer.parseInt(tfPunkteIntern.getText()));
            spiel.setExternPunkte(Integer.parseInt(tfPunkteExtern.getText()));

            Spiele erstelltesSpiel = spielService.create(spiel);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Spiel erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie eine Statistik für dieses Spiel anlegen?");

            ButtonType jaButton = new ButtonType("Ja");
            ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(jaButton, neinButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == neinButton) {
                Stage stage = (Stage) cbHalle.getScene().getWindow();
                stage.close();
            } else {
                oeffneStatistik(erstelltesSpiel);
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    private void oeffneStatistik(Spiele spiel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/statistik/statistikhinzufuegen.fxml"));
            Parent root = loader.load();

            StatistikHinzufuegenController controller = loader.getController();
            controller.setSpiel(spiel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Spielerstatistik erfassen");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            Stage currentStage = (Stage) cbHalle.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
