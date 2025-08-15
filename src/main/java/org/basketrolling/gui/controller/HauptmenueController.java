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
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.MitgliedsbeitragZuweisung;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.beans.Training;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.MitgliedsbeitragZuweisungDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.dao.TrainingDAO;
import org.basketrolling.enums.Rolle;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.MitgliedsbeitragZuweisungService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.service.TrainingService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller-Klasse für das Hauptmenü der Anwendung.
 * <p>
 * Diese Klasse steuert die Anzeige von wichtigen Übersichten wie bevorstehenden
 * Spielen, Top-Scorern, heutigen Trainings und offenen Mitgliedsbeiträgen.
 * Zudem bietet sie Navigationselemente zu allen Untermenüs und
 * Schnellzugriffsfunktionen für Administratoren.
 * </p>
 *
 * <p>
 * <b>Hauptfunktionen:</b></p>
 * <ul>
 * <li>Lädt und filtert die letzten Spiele der Mannschaften (Max. 3 Teams)</li>
 * <li>Zeigt die Top-5-Scorer mit PPG und Anzahl der Spiele</li>
 * <li>Listet die heutigen Trainingseinheiten</li>
 * <li>Listet offene Mitgliedsbeiträge</li>
 * <li>Steuert die Sichtbarkeit von Admin-spezifischen Buttons</li>
 * <li>Bietet Navigationsmethoden zu allen Untermenüs</li>
 * </ul>
 *
 * <p>
 * Die Controller-Methoden nutzen Service-Klassen für Datenabfragen und binden
 * die Ergebnisse an JavaFX-UI-Elemente.</p>
 *
 * @author Marko
 */
public class HauptmenueController implements Initializable, MainBorderSettable {

    SpieleDAO spieleDao;
    StatistikDAO statistikDao;
    TrainingDAO trainingDao;
    MitgliedsbeitragZuweisungDAO mitgliedsbeitragDao;
    MannschaftInternDAO mannInDao;

    SpieleService spieleService;
    StatistikService statistikService;
    TrainingService trainingService;
    MitgliedsbeitragZuweisungService mitgliedsbeitragZuweisungService;
    MannschaftInternService mannInService;

    private Login benutzer;
    public static final String DEFAULT_CSS = "/org/basketrolling/gui/css/styles.css";

    @FXML
    private Label welcomeUser;

    @FXML
    private Label lbSpiele1;

    @FXML
    private Label lbSpiele2;

    @FXML
    private Label lbSpiele3;

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

    @FXML
    private Button spielerHinzu;

    @FXML
    private Button trainingHinzu;

    @FXML
    private Button spielHinzu;

    @FXML
    private Button trainerHinzu;

    @FXML
    private Button mannschaftEHinzu;

    @FXML
    private Button mannschaftIHinzu;

    @FXML
    private Button mitgliedsbeitragHinzu;

    @FXML
    private Button halleHinzu;

