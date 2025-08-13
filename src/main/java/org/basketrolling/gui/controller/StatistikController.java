/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.basketrolling.gui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.basketrolling.beans.MannschaftIntern;
import org.basketrolling.beans.Spiele;
import org.basketrolling.beans.Spieler;
import org.basketrolling.dao.MannschaftInternDAO;
import org.basketrolling.dao.SpieleDAO;
import org.basketrolling.dao.SpielerDAO;
import org.basketrolling.dao.StatistikDAO;
import org.basketrolling.interfaces.MainBorderSettable;
import org.basketrolling.service.MannschaftInternService;
import org.basketrolling.service.SpieleService;
import org.basketrolling.service.SpielerService;
import org.basketrolling.service.StatistikService;
import org.basketrolling.utils.AlertUtil;
import org.basketrolling.utils.MenuUtil;

/**
 * Controller zur Anzeige und Auswertung der Statistiken für die internen
 * Mannschaften.
 * <p>
 * Zeigt zu jeder Mannschaft u.a.:
 * <ul>
 * <li>Diagramm mit erzielten Punkten pro Spiel</li>
 * <li>Anzahl aktiver Spieler im Kader</li>
 * <li>Durchschnittsalter der aktiven Spieler</li>
 * <li>Top-Scorer mit Punkten pro Spiel (PPG)</li>
 * <li>Punktedurchschnitt der gesamten Mannschaft</li>
 * </ul>
 *
 * Es werden bis zu drei Mannschaften geladen (erkennbar an den Endungen
 * <code>/1</code>, <code>/2</code> und <code>/3</code> im Namen). Die Daten
 * werden aus den zugehörigen Services geladen und grafisch in
 * JavaFX-{@link BarChart} Elementen dargestellt.
 *
 * Implementiert {@link Initializable} für die Initialisierungslogik und
 * {@link MainBorderSettable}, um das Haupt-{@link BorderPane} zu setzen.
 *
 * @author Marko
 */
public class StatistikController implements Initializable, MainBorderSettable {

    // DAOs
    SpielerDAO spielerDao;
    MannschaftInternDAO mannInDao;
    SpieleDAO spieleDao;
    StatistikDAO statsitikDao;

    // Services
    SpielerService spielerService;
    SpieleService spieleService;
    MannschaftInternService mannInService;
    StatistikService statsitikService;

    // Mannschaftsobjekte
    MannschaftIntern mannIn1;
    MannschaftIntern mannIn2;
    MannschaftIntern mannIn3;

    // Diagramme
    @FXML
    private BarChart<String, Integer> bcRolling1;
    @FXML
    private BarChart<String, Integer> bcRolling2;
    @FXML
    private BarChart<String, Integer> bcRolling3;

    // Labels für Teams
    @FXML
    private Label lbTeam1;
    @FXML
    private Label lbTeam2;
    @FXML
    private Label lbTeam3;

    // Labels für aktive Spieler
    @FXML
    private Label lbAktiveSpieler1;
    @FXML
    private Label lbAktiveSpieler2;
    @FXML
    private Label lbAktiveSpieler3;

    // Labels für Durchschnittsalter
    @FXML
    private Label lbDurchschnittsalter1;
    @FXML
    private Label lbDurchschnittsalter2;
    @FXML
    private Label lbDurchschnittsalter3;

    // Labels für Top-Scorer
    @FXML
    private Label lbTopScorer1;
    @FXML
    private Label lbTopScorer2;
    @FXML
    private Label lbTopScorer3;

    // Labels für Punktedurchschnitt
    @FXML
    private Label lbPunktedurchschnit1;
    @FXML
    private Label lbPunktedurchschnit2;
    @FXML
    private Label lbPunktedurchschnit3;

    private BorderPane mainBorderPane;

