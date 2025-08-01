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
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LoginService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.BildUtil;
import org.basketrolling.utils.MenuUtil;

/**
 *
 * @author Marko
 */
public class UserController implements Initializable, MainBorderSettable {

    LoginDAO dao = new LoginDAO();
    LoginService service = new LoginService(dao);

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Login> tabelleLogin;

    @FXML
    private TableColumn<Login, String> vornameSpalte;

    @FXML
    private TableColumn<Login, String> nachnameSpalte;

    @FXML
    private TableColumn<Login, String> benutzernameSpalte;

    @FXML
    private TableColumn<Login, String> rolleSpalte;

    @FXML
    private TableColumn<Login, Void> aktionenSpalte;

    private BorderPane mainBorderPane;

    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vornameSpalte.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameSpalte.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        benutzernameSpalte.setCellValueFactory(new PropertyValueFactory<>("benutzername"));
        rolleSpalte.setCellValueFactory(new PropertyValueFactory<>("rolle"));

        buttonsHinzufuegen();

        List<Login> spielerListe = service.getAll();
        tabelleLogin.setItems(FXCollections.observableArrayList(spielerListe));

    }

    private void buttonsHinzufuegen() {
        aktionenSpalte.setCellFactory(spalte -> new TableCell<>() {

            private final Button loeschenBtn = new Button();

            private final HBox buttonBox = new HBox(15, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                loeschenBtn.setGraphic(BildUtil.ladeBildSmall("/org/basketrolling/gui/images/delete.png"));
                loeschenBtn.getStyleClass().add("icon-btn");

                loeschenBtn.setOnAction(e -> {
                    Login login = getTableView().getItems().get(getIndex());

                    boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", "Training löschen", "Möchten Sie das Training wirklich löschen?");

                    if (bestaetigung) {
                        service.delete(login);
                        tabelleLogin.getItems().remove(login);
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
                }
            }
        });
    }

    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    public void userHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/login/userhinzufuegen.fxml", "User hinzufügen");
    }
}