    /**
     * Initialisiert das Hauptmenü.
     * <p>
     * Lädt die Spieldaten, Top-Scorer, heutige Trainings und offenen
     * Mitgliedsbeiträge, bereitet diese auf und zeigt sie in den
     * Listenansichten an. Platzhalter-Labels werden gesetzt, wenn keine Daten
     * vorhanden sind.
     * </p>
     *
     * @param url wird von JavaFX übergeben (nicht verwendet)
     * @param rb wird von JavaFX übergeben (nicht verwendet)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spieleDao = new SpieleDAO();
        statistikDao = new StatistikDAO();
        trainingDao = new TrainingDAO();
        mitgliedsbeitragDao = new MitgliedsbeitragZuweisungDAO();
        mannInDao = new MannschaftInternDAO();

        spieleService = new SpieleService(spieleDao);
        statistikService = new StatistikService(statistikDao);
        trainingService = new TrainingService(trainingDao);
        mitgliedsbeitragZuweisungService = new MitgliedsbeitragZuweisungService(mitgliedsbeitragDao);
        mannInService = new MannschaftInternService(mannInDao);

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
            String rolling1 = "/1";
            String rolling2 = "/2";
            String rolling3 = "/3";

            List<Spiele> rolling1Spiele = spieleList.stream()
                    .filter(s -> s.getMannschaftIntern().getName().contains(rolling1))
                    .sorted(Comparator.comparing(Spiele::getDatum).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            List<Spiele> rolling2Spiele = spieleList.stream()
                    .filter(s -> s.getMannschaftIntern().getName().contains(rolling2))
                    .sorted(Comparator.comparing(Spiele::getDatum).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            List<Spiele> rolling3Spiele = spieleList.stream()
                    .filter(s -> s.getMannschaftIntern().getName().contains(rolling3))
                    .sorted(Comparator.comparing(Spiele::getDatum).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            List<MannschaftIntern> mannschaften = mannInService.getAll();
            lbSpiele1.setText("Letzte 5 Spiele - " + mannschaften.stream().filter(m -> m.getName().contains("/1")).findFirst().map(MannschaftIntern::getName).orElse("Keine Mannschaft gefunden/1"));
            lbSpiele2.setText("Letzte 5 Spiele - " + mannschaften.stream().filter(m -> m.getName().contains("/2")).findFirst().map(MannschaftIntern::getName).orElse("Keine Mannschaft gefunden/2"));
            lbSpiele3.setText("Letzte 5 Spiele - " + mannschaften.stream().filter(m -> m.getName().contains("/3")).findFirst().map(MannschaftIntern::getName).orElse("Keine Mannschaft gefunden/3"));

            if (rolling1Spiele.isEmpty()) {
                fiveSpieleLL.setItems(FXCollections.observableArrayList());
                platzhalterLL.getStyleClass().add("platzhalter");
                fiveSpieleLL.setPlaceholder(platzhalterLL);
            } else {
                fiveSpieleLL.setItems(FXCollections.observableArrayList(rolling1Spiele));
            }

            if (rolling2Spiele.isEmpty()) {
                fiveSpieleHZwei.setItems(FXCollections.observableArrayList());
                platzhalterH2.getStyleClass().add("platzhalter");
                fiveSpieleHZwei.setPlaceholder(platzhalterH2);
            } else {
                fiveSpieleHZwei.setItems(FXCollections.observableArrayList(rolling2Spiele));
            }

            if (rolling3Spiele.isEmpty()) {
                fiveSpieleRossau.setItems(FXCollections.observableArrayList());
                platzhalterRossau.getStyleClass().add("platzhalter");
                fiveSpieleRossau.setPlaceholder(platzhalterRossau);
            } else {
                fiveSpieleRossau.setItems(FXCollections.observableArrayList(rolling3Spiele));
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

    /**
     * Setzt das Haupt-{@link BorderPane} für diesen Controller.
     *
     * @param mainBorderPane das zentrale {@link BorderPane} der Anwendung
     */
    public void setMainBorder(BorderPane mainBorderPane) {
        this.borderPane = mainBorderPane;
    }

    /**
     * Initialisiert den eingeloggten Benutzer und passt die Sichtbarkeit von
     * Admin-Elementen an.
     *
     * @param benutzer das {@link Login}-Objekt des angemeldeten Benutzers
     */
    public void initUser(Login benutzer) {
        this.benutzer = benutzer;
        welcomeUser.setText("Willkommen, " + benutzer.getVorname() + "!");

        if (benutzer.getRolle() != Rolle.ADMIN) {
            if (adminBtn != null) {
                adminBtn.setDisable(true);
                adminBtn.setVisible(false);
            }
            spielerHinzu.setDisable(true);
            trainingHinzu.setDisable(true);
            spielHinzu.setDisable(true);
            trainerHinzu.setDisable(true);
            mannschaftEHinzu.setDisable(true);
            mannschaftIHinzu.setDisable(true);
            mitgliedsbeitragHinzu.setDisable(true);
            halleHinzu.setDisable(true);
        }
    }

    /**
     * Meldet den Benutzer nach Bestätigung ab und öffnet den Login-Bildschirm.
     */
    public void logout() {
        boolean bestaetigung = AlertUtil.confirmationMitJaNein("Bestätigung", "Logout bestätigen", "Sind Sie sicher, dass Sie sich abmelden möchten?");

        if (bestaetigung) {
            MenuUtil.neuesFensterAnzeigen("/org/basketrolling/gui/fxml/login/login.fxml", "Login - Basket Rolling", DEFAULT_CSS);
            MenuUtil.fensterSchliessenOhneWarnung(logout);
        }
    }

    /**
     * Beendet das Programm nach Bestätigung.
     */
    public void beenden() {
        boolean beenden = AlertUtil.confirmationMitJaNein("Bestätigung", "Programm schließen", "Sind Sie sicher, dass Sie das Programm beenden möchten?");

        if (beenden) {
            MenuUtil.fensterSchliessenOhneWarnung(this.beenden);
        }
    }