    /**
     * Setzt das Haupt-Layout-Element für Navigation.
     *
     * @param mainBorderPane zentrales {@link BorderPane}
     */
    @Override
    public void setMainBorder(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    /**
     * Initialisiert den Controller und lädt alle relevanten Statistikdaten:
     * <ul>
     * <li>Lädt Mannschaften anhand der Kennzeichnung im Namen (/1, /2, /3)</li>
     * <li>Setzt Teamnamen-Labels</li>
     * <li>Erstellt Diagramme mit erzielten Punkten</li>
     * <li>Zeigt aktive Spieleranzahl und Durchschnittsalter</li>
     * <li>Ermittelt Top-Scorer und Punkteschnitt</li>
     * </ul>
     * Falls keine Mannschaft gefunden wird, erscheint eine Fehlermeldung.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spieleDao = new SpieleDAO();
        spielerDao = new SpielerDAO();
        mannInDao = new MannschaftInternDAO();
        statsitikDao = new StatistikDAO();

        spieleService = new SpieleService(spieleDao);
        spielerService = new SpielerService(spielerDao);
        mannInService = new MannschaftInternService(mannInDao);
        statsitikService = new StatistikService(statsitikDao);

        List<MannschaftIntern> mannschaft1 = mannInService.getByName("/1");
        List<MannschaftIntern> mannschaft2 = mannInService.getByName("/2");
        List<MannschaftIntern> mannschaft3 = mannInService.getByName("/3");

        if (mannschaft1.isEmpty() && mannschaft2.isEmpty() && mannschaft3.isEmpty()) {
            AlertUtil.alertError("Initialisierung fehlgeschlagen", "Keine Mannschaft(en) gefunden.", "Bitte prüfen Sie, ob die Mannschaften in der Datenbank vorhanden sind.");
            return;
        }

        if (!mannschaft1.isEmpty()) {
            mannIn1 = mannschaft1.get(0);
            lbTeam1.setText(mannIn1.getName());
        }

        if (!mannschaft2.isEmpty()) {
            mannIn2 = mannschaft2.get(0);
            lbTeam2.setText(mannIn2.getName());
        }

        if (!mannschaft3.isEmpty()) {
            mannIn3 = mannschaft3.get(0);
            lbTeam3.setText(mannIn3.getName());
        }

        List<Spiele> spieleList1 = spieleService.getByMannschaftIntern(mannIn1);
        List<Spiele> spieleList2 = spieleService.getByMannschaftIntern(mannIn2);
        List<Spiele> spieleList3 = spieleService.getByMannschaftIntern(mannIn3);

        bcRolling1.getData().add(setzeStatistik(spieleList1));
        bcRolling2.getData().add(setzeStatistik(spieleList2));
        bcRolling3.getData().add(setzeStatistik(spieleList3));

        List<Spieler> aktiveSpieler1 = spielerService.getByAktivUndMannschaft(true, mannIn1);
        List<Spieler> aktiveSpieler2 = spielerService.getByAktivUndMannschaft(true, mannIn2);
        List<Spieler> aktiveSpieler3 = spielerService.getByAktivUndMannschaft(true, mannIn3);

        lbAktiveSpieler1.setText("Aktive Spieler im Kader: " + aktiveSpieler1.size());
        lbAktiveSpieler2.setText("Aktive Spieler im Kader: " + aktiveSpieler2.size());
        lbAktiveSpieler3.setText("Aktive Spieler im Kader: " + aktiveSpieler3.size());

        lbDurchschnittsalter1.setText("Durchschnittsalter: " + String.format("%.2f", berechneDurchschnittsalter(aktiveSpieler1)));
        lbDurchschnittsalter2.setText("Durchschnittsalter: " + String.format("%.2f", berechneDurchschnittsalter(aktiveSpieler2)));
        lbDurchschnittsalter3.setText("Durchschnittsalter: " + String.format("%.2f", berechneDurchschnittsalter(aktiveSpieler3)));

        Object[] topScorer1 = statsitikService.getTopScorerByMannschaft(mannIn1);
        Object[] topScorer2 = statsitikService.getTopScorerByMannschaft(mannIn2);
        Object[] topScorer3 = statsitikService.getTopScorerByMannschaft(mannIn3);

        zeigeTopScorer(lbTopScorer1, topScorer1);
        zeigeTopScorer(lbTopScorer2, topScorer2);
        zeigeTopScorer(lbTopScorer3, topScorer3);

        zeigePunkteschnitt(lbPunktedurchschnit1, mannIn1);
        zeigePunkteschnitt(lbPunktedurchschnit2, mannIn2);
        zeigePunkteschnitt(lbPunktedurchschnit3, mannIn3);
    }

    /**
     * Erstellt eine {@link XYChart.Series} mit den erzielten Punkten der
     * Mannschaft. Jeder Balken erhält ein Label mit der Punktzahl.
     *
     * @param spieleList Liste der Spiele
     * @return Diagrammserie mit Gegnernamen und Punkten
     */
    private XYChart.Series setzeStatistik(List<Spiele> spieleList) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Erzielte Punkte");

        for (Spiele spiel : spieleList) {
            String mannEx = spiel.getMannschaftExtern().getName() + "(" + spiel.getDatum().toString() + ")";
            int punkte = spiel.getInternPunkte();

            XYChart.Data<String, Integer> data = new XYChart.Data<>(mannEx, punkte);
            series.getData().add(data);

            data.nodeProperty().addListener((obs, altNode, neuNode) -> {
                if (neuNode != null) {
                    Label label = new Label(String.valueOf(punkte));
                    ((StackPane) neuNode).getChildren().add(label);
                }
            });
        }
        return series;
    }

