/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.basketrolling.beans.Elternkontakt;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.ElternkontaktDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.service.ElternkontaktService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class ElternkontaktHinzufuegenController implements Initializable {

    ElternkontaktDAO dao;
    ElternkontaktService service;

    SpielerDAO spielerDAO;
    SpielerService spielerService;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private ComboBox<Spieler> cbSpieler;

    @FXML
    private TextField tfTelefon;

    @FXML
    private TextField tfEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new ElternkontaktDAO();
        service = new ElternkontaktService(dao);

        spielerDAO = new SpielerDAO();
        spielerService = new SpielerService(spielerDAO);

        List<Spieler> spielerList = spielerService.getAll();

        if (!spielerList.isEmpty() && spielerList != null) {
            cbSpieler.setItems(FXCollections.observableArrayList(spielerList));
        } else {
            cbSpieler.setDisable(true);
        }
    }

    public void speichern() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()
                && cbSpieler.getValue() != null) {

            Elternkontakt elternkontakt = new Elternkontakt();
            elternkontakt.setVorname(tfVorname.getText());
            elternkontakt.setNachname(tfNachname.getText());
            elternkontakt.setSpieler(cbSpieler.getValue());
            elternkontakt.setTelefon(tfTelefon.getText());
            elternkontakt.setEMail(tfEmail.getText());

            service.create(elternkontakt);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText("Elternkontakt erfolgreich gespeichert!");
            alert.setContentText("Möchten Sie einen weiteren Elternkontakt anlegen?");

            ButtonType jaButton = new ButtonType("Ja");
            ButtonType neinButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(jaButton, neinButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == neinButton) {
                MenuUtil.fensterSchliessenOhneWarning(tfVorname);
            } else {
                tfVorname.clear();
                tfNachname.clear();
                cbSpieler.setValue(null);
                tfTelefon.clear();
                tfEmail.clear();
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarning(tfVorname);
    }
}
