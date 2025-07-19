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
import javafx.scene.Parent;
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
import org.basketrolling.service.MitgliedsbeitragZuweisungService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.service.TrainingService;

/**
 *
 * @author Marko
 */
public class HauptmenueController implements Initializable {

    SpieleDAO spieleDao = new SpieleDAO();
    StatistikDAO statistikDao = new StatistikDAO();
    TrainingDAO trainingDao = new TrainingDAO();
    MitgliedsbeitragZuweisungDAO mitgliedsbeitragDao = new MitgliedsbeitragZuweisungDAO();
    SpieleService spieleService = new SpieleService(spieleDao);
    StatistikService statistikService = new StatistikService(statistikDao);
    TrainingService trainingService = new TrainingService(trainingDao);
    MitgliedsbeitragZuweisungService mitgliedsbeitragZuweisungService = new MitgliedsbeitragZuweisungService(mitgliedsbeitragDao);

    private Login benutzer;

    @FXML
    private Label welcomeUser;

    @FXML
    private Button logout;

    @FXML
    private Button beenden;

    @FXML
    private Button spielerBtn;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spielermenue.fxml"));
            Parent spielerMenu = loader.load();

            SpielermenueController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(spielerMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trainerMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/trainer/trainermenue.fxml"));
            Parent trainerMenu = loader.load();

            TrainermenueController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(trainerMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mannschaftInternMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/mannschaften/mannschaftinternmenu.fxml"));
            Parent mannschaftInternMenu = loader.load();

            MannschaftInternmenuController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(mannschaftInternMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mannschaftExternMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/mannschaften/mannschaftexternmenu.fxml"));
            Parent mannschaftExternMenu = loader.load();

            MannschaftExternmenuController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(mannschaftExternMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hallenMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/halle/hallenmenu.fxml"));
            Parent hallenMenu = loader.load();

            HallenmenuController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(hallenMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trainingMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/training/trainingmenu.fxml"));
            Parent trainingMenu = loader.load();

            TrainingController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(trainingMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mitgliedsbeitragMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitragmenu.fxml"));
            Parent mitgliedsbeitragMenu = loader.load();

            MitgliedsbeitragController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(mitgliedsbeitragMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adminMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/login/adminmenu.fxml"));
            Parent adminMenu = loader.load();

            AdminController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(adminMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ligaMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/liga/ligamenu.fxml"));
            Parent ligaMenu = loader.load();

            LigaController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(ligaMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void spieleMenuOeffnen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spiele/spielemenu.fxml"));
            Parent spieleMenu = loader.load();

            SpieleController controller = loader.getController();
            controller.setMainBorder(borderPane);

            borderPane.setCenter(spieleMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quickSpielerHinzufuegen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/basketrolling/gui/fxml/spieler/spielerhinzufuegen.fxml"));
            Scene scene = new Scene(loader.load());
            
            Stage spielerHinzufuegen = new Stage();
            spielerHinzufuegen.setTitle("Spieler hinzufügen");
            spielerHinzufuegen.setScene(scene);
            spielerHinzufuegen.initModality(Modality.APPLICATION_MODAL);
            spielerHinzufuegen.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