    /**
     * Öffnet das Spielermenü.
     */
    public void spielerMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/spieler/spielermenue.fxml");
    }

    /**
     * Öffnet das Trainermenü.
     */
    public void trainerMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/trainer/trainermenue.fxml");
    }

    /**
     * Öffnet das Menü für interne Mannschaften.
     */
    public void mannschaftInternMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/mannschaften/mannschaftinternmenu.fxml");
    }

    /**
     * Öffnet das Menü für externe Mannschaften.
     */
    public void mannschaftExternMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/mannschaften/mannschaftexternmenu.fxml");
    }

    /**
     * Öffnet das Hallenmenü.
     */
    public void hallenMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/halle/hallenmenu.fxml");
    }

    /**
     * Öffnet das Trainingsmenü.
     */
    public void trainingMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/training/trainingmenu.fxml");
    }

    /**
     * Öffnet das Mitgliedsbeitragsmenü.
     */
    public void mitgliedsbeitragMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitragmenu.fxml");
    }

    /**
     * Öffnet das Benutzermenü.
     */
    public void userMenu() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/login/usermenu.fxml");
    }

    /**
     * Öffnet das Ligamenü.
     */
    public void ligaMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/liga/ligamenu.fxml");
    }

    /**
     * Öffnet das Spielemenü.
     */
    public void spieleMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/spiele/spielemenu.fxml");
    }

    /**
     * Öffnet das Elternkontaktmenü.
     */
    public void elternkontaktMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/elternkontakte/elternkontaktemenu.fxml");
    }

    /**
     * Öffnet das Statistikmenü.
     */
    public void statistikMenuOeffnen() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/statistik/statistikmenu.fxml");
    }

    /**
     * Lädt die Hauptmenüansicht neu.
     */
    public void backToHauptmenu() {
        MenuUtil.ladeMenuUndSetzeCenter(borderPane, "/org/basketrolling/gui/fxml/hauptmenu/hauptmenuecenter.fxml");

    }

    /**
     * Öffnet das Accountmenü.
     */
    public void account() {
        AccountmenuController controller = MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/login/accountmenu.fxml", "Account", DEFAULT_CSS);
        if (controller != null) {
            controller.initLogin(benutzer);
        }
    }

    /**
     * Öffnet das Fenster zum Hinzufügen eines neuen Spielers.
     */
    public void quickSpielerHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spieler/spielerhinzufuegen.fxml", "Spieler hinzufügen");
    }

    /**
     * Öffnet das Fenster zum Hinzufügen eines neuen Trainings.
     */
    public void quickTrainingAnlegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/training/traininghinzufuegen.fxml", "Training hinzufügen");
    }

    /**
     * Öffnet das Fenster zum Hinzufügen eines neuen Spiels.
     */
    public void quickSpielHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/spiele/spielhinzufuegen.fxml", "Spiel hinzufügen");
    }

    /**
     * Öffnet das Fenster zum Hinzufügen eines neuen Trainers.
     */
    public void quickTrainerHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/trainer/trainerhinzufuegen.fxml", "Trainer hinzufügen");
    }

    /**
     * Öffnet das Fenster zum Hinzufügen einer externen Mannschaft.
     */
    public void quickMannschaftExternHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/mannschaften/mannschaftexternhinzufuegen.fxml", "Mannschaft Extern hinzufügen");
    }

    /**
     * Öffnet das Fenster zum Hinzufügen einer internen Mannschaft.
     */
    public void quickMannschaftInternHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/mannschaften/mannschaftinternhinzufuegen.fxml", "Mannschaft Intern hinzufügen");
    }

    /**
     * Öffnet das Fenster zum Hinzufügen eines Mitgliedsbeitrags.
     */
    public void quickMitgliedsbeitragHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/mitgliedsbeitrag/mitgliedsbeitraghinzufuegen.fxml", "Mitgliedsbeitrag hinzufügen");
    }

    /**
     * Öffnet das Fenster zum Hinzufügen einer Halle.
     */
    public void quickHalleHinzufuegen() {
        MenuUtil.neuesFensterModalAnzeigen("/org/basketrolling/gui/fxml/halle/hallehinzufuegen.fxml", "Halle hinzufügen");
    }
}
