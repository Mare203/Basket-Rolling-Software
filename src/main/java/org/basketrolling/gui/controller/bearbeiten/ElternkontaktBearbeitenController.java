/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.bearbeiten;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

/**
 *
 * @author Marko
 */
public class ElternkontaktBearbeitenController implements Initializable {

    Elternkontakt bearbeitenElternkontakt;

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

    public void initElternkontakt(Elternkontakt elternkontakt) {
        this.bearbeitenElternkontakt = elternkontakt;

        tfVorname.setText(elternkontakt.getVorname());
        tfNachname.setText(elternkontakt.getNachname());
        cbSpieler.setValue(elternkontakt.getSpieler());
        tfTelefon.setText(String.valueOf(elternkontakt.getTelefon()));
        tfEmail.setText(elternkontakt.getEMail());
    }

    public void aktualisieren() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()
                && cbSpieler.getValue() != null) {

            bearbeitenElternkontakt.setVorname(tfVorname.getText());
            bearbeitenElternkontakt.setNachname(tfNachname.getText());
            bearbeitenElternkontakt.setSpieler(cbSpieler.getValue());
            bearbeitenElternkontakt.setTelefon(tfTelefon.getText());
            bearbeitenElternkontakt.setEMail(tfEmail.getText());

            service.update(bearbeitenElternkontakt);

            AlertUtil.alertConfirmation("Speichern erfolgreich", "Elternkontakt erfolgreich aktualisiert!");

            Stage stage = (Stage) tfVorname.getScene().getWindow();
            stage.close();

        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }
}
