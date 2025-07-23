/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.basketrolling.beans.Login;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.MitgliedsbeitragZuweisungDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.enums.Rolle;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MitgliedsbeitragZuweisungService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.MenuOeffnenUtil;
import org.basketrolling.utils.QuickUtil;

/**
 *
 * @author Marko
 */
public class HauptmenueController implements Initializable, MainBorderSettable {

    SpieleDAO spieleDao = new SpieleDAO();
    StatistikDAO statistikDao = new StatistikDAO();
    TrainingDAO trainingDao = new TrainingDAO();
    MitgliedsbeitragZuweisungDAO mitgliedsbeitragDao = new MitgliedsbeitragZuweisungDAO();
    SpieleService spieleService = new SpieleService(spieleDao);
    StatistikService statistikService = new StatistikService(statistikDao);
    TrainingService trainingService = new TrainingService(trainingDao);
    MitgliedsbeitragZuweisungService mitgliedsbeitragZuweisungService = new MitgliedsbeitragZuweisungService(mitgliedsbeitragDao);

    private Login benutzer;
    private MenuOeffnenUtil menuUtil;

    @FXML
    private Label welcomeUser;

    @FXML
    private Button logout;

    @FXML
    private Button beenden;

    @FXML
    private Button adminBtn;

    @FXML
    private ListView<Spiele> fiveSpieleHZwei;

    @FXML
    private ListView<Spiele> fiveSpieleLL;

    @FXML
    private ListView<Spiele> fiveSpieleRossau;

    @FXML
    private ListView<String> scorerList;

    @FXML
    private ListView<Training> trainingList;

    @FXML
    private ListView<MitgliedsbeitragZuweisung> beitraegeList;

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Spiele> spieleList = spieleService.getAll();
        List<Object[]> top5 = statistikService.getTop5Scorer();
        List<String> topScorerAlsString = new ArrayList<>();
        List<Training> heutigeTrainings = trainingService.getHeutigeTrainings();
        List<MitgliedsbeitragZuweisung> spielerOffeneBeitraege = mitgliedsbeitragZuweisungService.getOffeneBeitraege();
        menuUtil = new MenuOeffnenUtil(borderPane);

        Label platzhalterLL = new Label("Keine Spiele vorhanden");
        Label platzhalterH2 = new Label("Keine Spiele vorhanden");
        Label platzhalterRossau = new Label("Keine Spiele vorhanden");
        Label platzhalterScorer = new Label("Keine Daten vorhanden");
        Label platzhalterTraining = new Label("Kein Training heute");
        Label platzhalterBeitraege = new Label("Keine offenen Beträge");

        if (spieleList != null) {
            String rollingLL = "LL";
            String rollingH2 = "H2";
            String rollingRossau = "Rossau";
            List<Spiele> rollingLLSpiele = spieleList.stream().filter(s -> s.getMannschaftIntern().getName().contains(rollingLL)).toList();
            List<Spiele> rollingH2Spiele = spieleList.stream().filter(s -> s.getMannschaftIntern().getName().contains(rollingH2)).toList();
            List<Spiele> rollingRossauSpiele = spieleList.stream().filter(s -> s.getMannschaftIntern().getName().contains(rollingRossau)).toList();

            if (rollingLLSpiele.isEmpty()) {
                fiveSpieleLL.setItems(FXCollections.observableArrayList());
                platzhalterLL.getStyleClass().add("platzhalter");
                fiveSpieleLL.setPlaceholder(platzhalterLL);
            } else {
                fiveSpieleLL.setItems(FXCollections.observableArrayList(rollingLLSpiele));
            }

            if (rollingH2Spiele.isEmpty()) {
                fiveSpieleHZwei.setItems(FXCollections.observableArrayList());
                platzhalterH2.getStyleClass().add("platzhalter");
                fiveSpieleHZwei.setPlaceholder(platzhalterH2);
            } else {
                fiveSpieleHZwei.setItems(FXCollections.observableArrayList(rollingH2Spiele));
            }

            if (rollingRossauSpiele.isEmpty()) {
                fiveSpieleRossau.setItems(FXCollections.observableArrayList());
                platzhalterRossau.getStyleClass().add("platzhalter");
                fiveSpieleRossau.setPlaceholder(platzhalterRossau);
            } else {
                fiveSpieleRossau.setItems(FXCollections.observableArrayList(rollingRossauSpiele));
            }
        }