    /**
     * Navigiert zurück ins Hauptmenü.
     */
    public void backToHauptmenue() {
        MenuUtil.backToHauptmenu(mainBorderPane);
    }

    /**
     * Berechnet das Durchschnittsalter einer Spielerliste.
     *
     * @param spielerListe Liste der Spieler
     * @return Durchschnittsalter oder 0, wenn keine Daten vorliegen
     */
    public double berechneDurchschnittsalter(List<Spieler> spielerListe) {
        LocalDate heute = LocalDate.now();
        int summe = 0;
        int zaehler = 0;

        for (Spieler spieler : spielerListe) {
            LocalDate geburt = spieler.getGeburtsdatum();
            if (geburt != null) {
                summe += Period.between(geburt, heute).getYears();
                zaehler++;
            }
        }

        return zaehler == 0 ? 0 : (double) summe / zaehler;
    }

    /**
     * Zeigt den Top-Scorer im angegebenen Label an.
     *
     * @param label Ziel-Label
     * @param scorer Array mit Spieler, Punkten und Anzahl der Spiele
     */
    private void zeigeTopScorer(Label label, Object[] scorer) {
        if (scorer != null) {
            Spieler spieler = (Spieler) scorer[0];
            double punkte = ((Number) scorer[1]).doubleValue();
            int spiele = ((Number) scorer[2]).intValue();

            label.setText("Top-Scorer: " + spieler.getVorname() + " " + spieler.getNachname()
                    + " | " + String.format("%.1f", punkte) + " PPG in "
                    + spiele + (spiele == 1 ? " Spiel" : " Spielen"));
        } else {
            label.setText("Top-Scorer: Nicht verfügbar");
        }
    }

    /**
     * Zeigt den Punktedurchschnitt der Mannschaft im angegebenen Label an.
     *
     * @param label Ziel-Label
     * @param mannschaft Mannschaft, deren Schnitt angezeigt werden soll
     */
    private void zeigePunkteschnitt(Label label, MannschaftIntern mannschaft) {
        Double schnitt = spieleService.getPunkteschnittProMannschaft(mannschaft);
        if (schnitt != null) {
            label.setText("Punktedurchschnitt: " + String.format("%.1f", schnitt) + " PPG");
        } else {
            label.setText("Punktedurchschnitt: Nicht verfügbar");
        }
    }
}
