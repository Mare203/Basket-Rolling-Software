/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
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
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;
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
    private MenuUtil menuUtil;
    public static final String DEFAULT_CSS = "/org/basketrolling/gui/css/styles.css";

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
        menuUtil = new MenuUtil(borderPane);

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

            List<Spiele> rollingLLSpiele = spieleList.stream()
                    .filter(s -> s.getMannschaftIntern().getName().contains(rollingLL))
                    .sorted(Comparator.comparing(Spiele::getDatum).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            List<Spiele> rollingH2Spiele = spieleList.stream()
                    .filter(s -> s.getMannschaftIntern().getName().contains(rollingH2))
                    .sorted(Comparator.comparing(Spiele::getDatum).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            List<Spiele> rollingRossauSpiele = spieleList.stream()
                    .filter(s -> s.getMannschaftIntern().getName().contains(rollingRossau))
                    .sorted(Comparator.comparing(Spiele::getDatum).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

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

            String spielerString = spieler.getVorname() + " " + spieler.getNachname() + " | " + String.format("%.1f", ppg) + " PPG in " + anzahlSpiele + " Spielen | " + spieler.getMannschaftIntern().getName();
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
        boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", "Logout bestätigen", "Sind Sie sicher, dass Sie sich abmelden möchten?");

        if (bestaetigung) {
            MenuUtil.neuesFensterAnzeigen("/org/basketrolling/gui/fxml/login/login.fxml", "Login - Basket Rolling", DEFAULT_CSS);
        }
    }

    public void beenden() {
        boolean beenden = AlertUtil.confirmationMitJaNein("Bestätigung", "Programm schließen", "Sind Sie sicher, dass Sie das Programm beenden möchten?");

        if (beenden) {
            MenuUtil.fensterSchliessenOhneWarnung(logout);
        }
    }

    public void spielerMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/spieler/spielermenue.fxml");
    }

    public void trainerMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/trainer/trainermenue.fxml");
    }

    public void mannschaftInternMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/mannschaften/mannschaftinternmenu.fxml");
    }

    public void mannschaftExternMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/mannschaften/mannschaftexternmenu.fxml");
    }

    public void hallenMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/halle/hallenmenu.fxml");
    }

    public void trainingMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/training/trainingmenu.fxml");
    }

    public void mitgliedsbeitragMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitragmenu.fxml");
    }

    public void adminMenu() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/login/adminmenu.fxml");
    }

    public void ligaMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/liga/ligamenu.fxml");
    }

    public void spieleMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/spiele/spielemenu.fxml");
    }

    public void elternkontaktMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/elternkontakte/elternkontaktemenu.fxml");
    }

    public void statistikMenuOeffnen() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/statistik/statistikmenu.fxml");
    }

    public void backToHauptmenu() {
        menuUtil.menuOeffnen("/org/basketrolling/gui/fxml/hauptmenu/hauptmenuecenter.fxml");

    }

    public void account() {
        AccountmenuController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/login/accountmenu.fxml", "Account", DEFAULT_CSS);
        if (controller != null) {
            controller.initLogin(benutzer);
        }
    }

    public void quickSpielerHinzufuegen() {
        QuickUtil.quickHinzufuegen("/org/basketrolling/gui/fxml/spieler/spielerhinzufuegen.fxml", "Spieler hinzufügen");
    }
}