        for (Object[] objekt : top5) {
            Spieler spieler = (Spieler) objekt[0];
            Double ppg = (Double) objekt[1];
            Long anzahlSpiele = (Long) objekt[2];

            String spielerString = spieler.getVorname() + " " + spieler.getNachname() + " | " + ppg + " PPG in " + anzahlSpiele + " Spielen | " + spieler.getMannschaftIntern().getName();
            topScorerAlsString.add(spielerString);
        }
        if (topScorerAlsString.isEmpty()) {
            scorerList.setItems(FXCollections.observableArrayList());
            platzhalterScorer.getStyleClass().add("platzhalter");
            scorerList.setPlaceholder(platzhalterScorer);
        } else {
            scorerList.setItems(FXCollections.observableArrayList(topScorerAlsString));
        }

        if (heutigeTrainings.isEmpty()) {
            trainingList.setItems(FXCollections.observableArrayList());
            platzhalterTraining.getStyleClass().add("platzhalter");
            trainingList.setPlaceholder(platzhalterTraining);
        } else {
            trainingList.setItems(FXCollections.observableArrayList(heutigeTrainings));
        }

        if (spielerOffeneBeitraege.isEmpty()) {
            beitraegeList.setItems(FXCollections.observableArrayList());
            platzhalterBeitraege.getStyleClass().add("platzhalter");
            beitraegeList.setPlaceholder(platzhalterBeitraege);
        } else {
            beitraegeList.setItems(FXCollections.observableArrayList(spielerOffeneBeitraege));
        }
    }

    public void setMainBorder(BorderPane mainBorderPane) {
        this.borderPane = mainBorderPane;
    }

    public void initUser(Login benutzer) {
        this.benutzer = benutzer;
        welcomeUser.setText("Willkommen, " + benutzer.getVorname() + "!");

        if (benutzer.getRolle() != Rolle.ADMIN) {
            adminBtn.setDisable(true);
            adminBtn.setVisible(false);
        }
    }

    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bestätigung");
        alert.setHeaderText("Logout bestätigen");
        alert.setContentText("Sind Sie sicher, dass Sie sich abmelden möchten?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/login/login.fxml"));
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(getClass().getResource("/org/basketrolling/gui/css/styles.css").toExternalForm());

                Stage loginStage = new Stage();
                loginStage.setTitle("Login - Basket Rolling");
                loginStage.setScene(scene);
                loginStage.show();

                Stage hauptmenuStage = (Stage) logout.getScene().getWindow();
                hauptmenuStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void beenden() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bestätigung");
        alert.setHeaderText("Programm schließen");
        alert.setContentText("Sind Sie sicher, dass Sie das Programm beenden möchten?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) beenden.getScene().getWindow();
            stage.close();
        }
    }

    public void spielerMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/spieler/spielermenue.fxml");
    }

    public void trainerMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/trainer/trainermenue.fxml");
    }

    public void mannschaftInternMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/mannschaften/mannschaftinternmenu.fxml");
    }

    public void mannschaftExternMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/mannschaften/mannschaftexternmenu.fxml");
    }

    public void hallenMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/halle/hallenmenu.fxml");
    }

    public void trainingMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/training/trainingmenu.fxml");
    }

    public void mitgliedsbeitragMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitragmenu.fxml");
    }

    public void adminMenu() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/login/adminmenu.fxml");
    }

    public void ligaMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/liga/ligamenu.fxml");
    }

    public void spieleMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/spiele/spielemenu.fxml");
    }

    public void elternkontaktMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/elternkontakte/elternkontaktemenu.fxml");
    }
    
    public void statistikMenuOeffnen() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/statistik/statistikmenu.fxml");
    }

    public void backToHauptmenu() {
        menuUtil.MenuOeffnen("/org/basketrolling/gui/fxml/hauptmenu/hauptmenuecenter.fxml");

    }

    public void account() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/login/accountmenu.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/org/basketrolling/gui/css/styles.css").toExternalForm());

            AccountmenuController controller = loader.getController();
            controller.initLogin(benutzer);

            Stage account = new Stage();
            account.setTitle("Account");
            account.setScene(scene);
            account.initModality(Modality.APPLICATION_MODAL);
            account.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void quickSpielerHinzufuegen() {
        QuickUtil.quickHinzufuegen("/org/basketrolling/gui/fxml/spieler/spielerhinzufuegen.fxml", "Spieler hinzufügen");
    }
}
