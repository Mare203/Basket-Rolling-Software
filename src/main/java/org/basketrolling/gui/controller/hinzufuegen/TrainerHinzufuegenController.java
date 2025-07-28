/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller.hinzufuegen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.basketrolling.beans.Trainer;
import org.basketrolling.dao.TrainerDAO;
import org.basketrolling.service.TrainerService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class TrainerHinzufuegenController implements Initializable {

    TrainerDAO dao;
    TrainerService service;

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private TextField tfTelefon;

    @FXML
    private TextField tfEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new TrainerDAO();
        service = new TrainerService(dao);
    }

    public void speichern() {
        if (!tfVorname.getText().isEmpty()
                && !tfNachname.getText().isEmpty()
                && !tfTelefon.getText().isEmpty()) {

            Trainer trainer = new Trainer();
            trainer.setVorname(tfVorname.getText());
            trainer.setNachname(tfNachname.getText());
            trainer.setTelefon(tfTelefon.getText());
            trainer.setEMail(tfEmail.getText());

            service.create(trainer);

            boolean weiter = MenuUtil.fensterSchliessenMitEigenerWarnung("Speichern erfolgreich", "Trainer erfolgreich gespeichert!", "Möchten Sie einen weiteren Trainer anlegen?");

            if (!weiter) {
                MenuUtil.fensterSchliessenOhneWarnung(tfVorname);
            } else {
                tfVorname.clear();
                tfNachname.clear();
                tfTelefon.clear();
                tfEmail.clear();
            }
        } else {
            AlertUtil.alertWarning("Eingabefehler", "Unvollständige oder ungültige Eingaben", "- Alle Pflichtfelder müssen ausgefüllt sein.");
        }
    }

    public void abbrechen() {
        MenuUtil.fensterSchliessenMitWarnung(tfVorname);
    }
}
