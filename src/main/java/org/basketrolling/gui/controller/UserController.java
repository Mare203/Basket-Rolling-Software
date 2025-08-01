/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import org.basketrolling.beans.Login;
import org.basketrolling.dao.LoginDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.LoginService;

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

            private final Button bearbeitenBtn = new Button();
            private final Button loeschenBtn = new Button();

            private final HBox buttonBox = new HBox(15, bearbeitenBtn, loeschenBtn);

            {
                buttonBox.setAlignment(Pos.CENTER_LEFT);

                bearbeitenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/edit.png"));
                loeschenBtn.setGraphic(ladeBild("/org/basketrolling/gui/images/delete.png"));

                bearbeitenBtn.getStyleClass().add("icon-btn");
                loeschenBtn.getStyleClass().add("icon-btn");

                bearbeitenBtn.setOnAction(e -> {
                    try {
                        Login login = getTableView().getItems().get(getIndex());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spielerbearbeiten.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage spielerBearbeiten = new Stage();
                        spielerBearbeiten.setTitle("User Bearbeiten");
                        spielerBearbeiten.setScene(scene);
                        spielerBearbeiten.initModality(Modality.APPLICATION_MODAL);
                        spielerBearbeiten.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                loeschenBtn.setOnAction(e -> {
                    Login login = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Bestätigung");
                    alert.setHeaderText("User löschen");
                    alert.setContentText("Möchten Sie den User " + login.getVorname() + " " + login.getNachname() + " / " + login.getBenutzername() + " wirklich löschen?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
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

    public void userHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/login/userhinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());

            Stage halle = new Stage();
            halle.setTitle("User hinzufügen");
            halle.setScene(scene);
            halle.initModality(Modality.APPLICATION_MODAL);
            halle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
